package cz.au.railwaystation.fol;

import static com.google.common.base.Preconditions.checkNotNull;

public class Negation extends Formula {

	private final Formula formula;

	public Negation(Formula formula) {
		this.formula = checkNotNull(formula);
	}

	public Formula getFormula() {
		return formula;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Negation)) return false;

		final Negation negation = (Negation) o;
		return formula.equals(negation.formula);
	}

	@Override
	public int hashCode() {
		return formula.hashCode();
	}

	@Override
	public String toString() {
		return "-" + formula.toString();
	}
}
