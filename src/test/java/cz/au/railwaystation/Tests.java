package cz.au.railwaystation;

import cz.au.railwaystation.dot.Graph;
import cz.au.railwaystation.dot.Parser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class Tests {

	private static final String GRAPH =
			"digraph GRAPH {\n" +
			"  in -> v;\n" +
			"  v -> out1;\n" +
			"  v -> out2;\n" +
			"}";

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

}
