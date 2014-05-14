package cz.au.railwaystation.fol;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class Conjunction extends Formula implements Iterable<Formula> {

	private final List<Formula> formulas;

	public Conjunction(Formula... formulas) {
		this(Lists.newArrayList(checkNotNull(formulas)));
	}

	public Conjunction(List<Formula> formulas) {
		this.formulas = checkNotNull(formulas);
	}

	public List<Formula> getFormulas() {
		return formulas;
	}

	public Conjunction add(Formula formula) {
		formulas.add(checkNotNull(formula));
		return this;
	}

	public int size() {
		return formulas.size();
	}

	@Override
	public Appendable print(Appendable out, OutputFormat format) throws IOException {
		out.append('(');
		for (Iterator<Formula> i = iterator(); i.hasNext(); ) {
			i.next().print(out, format).append(i.hasNext() ? " & " : ")");
		}
		return out;
	}

	@Override
	public Iterator<Formula> iterator() {
		return formulas.iterator();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Conjunction)) return false;

		final Conjunction conjunction = (Conjunction) o;
		return formulas.equals(conjunction.formulas);
	}

	@Override
	public int hashCode() {
		return formulas.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder().append("and[");
		for (Iterator<Formula> i = iterator(); i.hasNext(); ) {
			sb.append(i.next().toString()).append(i.hasNext() ? " & " : "]");
		}
		return sb.toString();
	}
}
