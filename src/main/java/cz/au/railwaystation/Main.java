package cz.au.railwaystation;

import cz.au.railwaystation.fol.OutputFormat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

		final BufferedReader input = new BufferedReader(new FileReader("example/station-1.dot"));
		final ModelBuilder builder = new ModelBuilder(input, new File("example"));
		builder.setFormat(OutputFormat.TPTP);
		builder.createStationLayoutAxioms();

	}

}
