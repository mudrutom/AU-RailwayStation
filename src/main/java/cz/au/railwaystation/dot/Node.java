package cz.au.railwaystation.dot;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class Node {

	private final String name;
	private final List<Node> parents, children;

	public Node(String name) {
		this.name = checkNotNull(name);
		parents = new LinkedList<Node>();
		children = new LinkedList<Node>();
	}

	public String getName() {
		return name;
	}

	public void addParent(Node node) {
		parents.add(checkNotNull(node));
	}

	public List<Node> getParents() {
		return parents;
	}

	public void addChild(Node node) {
		children.add(checkNotNull(node));
	}

	public List<Node> getChildren() {
		return children;
	}

	public boolean isSource() {
		return parents.isEmpty();
	}

	public boolean isSink() {
		return children.isEmpty();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Node)) return false;

		final Node node = (Node) o;
		return name.equals(node.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}
}
