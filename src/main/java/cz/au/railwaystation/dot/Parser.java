package cz.au.railwaystation.dot;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class Parser {

	public static Graph parse(BufferedReader input) throws IOException {
		final Graph graph = new Graph();

		String line;
		while ((line = input.readLine()) != null) {
			line = line.trim();
			if (line.startsWith("}") || line.endsWith("{")) continue;

			line = line.replaceAll("[;\\s]", "");
			String[] nodes = StringUtils.split(line, "->");
			int len = nodes.length - 1;
			for (int i = 0; i < len; i++) {
				graph.edge(nodes[i], nodes[i + 1]);
			}
		}

		graph.analyze();
		return graph;
	}

}
