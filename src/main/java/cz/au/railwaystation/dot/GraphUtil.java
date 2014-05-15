package cz.au.railwaystation.dot;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GraphUtil {

	private GraphUtil() {}

	public static Graph parseGraph(BufferedReader input) throws IOException {
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

	public static GraphPaths findAllPaths(Graph graph) {
		final Set<Node> starts = graph.getSources();
		final Set<Node> ends = graph.getSinks();
		final GraphPaths graphPaths = new GraphPaths(starts.size(), ends.size());

		for (Node start : starts) {
			for (Node end : ends) {
				findAllPaths(start, end, new LinkedList<Node>(), graphPaths);
			}
		}
		return graphPaths;
	}

	private static void findAllPaths(Node from, Node end, List<Node> path, GraphPaths graphPaths) {
		path.add(from);
		if (from.isSink()) {
			if (from.equals(end)) {
				// path leads to the end
				graphPaths.addPath(new Path(path));
			}
		} else {
			final ArrayList<Node> pathHead = new ArrayList<Node>(path);
			final Iterator<Node> children = from.getChildren().iterator();

			findAllPaths(children.next(), end, path, graphPaths);
			while (children.hasNext()) {
				findAllPaths(children.next(), end, new LinkedList<Node>(pathHead), graphPaths);
			}
		}
	}

	public static Set<Path> getIntersectingPaths(GraphPaths graphPaths, Path path) {
		final Set<Path> intersection = new LinkedHashSet<Path>();
		for (Path other : graphPaths.getAllPaths()) {
			for (Node node : path) {
				if (other.contains(node)) {
					intersection.add(other);
					break;
				}
			}
		}
		// exclude the path itself
		intersection.remove(path);
		return intersection;
	}

}
