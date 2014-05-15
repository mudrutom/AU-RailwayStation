package cz.au.railwaystation.dot;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Path implements Iterable<Node> {

	private final List<Node> path;
	private final Node start;
	private final Node end;

	private int index = 0;

	public Path(Node start, Node end, List<Node> path) {
		this.path = Collections.unmodifiableList(checkNotNull(path));
		checkArgument(!path.isEmpty());
		this.start = checkNotNull(start);
		checkArgument(start.equals(path.get(0)));
		this.end = checkNotNull(end);
		checkArgument(end.equals(path.get(path.size() - 1)));
	}

	public Path(List<Node> path) {
		this(path.get(0), path.get(path.size() - 1), path);
	}

	public Path(Iterable<Node> path) {
		this(Lists.newArrayList(path));
	}

	public Path(Node... path) {
		this(Lists.newArrayList(path));
	}

	public Node getStart() {
		return start;
	}

	public Node getEnd() {
		return end;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int size() {
		return path.size();
	}

	public boolean contains(Node node) {
		return path.contains(node);
	}

	@Override
	public Iterator<Node> iterator() {
		return path.iterator();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Path)) return false;

		final Path that = (Path) o;
		return path.equals(that.path);
	}

	@Override
	public int hashCode() {
		return path.hashCode();
	}

	@Override
	public String toString() {
		return path.toString();
	}
}
