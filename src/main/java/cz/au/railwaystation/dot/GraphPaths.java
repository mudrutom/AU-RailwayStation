package cz.au.railwaystation.dot;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class GraphPaths {

	private Table<Node, Node, Set<Path>> graphPaths;

	public GraphPaths() {
		graphPaths = HashBasedTable.create();
	}

	public GraphPaths(int starts, int ends) {
		graphPaths = HashBasedTable.create(starts, ends);
	}

	public Set<Path> getPaths(Node start, Node end) {
		return graphPaths.get(start, end);
	}

	public void addPath(Node start, Node end, Path path) {
		checkNotNull(start); checkNotNull(end);
		checkNotNull(path);
		if (!graphPaths.contains(start, end)) {
			graphPaths.put(start, end, new HashSet<Path>());
		}
		final Set<Path> paths = graphPaths.get(start, end);
		path.setIndex(paths.size());
		paths.add(path);
	}

	public void addPath(Path path) {
		checkNotNull(path);
		addPath(path.getStart(), path.getEnd(), path);
	}

	public List<Path> getAllPaths() {
		final LinkedList<Path> paths = new LinkedList<Path>();
		for (Set<Path> pathSet : graphPaths.values()) {
			paths.addAll(pathSet);
		}
		return paths;
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
		for (Table.Cell<Node, Node, Set<Path>> cell : graphPaths.cellSet()) {
			sb.append(String.format("(%s->%s)=%s\n", cell.getRowKey(), cell.getColumnKey(), cell.getValue()));
		}
		return sb.toString();
	}
}
