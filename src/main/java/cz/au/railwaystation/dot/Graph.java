package cz.au.railwaystation.dot;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class Graph implements Iterable<Node> {

	private final Map<String, Node> nodes;
	private final Set<Node> sources, sinks, innerNodes;
	private boolean isAnalyzed;

	public Graph() {
		nodes = new LinkedHashMap<String, Node>();
		sources = new LinkedHashSet<Node>();
		sinks = new LinkedHashSet<Node>();
		innerNodes = new LinkedHashSet<Node>();
		isAnalyzed = false;
	}

	public Node getNode(String name) {
		checkNotNull(name);
		Node node = nodes.get(name);
		if (node == null) {
			node = new Node(name);
			nodes.put(name, node);
		}
		return node;
	}

	public Graph edge(String fromNode, String toNode) {
		final Node from = getNode(fromNode);
		final Node to = getNode(toNode);
		from.addChild(to);
		to.addParent(from);
		isAnalyzed = false;
		return this;
	}

	protected void analyze() {
		sources.clear();
		sinks.clear();
		innerNodes.clear();
		for (Node node : this) {
			if (node.isSource()) {
				sources.add(node);
			} else if (node.isSink()) {
				sinks.add(node);
			} else {
				innerNodes.add(node);
			}
		}
		isAnalyzed = true;
	}

	public Set<Node> getSources() {
		if (!isAnalyzed) analyze();
		return sources;
	}

	public Set<Node> getSinks() {
		if (!isAnalyzed) analyze();
		return sinks;
	}

	public Set<Node> getInnerNodes() {
		if (!isAnalyzed) analyze();
		return innerNodes;
	}

	@Override
	public Iterator<Node> iterator() {
		return nodes.values().iterator();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Graph)) return false;

		final Graph graph = (Graph) o;
		return nodes.equals(graph.nodes);
	}

	@Override
	public int hashCode() {
		return nodes.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("digraph GRAPH {\n");
		for (Node node : this) {
			for (Node child : node.getChildren()) {
				sb.append("\t").append(node).append(" -> ").append(child).append(";\n");
			}
		}
		return sb.append("}").toString();
	}
}
