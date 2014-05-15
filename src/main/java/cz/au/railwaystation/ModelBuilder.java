package cz.au.railwaystation;

import com.google.common.collect.Lists;
import cz.au.railwaystation.dot.Graph;
import cz.au.railwaystation.dot.Node;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static cz.au.railwaystation.domain.DomainFactory.*;

public class ModelBuilder {

	private final Graph graph;
	private final File outputFolder;

	private OutputFormat format = OutputFormat.LADR;

	private ArrayList<Node> nodes;
	private Map<Node, Constant> nodeCons;

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

	private void init() {
		nodes = Lists.newArrayList(graph);
		nodeCons = new HashMap<Node, Constant>(nodes.size());
		for (Node node : nodes) {
			nodeCons.put(node, con(node.getName()));
		}
	}

	/** Axioms for the layout of the given railway station. */
	public void createStationLayoutAxioms() throws IOException {
		final Variable x = var("x"), y = var("y"), t = var("t"), n = var("n"), n1 = var("n1"), n2 = var("n2");

		final List<Formula> layoutAxioms = new LinkedList<Formula>();
		// add the node domain axioms
		layoutAxioms.addAll(buildNodeDomainAxioms());
		// add the node transition axioms
		layoutAxioms.addAll(buildNodeTransitionAxioms());

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

	/** Builds axioms for the node domain, i.e. allDiff and node predicate. */
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
		// X = time, T = train
		final Variable x = var("x"), t = var("t");

		final List<Formula> nodeAxioms = new LinkedList<Formula>();
		for (Node node : nodes) {
			if (node.isSource()) {
				// input node axioms : all X,T at(succ(X),T,in) <-> (enter(X,T,in) | (at(X,T,in) & (-goes(X,in) | -open(X,in)))) ....
				Constant in = nodeCon(node);
				Formula inputNodeAxiom = q(eqv(at(succ(x), t, in), or(enter(x, t, in), and(at(x, t, in), or(neg(goes(x, in)), neg(open(x, in))))))).forAll(x, t);
				inputNodeAxiom.label("node_" + node.getName()).comment("input node transition axiom");
				nodeAxioms.add(inputNodeAxiom);
			} else {
				// other node axioms : all X,T at(succ(X),T,n) <-> ((at(X,T,n) & -goes(X,n)) | (at(X,T,v1) & goes(X,T,v1) & open(X,v1) & switch(X,v1) = n) | .... )
				Constant n = nodeCon(node);
				Disjunction leftSide = or();
				if (!node.isSink()) {
					leftSide.add(and(at(x, t, n), neg(goes(x, n))));
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
					leftSide.add(conditions);
				}
				Formula nodeAxiom = q(eqv(at(succ(x), t, n), leftSide)).forAll(x, t);
				nodeAxiom.label("node_" + node.getName()).comment((node.isSink() ? "sink" : "inner") + " node transition axiom");
				nodeAxioms.add(nodeAxiom);
			}
		}

		return nodeAxioms;
	}

	/** Axioms for the control system of the given railway station. */
	public void createStationControlAxioms() throws IOException {
		final ArrayList<Node> sources = Lists.newArrayList(graph.getSources());

		final List<Formula> controlAxioms = new LinkedList<Formula>();
		// add the clock axioms
		controlAxioms.addAll(buildClockAxioms(sources));

		exportAxioms(controlAxioms, "control");
	}

	/** Builds axioms for the controlling clock. */
	private List<Formula> buildClockAxioms(ArrayList<Node> sources) {
		final Variable x = var("x");
		final List<Formula> clockAxioms = new LinkedList<Formula>();

		// clock options : all X (clock(X) = in1 | clock(X) = in2 .... )
		final Disjunction clockOptions = or();
		for (Node node : sources) {
			clockOptions.add(eq(clock(x), nodeCon(node)));
		}
		final Formula clockOptionsAxiom = q(clockOptions).forAll(x);
		clockOptionsAxiom.label("clockOptions").comment("the clock has to be in one of the input nodes");
		clockAxioms.add(clockOptionsAxiom);

		// clock tics : all X (clock(X) = in1) <=> (clock(succ(X) = in2) ....
		final Formula clockTic = q(eqv(eq(clock(x), nodeCon(sources.get(sources.size() - 1))), eq(clock(succ(x)), nodeCon(sources.get(0))))).forAll(x);
		clockTic.label("clockTic").comment("the sequence of tics of the clock");
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
				axiom.printFormula(layout, format);
			}
			layout.flush();
		} catch (IOException e) {
			layout.close();
		}
	}

}
