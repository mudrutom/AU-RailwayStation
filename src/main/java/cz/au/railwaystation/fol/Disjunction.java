package cz.au.railwaystation.fol;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class Disjunction extends Formula implements Iterable<Formula> {

	private final List<Formula> formulas;

	public Disjunction(Formula... formulas) {
		this(Arrays.asList(checkNotNull(formulas)));
	}

	public Disjunction(List<Formula> formulas) {
		this.formulas = Collections.unmodifiableList(checkNotNull(formulas));
	}

	public List<Formula> getFormulas() {
		return formulas;
	}

	public int size() {
		return formulas.size();
	}

	@Override
	public Iterator<Formula> iterator() {
		return formulas.iterator();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Disjunction)) return false;

		final Disjunction disjunction = (Disjunction) o;
		return formulas.equals(disjunction.formulas);
	}

	@Override
	public int hashCode() {
		return formulas.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (Formula formula : this) {
			sb.append(formula).append(" | ");
		}
		sb.delete(sb.length() - 3, sb.length());
		return sb.toString();
	}
}
