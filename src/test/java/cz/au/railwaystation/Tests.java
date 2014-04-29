package cz.au.railwaystation;

import cz.au.railwaystation.dot.Graph;
import cz.au.railwaystation.dot.Parser;
import cz.au.railwaystation.fol.Constant;
import cz.au.railwaystation.fol.Formula;
import cz.au.railwaystation.fol.OutputFormat;
import cz.au.railwaystation.fol.Variable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static cz.au.railwaystation.fol.Builder.*;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class Tests {

	private static final String GRAPH =
			"digraph GRAPH {\n" +
			"	in -> v;\n" +
			"	v -> out1;\n" +
			"	v -> out2;\n" +
			"}";

	private static final String FORMULA_1_TPTP =
			"fof(antisymmetry, axiom, (\n" +
			"   (![X]: ![Y]: ((less(X, Y) & less(Y, X)) => (X = Y)))\n" +
			")).\n";
	private static final String FORMULA_1_LADR =
			"(all X all Y ((less(X, Y) & less(Y, X)) -> (X = Y))) # label(antisymmetry).\n";
	private static final String FORMULA_2_TPTP =
			"fof(testing, axiom, (\n" +
			"   (?[X]: (is(X) <=> ~(is(a) | is(fun(b, c)) | is(d))))\n" +
			")).\n";
	private static final String FORMULA_2_LADR =
			"(exists X (is(X) <-> -(is(a) | is(fun(b, c)) | is(d)))) # label(testing).\n";

	@Test
	public void testParser() throws IOException {
		final BufferedReader input = new BufferedReader(new StringReader(GRAPH));
		final Graph result = Parser.parse(input);

		// build the graph
		final Graph graph = new Graph();
		graph
			.edge("in", "v")
			.edge("v", "out1")
			.edge("v", "out2");

		assertEquals(graph, result);
	}

	@Test
	public void testFormulaPrinting() throws IOException {
		final Variable x = var("x"), y = var("y");
		final Constant a = con("a"), b = con("b"), c = con("c"), d = con("d");

		final Formula formula1 = q(imp(and(pr("less", x, y), pr("less", y, x)), eq(x, y))).forAll(x, y);
		formula1.setLabel("antisymmetry");

		final String result1_tptp = formula1.printFormula(new StringBuilder(), OutputFormat.TPTP).toString();
		assertEquals(FORMULA_1_TPTP, result1_tptp);

		final String result1_ladr = formula1.printFormula(new StringBuilder(), OutputFormat.LADR).toString();
		assertEquals(FORMULA_1_LADR, result1_ladr);

		final Formula formula2 = q(eqv(pr("is", x), neg(or(pr("is", a), pr("is", fn("fun", b, c)), pr("is", d))))).exists(x);
		formula2.setLabel("testing");

		final String result2_tptp = formula2.printFormula(new StringBuilder(), OutputFormat.TPTP).toString();
		assertEquals(FORMULA_2_TPTP, result2_tptp);

		final String result2_ladr = formula2.printFormula(new StringBuilder(), OutputFormat.LADR).toString();
		assertEquals(FORMULA_2_LADR, result2_ladr);
	}

}
