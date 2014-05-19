package cz.au.railwaystation;

import com.google.common.collect.Lists;
import cz.au.railwaystation.dot.Graph;
import cz.au.railwaystation.dot.Node;
import cz.au.railwaystation.fol.Constant;
import cz.au.railwaystation.fol.Formula;
import cz.au.railwaystation.fol.OutputFormat;
import cz.au.railwaystation.fol.Variable;
import org.apache.commons.io.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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

public class ProofBuilder {

	private final Graph graph;
	private final File outputFolder;

	private OutputFormat format = OutputFormat.LADR;

	private Map<Node, Constant> nodeCons;

	public ProofBuilder(Graph graph, File outputFolder) {
		this.graph = checkNotNull(graph);
		this.outputFolder = checkNotNull(outputFolder);
		init();
	}

	public void setFormat(OutputFormat format) {
		this.format = format;
	}

	private Constant nodeCon(Node node) {
		return nodeCons.get(node);
	}

	private void init() {
		final ArrayList<Node> nodes = Lists.newArrayList(graph);
		nodeCons = new HashMap<Node, Constant>(nodes.size());
		for (Node node : nodes) {
			nodeCons.put(node, con(node.getName()));
		}
	}

	/** Proofs for verifying that there are no derails. */
	public void createDerailsProofs() throws IOException {
		// the additional axioms
		final LinkedList<Formula> axioms = new LinkedList<Formula>();
		axioms.addAll(getAlwaysEnterAxioms());

		int i = 1;
		for (Formula conjecture : getNoDerailsConjecture()) {
			writeAxiomsAndConjecture(axioms, conjecture, "proof_derails_" + (i++));
		}
	}

	/** Proofs for verifying that there are no collisions. */
	public void createCollisionsProofs() throws IOException {
		// the additional axioms
		final LinkedList<Formula> axioms = new LinkedList<Formula>();
		axioms.addAll(getAlwaysEnterAxioms());
		axioms.addAll(getAlwaysGoesAxioms());

		int i = 1;
		for (Formula conjecture : getNoCollisionsConjecture()) {
			writeAxiomsAndConjecture(axioms, conjecture, "proof_collisions_" + (i++));
		}
	}

	/** Proofs for verifying that the signals open. */
	public void createSignalsProofs() throws IOException {
		// the additional axioms
		final LinkedList<Formula> axioms = new LinkedList<Formula>();
		axioms.addAll(getAlwaysEnterAxioms());
		axioms.addAll(getAlwaysGoesAxioms());

		int i = 1;
		for (Formula conjecture : getSignalOpensConjecture()) {
			writeAxiomsAndConjecture(axioms, conjecture, "proof_signals_" + (i++));
		}
	}

	/** The train driver axioms. */
	private List<Formula> getAlwaysGoesAxioms() {
		final Variable x = var("x"), t = var("t");

		// always goes : all X,T goes(X,T)
		final Formula alwaysGoes = q(goes(x, t)).forAll(x, t);
		alwaysGoes.label("alwaysGoes").comment("the train driver always goes");

		return Arrays.asList(alwaysGoes);
	}

	/** The train always enters axioms. */
	private List<Formula> getAlwaysEnterAxioms() {
		final Variable x = var("x"), t = var("t");
		final LinkedList<Formula> alwaysEntersAxioms = new LinkedList<Formula>();

		for (Node node : graph.getSources()) {
			// always enters axiom : all X,T enter(X,T,in)
			Constant in = nodeCon(node);
			Formula alwaysEnters = q(enter(x, t, in)).forAll(x, t);
			alwaysEnters.label("alwaysEnters_" + in.getName()).comment("the train always enters for " + node.getName());
			alwaysEntersAxioms.add(alwaysEnters);
		}

		return alwaysEntersAxioms;
	}

	/** The no train derail conjecture. */
	private List<Formula> getNoDerailsConjecture() {
		final Variable x = var("x"), t = var("t");
		final LinkedList<Formula> conjecture = new LinkedList<Formula>();

		for (Node node : graph.getInnerNodes()) {
			if (node.isSwitch()) {
				// no derail conjecture : all X,T (at(X,T,n) & at(succ(X),T,n)) => switch(X,n) = switch(succ(X),n)
				Constant n = nodeCon(node);
				Formula noDerail = q(imp(and(at(x, t, n), at(succ(x), t, n)), eq(swtch(x,n), swtch(succ(x), n)))).forAll(x, t);
				noDerail.type("conjecture").label("noDerails_" + n.getName()).comment("no train derails for " + node.getName());
				conjecture.add(noDerail);
			}
		}

		return conjecture;
	}

	/** The no train collisions conjecture. */
	private List<Formula> getNoCollisionsConjecture() {
		final Variable x = var("x"), t1 = var("t1"), t2 = var("t2");
		final LinkedList<Formula> conjecture = new LinkedList<Formula>();

		for (Node node : graph.getInnerNodes()) {
			// no collisions conjecture : all X,T1,T2 (T1 != T2 & at(X,T1,n)) => -at(X,T2,n)
			Constant n = nodeCon(node);
			Formula noCollision = q(imp(and(neq(t1, t2), at(x, t1, n)), not(at(x, t2, n)))).forAll(x, t1, t2);
			noCollision.type("conjecture").label("noCollisions_" + n.getName()).comment("no train collisions for " + node.getName());
			conjecture.add(noCollision);
		}

		return conjecture;
	}

	/** The signal opens conjecture. */
	private List<Formula> getSignalOpensConjecture() {
		final Variable x = var("x");
		final LinkedList<Formula> conjecture = new LinkedList<Formula>();

		for (Node node : graph.getSources()) {
			// signal opens conjecture : exists X open(X,in)
			Constant in = nodeCon(node);
			Formula signalOpens = q(open(x, in)).exists(x);
			signalOpens.type("conjecture").label("signalOpens_" + in.getName()).comment("the signal opens for " + node.getName());
			conjecture.add(signalOpens);
		}

		return conjecture;
	}

	private void writeAxiomsAndConjecture(List<Formula> axioms, Formula conjecture, String filename) throws IOException {
		switch (format) {
			case TPTP: filename += ".p"; break;
			case LADR: filename += ".p9"; break;
		}

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new FileWriter(new File(outputFolder, filename)));

			if (format == OutputFormat.LADR) {
				output.write(String.format(
						"if(Prover9).%n" +
						" set(prolog_style_variables).%n" +
						"end_if.%n%n" +
						"if(Mace4).%n" +
						" set(prolog_style_variables).%n" +
						"end_if.%n%n" +
						"formulas(assumptions).%n%n"
				));
				output.flush();
			}

			includeFile("order", output);
			includeFile("layout", output);
			includeFile("control", output);

			output.write(String.format("%n%% ADDITIONAL AXIOMS :%n%n"));
			for (Formula axiom : axioms) {
				if (axiom == null) {
					output.newLine();
				} else {
					axiom.printFormula(output, format);
				}
			}
			output.flush();

			if (format == OutputFormat.LADR) {
				output.write(String.format(
						"%nend_of_list.%n%n" +
						"formulas(goals).%n%n"));
				output.flush();
			}

			output.newLine();
			conjecture.printFormula(output, format);
			output.flush();

			if (format == OutputFormat.LADR) {
				output.write(String.format(
						"%nend_of_list.%n%n"));
				output.flush();
			}
		} finally {
			IOUtils.closeQuietly(output);
		}
	}

	private void includeFile(String filename, BufferedWriter output) throws IOException {
		if (format == OutputFormat.TPTP) {
			output.write(String.format("include('%s.p').%n", filename));
		}
		if (format == OutputFormat.LADR) {
			output.write(String.format("%%%% INCLUDING FILE %s.p9 %%%%%n%n", filename));
			IOUtils.copy(new FileReader(new File(outputFolder, filename + ".p9")), output);
			output.newLine();
			output.flush();
		}
	}
}
