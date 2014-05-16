package cz.au.railwaystation;

import com.google.common.collect.Lists;
import cz.au.railwaystation.dot.Graph;
import cz.au.railwaystation.dot.GraphPaths;
import cz.au.railwaystation.dot.GraphUtil;
import cz.au.railwaystation.dot.Node;
import cz.au.railwaystation.dot.Path;
import cz.au.railwaystation.fol.Conjunction;
import cz.au.railwaystation.fol.Constant;
import cz.au.railwaystation.fol.Disjunction;
import cz.au.railwaystation.fol.Formula;
import cz.au.railwaystation.fol.OutputFormat;
import cz.au.railwaystation.fol.Variable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static cz.au.railwaystation.domain.DomainFactory.*;

public class ModelBuilder {

	private final Graph graph;
	private final File outputFolder;

	private OutputFormat format = OutputFormat.LADR;

	private GraphPaths graphPaths;

	private ArrayList<Node> nodes;
	private Map<Node, Constant> nodeCons;

	private ArrayList<Path> paths;
	private Map<Path, Constant> pathCons;

	public ModelBuilder(Graph graph, File outputFolder) {
		this.graph = checkNotNull(graph);
		this.outputFolder = checkNotNull(outputFolder);
		init();
	}

	public void setFormat(OutputFormat format) {
		this.format = checkNotNull(format);
	}

	private Constant nodeCon(Node node) {
		return nodeCons.get(node);
	}

	private Constant pathCon(Path path) {
		return pathCons.get(path);
	}

	private void init() {
		graphPaths = GraphUtil.findAllPaths(graph);
		nodes = Lists.newArrayList(graph);
		nodeCons = new HashMap<Node, Constant>(nodes.size());
		for (Node node : nodes) {
			nodeCons.put(node, con(node.getName()));
		}
		paths = new ArrayList<Path>(graphPaths.getAllPaths());
		pathCons = new HashMap<Path, Constant>(paths.size());
		for (Path path : paths) {
			pathCons.put(path, con(String.format("%s_%s_%d", path.getStart().getName(), path.getEnd().getName(), path.getIndex())));
		}
	}

	/** Axioms for the layout of the given railway station. */
	public void createStationLayoutAxioms() throws IOException {
		final Variable x = var("x"), y = var("y"), t = var("t"), n = var("n"), n1 = var("n1"), n2 = var("n2");

		final List<Formula> layoutAxioms = new LinkedList<Formula>();
		// add the node domain axioms
		layoutAxioms.addAll(buildNodeDomainAxioms());
		layoutAxioms.add(null);
		// add the node transition axioms
		layoutAxioms.addAll(buildNodeTransitionAxioms());
		layoutAxioms.add(null);

		// train singular location axiom : all X,T,N1,N2 (at(X,T,N1) & at(X,T,N2)) => (N1 = N2)
		final Formula singularTrainLocation = q(imp(and(at(x, t, n1), at(x, t, n2)), eq(n1, n2))).forAll(x, t, n1, n2);
		singularTrainLocation.label("singularTrainLocation").comment("no train can be at any two nodes in the same time");
		layoutAxioms.add(singularTrainLocation);

		// train driver goes axiom : all X,T,N at(X,T,N) => (exists Y ((X = Y | less(X,Y)) & goes(Y,N)))
		final Formula trainDriverGoes = q(imp(at(x, t, n), q(and(or(eq(x, y), less(x, y)), goes(y, n))).exists(y))).forAll(x, t, n);
		trainDriverGoes.label("trainDriverGoes").comment("the train driver has to go eventually");
		layoutAxioms.add(trainDriverGoes);

		exportAxioms(layoutAxioms, "layout");
	}

	/** Builds axioms for the node constants, i.e. allDiff and node predicate. */
	private List<Formula> buildNodeDomainAxioms() {
		final Variable n = var("n");

		// nodes all-different : (in1 != in2 & in1 != v1 & .... )
		final Conjunction nodesAllDiff = and();
		for (int i = 0, size = nodes.size(); i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				nodesAllDiff.add(neq(nodeCon(nodes.get(i)), nodeCon(nodes.get(j))));
			}
		}
		nodesAllDiff.label("nodesAllDiff").comment("all the node constants need to be different");

		// node predicate : all N node(N) <=> (N = in1 | N = in2 | .... )
		final Disjunction nodeDomain = or();
		for (Node node : nodes) {
			nodeDomain.add(eq(n, nodeCon(node)));
		}
		final Formula nodePredicate = q(eqv(node(n), nodeDomain)).forAll(n);
		nodePredicate.label("nodePredicate").comment("all and only the node constants are nodes");

		return Arrays.asList(nodesAllDiff, nodePredicate);
	}

	/** Builds axioms defining the transitions between nodes. */
	private List<Formula> buildNodeTransitionAxioms() {
		final Variable x = var("x"), t = var("t");

		final List<Formula> nodeAxioms = new LinkedList<Formula>();
		for (Node node : nodes) {
			if (node.isSource()) {
				// input node axioms : all X,T at(succ(X),T,in) <-> (enter(X,T,in) | (at(X,T,in) & (-goes(X,in) | -open(X,in)))) ....
				Constant in = nodeCon(node);
				Formula inputNodeAxiom = q(eqv(at(succ(x), t, in), or(enter(x, t, in), and(at(x, t, in), or(neg(goes(x, in)), neg(open(x, in))))))).forAll(x, t);
				inputNodeAxiom.label("node_" + in.getName()).comment("transition axiom for input node " + node.getName());
				nodeAxioms.add(inputNodeAxiom);
			} else {
				// other node axioms : all X,T at(succ(X),T,n) <-> ((at(X,T,n) & -goes(X,n)) | (at(X,T,v1) & goes(X,T,v1) & open(X,v1) & switch(X,v1) = n) | .... )
				Constant n = nodeCon(node);
				Disjunction rightSide = or();
				if (!node.isSink()) {
					rightSide.add(and(at(x, t, n), neg(goes(x, n))));
				}
				for (Node parent : node.getParents()) {
					Constant parentCon = nodeCon(parent);
					Conjunction conditions = and(at(x, t, parentCon), goes(x, parentCon));
					if (parent.isSource()) {
						conditions.add(open(x, parentCon));
					}
					if (parent.isSwitch()) {
						conditions.add(eq(swtch(x, parentCon), n));
					}
					rightSide.add(conditions);
				}
				Formula nodeAxiom = q(eqv(at(succ(x), t, n), rightSide)).forAll(x, t);
				nodeAxiom.label("node_" + n.getName()).comment("transition axiom for " + (node.isSink() ? "sink node " : "inner node ") + node.getName());
				nodeAxioms.add(nodeAxiom);
			}
		}

		return nodeAxioms;
	}

	/** Axioms for the control system of the given railway station. */
	public void createStationControlAxioms() throws IOException {
		final List<Formula> controlAxioms = new LinkedList<Formula>();

		// add the node domain axioms
		controlAxioms.addAll(buildPathDomainAxioms());
		controlAxioms.add(null);
		// add the path axioms
		controlAxioms.addAll(buildPathAxioms());
		// add the signal control axioms
		controlAxioms.addAll(buildSignalControlAxioms());
		// add the clock axioms
		controlAxioms.addAll(buildClockAxioms());

		exportAxioms(controlAxioms, "control");
	}

	/** Builds axioms for the path constants, i.e. allDiff and path predicate. */
	private List<Formula> buildPathDomainAxioms() {
		final Variable p = var("p");

		// path all-different : (p1 != p2 & p1 != p3 & .... )
		final Conjunction pathAllDiff = and();
		for (int i = 0, size = paths.size(); i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				pathAllDiff.add(neq(pathCon(paths.get(i)), pathCon(paths.get(j))));
			}
		}
		pathAllDiff.label("pathAllDiff").comment("all the path constants need to be different");

		// path predicate : all P node(P) <=> (P = p1 | P = p2 | .... )
		final Disjunction pathDomain = or();
		for (Path path : paths) {
			pathDomain.add(eq(p, pathCon(path)));
		}
		final Formula pathPredicate = q(eqv(path(p), pathDomain)).forAll(p);
		pathPredicate.label("pathPredicate").comment("all and only the path constants are paths");

		return Arrays.asList(pathAllDiff, pathPredicate);
	}

	/** Builds axioms defining the possible paths through the station. */
	private List<Formula> buildPathAxioms() {
		final Variable x = var("x"), t = var("t");
		final LinkedList<Formula> pathAxioms = new LinkedList<Formula>();

		for (Path path : paths) {
			Constant p = pathCon(path);
			String pathName = String.format("(%s->%s)#%d", path.getStart().getName(), path.getEnd().getName(), path.getIndex());

			// switch configuration : all X conf(X,p) <=> (switch(X,n1) = n1 & switch(X,n2) = n3 & .... )
			Conjunction switches = and();
			ArrayList<Node> nodes = Lists.newArrayList(path.iterator());
			for (int i = 0, size = nodes.size() - 1; i < size; i++) {
				if (nodes.get(i).isSwitch()) {
					switches.add(eq(swtch(x, nodeCon(nodes.get(i))), nodeCon(nodes.get(i + 1))));
				}
			}
			Formula pathConf = q(eqv(conf(x, p), (switches.size() > 0) ? switches : b(true))).forAll(x);
			pathConf.label("conf_" + p.getName()).comment("switch configuration axiom for " + pathName);
			pathAxioms.add(pathConf);

			// free path axiom : all X,T free(X,p) <=> (-at(X,T,n1) & -at(X,T,n2) & .... )
			final LinkedHashSet<Node> freeNodes = new LinkedHashSet<Node>();
			for (Node node : path) {
				if (!node.isSource()) {
					freeNodes.add(node);
				}
			}
			for (Path intersecting : GraphUtil.getIntersectingPaths(graphPaths, path)) {
				// TODO add only the nodes before the last intersection
				for (Node node : intersecting) {
					if (!node.isSource() && !node.isSink()) {
						freeNodes.add(node);
					}
				}
			}
			Conjunction rightSide = and();
			for (Node node : freeNodes) {
				rightSide.add(neg(at(x, t, nodeCon(node))));
			}
			Formula pathFree = q(eqv(free(x, p), rightSide)).forAll(x, t);
			pathFree.label("free_" + p.getName()).comment("free path axiom for " + pathName);
			pathAxioms.add(pathFree);

			pathAxioms.add(null);
		}

		return pathAxioms;
	}

	/** Builds axioms for controlling the input node signals. */
	private List<Formula> buildSignalControlAxioms() {
		final Variable x = var("x"), t = var("t");
		final List<Formula> signalAxioms = new LinkedList<Formula>();

		for (Node node : graph.getSources()) {
			Constant in = nodeCon(node);

			for (Node sink : graph.getSinks()) {
				// open the signal : all X,T (clock(X) = in & at(X,T,in) & gate(T) = out & ((conf(X,p1) & free(X,p1)) | ....)) => open(X,in)
				Disjunction paths = or();
				for (Path path : graphPaths.getPaths(node, sink)) {
					paths.add(and(conf(x, pathCon(path)), free(x, pathCon(path))));
				}
				Constant out = nodeCon(sink);
				Formula openAxiom = q(imp(and(eq(clock(x), in), at(x, t, in), eq(gate(t), out), paths), open(x, in))).forAll(x, t);
				openAxiom.label("open_" + in.getName() + "_" + out.getName()).comment("open the signal " + node.getName() + " for a path to " + sink.getName());
				signalAxioms.add(openAxiom);
			}

			// close the signal : all X,T (at(pred(X),T,in) & open(X,in) & -at(X,T,in) => -open(X,in)
			Formula closeAxiom = q(imp(and(at(pred(x), t, in), open(x, in), neg(at(x, t, in))), neg(open(x, in)))).forAll(x, t);
			closeAxiom.label("close_" + in.getName()).comment("close the signal " + node.getName() + " when a train leaves");
			signalAxioms.add(closeAxiom);

			signalAxioms.add(null);
		}

		return signalAxioms;
	}

	/** Builds axioms for the control clock. */
	private List<Formula> buildClockAxioms() {
		final Variable x = var("x");
		final List<Formula> clockAxioms = new LinkedList<Formula>();

		final ArrayList<Node> sources = Lists.newArrayList(graph.getSources());

		// clock options : all X (clock(X) = in1 | clock(X) = in2 .... )
		final Disjunction clockOptions = or();
		for (Node node : sources) {
			clockOptions.add(eq(clock(x), nodeCon(node)));
		}
		final Formula clockOptionsAxiom = q(clockOptions).forAll(x);
		clockOptionsAxiom.label("clockOptions").comment("the control clock has to be in one of the input nodes");
		clockAxioms.add(clockOptionsAxiom);

		// clock tics : all X (clock(X) = in1) <=> (clock(succ(X) = in2) ....
		final Formula clockTic = q(eqv(eq(clock(x), nodeCon(sources.get(sources.size() - 1))), eq(clock(succ(x)), nodeCon(sources.get(0))))).forAll(x);
		clockTic.label("clockTic").comment("the sequence of tics of the control clock");
		clockAxioms.add(clockTic);
		for (int i = 0, size = sources.size() - 1; i < size; i++) {
			Constant now = nodeCon(sources.get(i)), next = nodeCon(sources.get(i + 1));
			clockAxioms.add(q(eqv(eq(clock(x), now), eq(clock(succ(x)), next))).forAll(x).label("clockTic_" + i));
		}

		return clockAxioms;
	}

	/** Axioms for the linear ordering. */
	public void createOrderAxioms() throws IOException {
		final Variable x = var("x"), y = var("y"), z = var("z");

		// antisymmetry : all X,Y (less(X,Y) & less(Y,X)) => (X = Y)
		final Formula antisymmetry = q(imp(and(less(x, y), less(y, x)), eq(x, y))).forAll(x, y);
		antisymmetry.label("antisymmetry");

		// transitivity : all X,Y,Z (less(X,Y) & less(Y,Z)) => less(X,Z)
		final Formula transitivity = q(imp(and(less(x, y), less(y, z)), less(x, y))).forAll(x, y, z);
		transitivity.label("transitivity");

		// totality : all X,Y (less(X,Y) | less(Y,X))
		final Formula totality = q(or(less(x, y), less(y, x))).forAll(x, y);
		totality.label("totality");

		// successor : all X (less(X,succ(X)) & (all Y (less(Y,X) | less(succ(X),Y)))
		final Formula successor = q(and(less(x, succ(x)), q(or(less(y, x), less(succ(x), y))).forAll(y))).forAll(x);
		successor.label("successor");

		// predecessor : all X (pred(succ(X)) = X) & (succ(pred(X)) = X)
		final Formula predecessor = q(and(eq(pred(succ(x)), x), eq(succ(pred(x)), x))).forAll(x);
		predecessor.label("predecessor");

		exportAxioms(Arrays.asList(antisymmetry, transitivity, totality, successor, predecessor), "order");
	}

	private void exportAxioms(List<Formula> axioms, String filename) throws IOException {
		switch (format) {
			case TPTP: filename += ".p"; break;
			case LADR: filename += ".p9"; break;
		}
		final BufferedWriter layout = new BufferedWriter(new FileWriter(new File(outputFolder, filename)));
		try {
			for (Formula axiom : axioms) {
				if (axiom == null) {
					layout.newLine();
				} else {
					axiom.printFormula(layout, format);
				}
			}
			layout.flush();
		} catch (IOException e) {
			layout.close();
		}
	}

}
