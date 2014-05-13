package cz.au.railwaystation.dot;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class GraphPaths {

	private Table<Node, Node, Set<List<Node>>> graphPaths;

	public GraphPaths() {
		graphPaths = HashBasedTable.create();
	}

	public GraphPaths(int starts, int ends) {
		graphPaths = HashBasedTable.create(starts, ends);
	}

	public Set<List<Node>> getPaths(Node start, Node end) {
		return graphPaths.get(start, end);
	}

	public void addPath(Node start, Node end, List<Node> path) {
		checkNotNull(start); checkNotNull(end);
		checkNotNull(path); checkArgument(!path.isEmpty());
		if (!graphPaths.contains(start, end)) {
			graphPaths.put(start, end, new HashSet<List<Node>>());
		}
		final Set<List<Node>> paths = graphPaths.get(start, end);
		paths.add(path);
	}

	public void addPath(List<Node> path) {
		checkNotNull(path); checkArgument(!path.isEmpty());
		final Node start = path.get(0);
		final Node end = path.get(path.size() - 1);
		addPath(start, end, path);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GraphPaths)) return false;

		final GraphPaths that = (GraphPaths) o;
		return graphPaths.equals(that.graphPaths);
	}

	@Override
	public int hashCode() {
		return graphPaths.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (Table.Cell<Node, Node, Set<List<Node>>> cell : graphPaths.cellSet()) {
			sb.append(String.format("(%s->%s)=%s\n", cell.getRowKey(), cell.getColumnKey(), cell.getValue()));
		}
		return sb.toString();
	}
}
