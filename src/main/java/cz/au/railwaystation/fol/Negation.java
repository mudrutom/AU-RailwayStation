package cz.au.railwaystation.fol;

import java.io.IOException;

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
	public Appendable print(Appendable out, OutputFormat format) throws IOException {
		final char neg;
		switch (format) {
			case TPTP: neg = '~'; break;
			case LADR: neg = '-'; break;
			default: throw new IllegalArgumentException(format.name());
		}
		return formula.print(out.append(neg), format);
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
		return String.format("neg[%s]", formula);
	}
}
