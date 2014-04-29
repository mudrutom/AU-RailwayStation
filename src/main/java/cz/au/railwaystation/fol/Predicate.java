package cz.au.railwaystation.fol;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Predicate extends Formula {

	private final String name;
	private final Term[] params;
	private final int arity;

	public Predicate(String name, Term... params) {
		this.name = checkNotNull(name);
		this.params = checkNotNull(params);
		arity = params.length;
		checkArgument(arity >= 0);
	}

	public Predicate(String name) {
		this(name, new Term[0]);
	}

	public String getName() {
		return name;
	}

	public Term[] getParams() {
		return params;
	}

	public int getArity() {
		return arity;
	}

	public Term getParam(int index) {
		checkArgument(index < arity);
		return params[index];
	}

	public void setParam(int index, Term term) {
		checkArgument(index < arity);
		params[index] = checkNotNull(term);
	}

	@Override
	public Appendable print(Appendable out, OutputFormat format) throws IOException {
		out.append(name.toLowerCase().replaceAll("[\\s]", "_")).append('(');
		for (Iterator<Term> i = Arrays.asList(params).iterator(); i.hasNext(); ) {
			i.next().print(out, format).append(i.hasNext() ? ", " : ")");
		}
		return out;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Predicate)) return false;

		final Predicate predicate = (Predicate) o;
		return name.equals(predicate.name) && arity == predicate.arity && Arrays.equals(params, predicate.params);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + arity;
		result = 31 * result + Arrays.hashCode(params);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("pr[").append(name).append('(');
		for (Iterator<Term> i = Arrays.asList(params).iterator(); i.hasNext(); ) {
			sb.append(i.next().toString()).append(i.hasNext() ? ", " : ")");
		}
		return sb.append(']').toString();
	}
}
