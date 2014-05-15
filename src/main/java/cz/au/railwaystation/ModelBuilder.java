package cz.au.railwaystation;

import com.google.common.collect.Lists;
import cz.au.railwaystation.dot.Graph;
import cz.au.railwaystation.dot.GraphUtil;
import cz.au.railwaystation.dot.Node;
import cz.au.railwaystation.fol.Conjunction;
import cz.au.railwaystation.fol.Constant;
import cz.au.railwaystation.fol.Disjunction;
import cz.au.railwaystation.fol.Formula;
import cz.au.railwaystation.fol.OutputFormat;
import cz.au.railwaystation.fol.Variable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static cz.au.railwaystation.domain.DomainFactory.*;

public class ModelBuilder {

	private final Graph graph;
	private final File outputFolder;

	private OutputFormat format;

	public ModelBuilder(BufferedReader input, File outputFolder) throws IOException {
		this.outputFolder = checkNotNull(outputFolder);
		graph = GraphUtil.parseGraph(input);
		format = OutputFormat.LADR;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setFormat(OutputFormat format) {
		this.format = checkNotNull(format);
	}

	/** Axioms for the layout of the given railway station. */
	public void createStationLayoutAxioms() throws IOException {
		final ArrayList<Node> allNodes = Lists.newArrayList(graph);
		final Map<Node, Constant> cons = new HashMap<Node, Constant>(allNodes.size());
		for (Node node : allNodes) {
			cons.put(node, con(node.getName()));
		}
		final Variable x = var("x"), y = var("y"), t = var("t"), n = var("n"), n1 = var("n1"), n2 = var("n2");

		// node domain restriction : (in1 != in2 & .... & outY != outX)
		final Conjunction nodeDomainRestriction = and();
		for (int i = 0, size = allNodes.size(); i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				nodeDomainRestriction.add(neq(cons.get(allNodes.get(i)), cons.get(allNodes.get(j))));
			}
		}
		nodeDomainRestriction.setLabel("nodeDomainRestriction");

		// node predicate : all N node(N) <=> (N = in1 | .... | N = outX)
		final Disjunction nodeDomain = or();
		for (Node node : allNodes) {
			nodeDomain.add(eq(n, cons.get(node)));
		}
		final Formula nodePredicate = q(eqv(node(n), nodeDomain)).forAll(n);
		nodePredicate.setLabel("nodePredicate");

		final Set<Node> otherNodes = new LinkedHashSet<Node>(graph.getInnerNodes().size() + graph.getSinks().size());
		otherNodes.addAll(graph.getInnerNodes());
		otherNodes.addAll(graph.getSinks());

		// input node axioms : all X,T at(succ(X),T,in1) <-> (enter(X,T,in1) | (at(X,T,in1) & (-goes(X,in1) | -open(X,in1)))) ....
		final List<Formula> nodeAxioms = new LinkedList<Formula>();
		for (Node node : graph.getSources()) {
			Constant in = cons.get(node);
			Formula inputNodeAxiom = q(eqv(at(succ(x), t, in), or(enter(x, t, in), and(at(x, t, in), or(neg(goes(x, in)), neg(open(x, in))))))).forAll(x, t);
			inputNodeAxiom.setLabel("inputNode_" + node.getName());
			nodeAxioms.add(inputNodeAxiom);
		}

		// other node axioms : all X,T at(succ(X),T,v2) <-> ((at(X,T,in1) & -goes(X,in1)) | (at(X,T,v1) & goes(X,T,v1) & open(X,v1) & switch(X,v1) = v2) | .... )
		for (Node node : otherNodes) {
			Constant nodeCon = cons.get(node);
			Disjunction leftSide = or();
			if (!node.isSink()) {
				leftSide.add(and(at(x, t, nodeCon), neg(goes(x, nodeCon))));
			}
			for (Node parent : node.getParents()) {
				Constant parentCon = cons.get(parent);
				Conjunction conditions = and(at(x, t, parentCon), goes(x, parentCon));
				if (parent.isSource()) {
					conditions.add(open(x, parentCon));
				}
				if (parent.isSwitch()) {
					conditions.add(eq(swtch(x, parentCon), nodeCon));
				}
				leftSide.add(conditions);
			}
			Formula nodeAxiom = q(eqv(at(succ(x), t, nodeCon), leftSide)).forAll(x, t);
			nodeAxiom.setLabel("node_" + node.getName());
			nodeAxioms.add(nodeAxiom);
		}

		// the train location axiom : all X,T,N1,N2 (at(X,T,N1) & at(X,T,N2)) => (N1 = N2)
		final Formula trainLocation = q(imp(and(at(x, t, n1), at(x, t, n2)), eq(n1, n2))).forAll(x, t, n1, n2);
		trainLocation.setLabel("trainLocation");

		// the train driver axiom : all X,T,N at(X,T,N) => (exists Y ((X = Y | less(X,Y)) & goes(Y,N)))
		final Formula trainDriver = q(imp(at(x, t, n), q(and(or(eq(x, y), less(x, y)), goes(y, n))).exists(y))).forAll(x, t, n);
		trainDriver.setLabel("trainDriver");

		final BufferedWriter layout = new BufferedWriter(new FileWriter(new File(outputFolder, "layout.p")));
		try {
			nodePredicate.printFormula(layout, format);
			nodeDomainRestriction.printFormula(layout, format);
			for (Formula axiom : nodeAxioms) {
				axiom.printFormula(layout, format);
			}
			trainLocation.printFormula(layout, format);
			trainDriver.printFormula(layout, format);
			layout.flush();
		} catch (IOException e) {
			layout.close();
		}
	}

	/** Axioms for the linear ordering. */
	public void createOrderAxioms() throws IOException {
		final Variable x = var("x"), y = var("y"), z = var("z");

		// antisymmetry : all X,Y (less(X,Y) & less(Y,X)) => (X = Y)
		final Formula antisymmetry = q(imp(and(less(x, y), less(y, x)), eq(x, y))).forAll(x, y);
		antisymmetry.setLabel("antisymmetry");

		// transitivity : all X,Y,Z (less(X,Y) & less(Y,Z)) => less(X,Z)
		final Formula transitivity = q(imp(and(less(x, y), less(y, z)), less(x, y))).forAll(x, y, z);
		transitivity.setLabel("transitivity");

		// totality : all X,Y (less(X,Y) | less(Y,X))
		final Formula totality = q(or(less(x, y), less(y, x))).forAll(x, y);
		totality.setLabel("totality");

		// successor : all X (less(X,succ(X)) & (all Y (less(Y,X) | less(succ(X),Y)))
		final Formula successor = q(and(less(x, succ(x)), q(or(less(y, x), less(succ(x), y))).forAll(y))).forAll(x);
		successor.setLabel("successor");

		// predecessor : all X (pred(succ(X)) = X) & (succ(pred(X)) = X)
		final Formula predecessor = q(and(eq(pred(succ(x)), x), eq(succ(pred(x)), x))).forAll(x);
		predecessor.setLabel("predecessor");

		final BufferedWriter order = new BufferedWriter(new FileWriter(new File(outputFolder, "order.p")));
		try {
			antisymmetry.printFormula(order, format);
			transitivity.printFormula(order, format);
			totality.printFormula(order, format);
			successor.printFormula(order, format);
			predecessor.printFormula(order, format);
			order.flush();
		} catch (IOException e) {
			order.close();
		}
	}

}
