package cz.au.railwaystation;

import cz.au.railwaystation.dot.Graph;
import cz.au.railwaystation.dot.GraphUtil;
import cz.au.railwaystation.fol.OutputFormat;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static final String USAGE = "USAGE: java -jar AU-RailwayStation.jar [-tptp] [-ladr] <station.dot> [<out_dir>]";
	public static final OutputFormat DEFAULT = OutputFormat.TPTP;

    public static void main(String[] args) {
		final Arg arg = processArgs(args);

		// parse the input railway station
		Graph graph = null;
		try {
			graph = GraphUtil.parseGraph(new FileReader(arg.input));
		} catch (IOException e) {
			exit(e);
		}

		// create the axioms for the given graph
		final ModelBuilder model = new ModelBuilder(graph, arg.outDir);
		model.useConstantsAsParameters(true);
		model.setFormat(arg.format);
		try {
			model.createOrderAxioms();
			model.createStationLayoutAxioms();
			model.createStationControlAxioms();
		} catch (IOException e) {
			exit(e);
		}

		// create the proofs for the given graph
		final ProofBuilder proofs = new ProofBuilder(graph, arg.outDir);
		proofs.setFormat(arg.format);
		try {
			proofs.createDerailsProofs();
			proofs.createCollisionsProofs();
			proofs.createSignalsProofs();
		} catch (IOException e) {
			exit(e);
		}

		exit(null);
	}

	private static void exit(Exception e) {
		if (e != null) {
			System.err.println("An " + e.getClass().getSimpleName() + " has occurred: " + e.getMessage());
			System.exit(1);
		} else {
			System.exit(0);
		}
	}

	private static Arg processArgs(String[] args) {
		if (args.length < 1 || args.length > 3) printUsage();

		File input, output;
		OutputFormat format = null;

		if (args[0].startsWith("-") && args.length >= 2) {
			if (args[0].equalsIgnoreCase("-tptp")) format = OutputFormat.TPTP;
			else if (args[0].equalsIgnoreCase("-ladr")) format = OutputFormat.LADR;
			else printUsage();
			input = new File(args[1]);
			output = (args.length >= 3) ? new File(args[2]) : input.getParentFile();
		} else {
			format = DEFAULT;
			input = new File(args[0]);
			output = (args.length >= 2) ? new File(args[1]) : input.getParentFile();
		}

		if (output == null || (!output.exists() && !output.mkdirs())) {
			output = new File(System.getProperty("user.dir"));
		}

		return new Arg(input, output, format);
	}

	private static void printUsage() {
		System.out.println(USAGE);
		System.exit(0);
	}

	private static final class Arg {
		public File input, outDir;
		public OutputFormat format;

		private Arg(File input, File outDir, OutputFormat format) {
			this.input = input;
			this.outDir = outDir;
			this.format = format;
		}
	}
}
