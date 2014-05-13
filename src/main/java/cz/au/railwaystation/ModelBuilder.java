package cz.au.railwaystation;

import cz.au.railwaystation.dot.Graph;
import cz.au.railwaystation.dot.GraphUtil;
import cz.au.railwaystation.fol.Formula;
import cz.au.railwaystation.fol.OutputFormat;
import cz.au.railwaystation.fol.Variable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;
import static cz.au.railwaystation.fol.Factory.*;

public class ModelBuilder {

	public static final String LESS = "less";
	public static final String SUCC = "succ";
	public static final String PRED = "pred";

	private final Graph graph;
	private final File outputFolder;

	private OutputFormat format;

	public ModelBuilder(BufferedReader input, File outputFolder) throws IOException {
		this.outputFolder = checkNotNull(outputFolder);
		graph = GraphUtil.parseGraph(input);
		format = OutputFormat.LADR;
	}

	public void setFormat(OutputFormat format) {
		this.format = checkNotNull(format);
	}

	/** Axioms for the linear ordering. */
	public void createOrderAxioms() throws IOException {
		final Variable x = var("x"), y = var("y"), z = var("z");

		// antisymmetry : all X,Y (less(X,Y) & less(Y,X)) => (X = Y)
		final Formula antisymmetry = q(imp(and(pr(LESS, x, y), pr(LESS, y, x)), eq(x, y))).forAll(x, y);
		antisymmetry.setLabel("antisymmetry");

		// transitivity : all X,Y,Z (less(X,Y) & less(Y,Z)) => less(X,Z)
		final Formula transitivity = q(imp(and(pr(LESS, x, y), pr(LESS, y, z)), pr(LESS, x, y))).forAll(x, y, z);
		transitivity.setLabel("transitivity");

		// totality : all X,Y (less(X,Y) | less(Y,X))
		final Formula totality = q(or(pr(LESS, x, y), pr(LESS, y, x))).forAll(x, y);
		totality.setLabel("totality");

		// successor : all X (less(X,succ(X)) & (all Y (less(Y,X) | less(succ(X),Y)))
		final Formula successor = q(and(pr(LESS, x, fn(SUCC, x)), q(or(pr(LESS, y, x), pr(LESS, fn(SUCC, x), y))).forAll(y))).forAll(x);
		successor.setLabel("successor");

		// predecessor : all X (pred(succ(X)) = X) & (succ(pred(X)) = X)
		final Formula predecessor = q(and(eq(fn(PRED, fn(SUCC, x)), x), eq(fn(SUCC, fn(PRED, x)), x))).forAll(x);
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
