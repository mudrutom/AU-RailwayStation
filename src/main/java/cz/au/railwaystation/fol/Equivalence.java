package cz.au.railwaystation.fol;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

public class Equivalence extends Formula {

	private final Formula leftSide, rightSide;

	public Equivalence(Formula leftSide, Formula rightSide) {
		this.leftSide = checkNotNull(leftSide);
		this.rightSide = checkNotNull(rightSide);
	}

	public Formula getLeftSide() {
		return leftSide;
	}

	public Formula getRightSide() {
		return rightSide;
	}

	@Override
	public Appendable print(Appendable out, OutputFormat format) throws IOException {
		final String eqv;
		switch (format) {
			case TPTP: eqv = " <=> "; break;
			case LADR: eqv = " <-> "; break;
			default: throw new IllegalArgumentException(format.name());
		}
		leftSide.print(out.append('('), format).append(eqv);
		rightSide.print(out, format).append(')');
		return out;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Equivalence)) return false;

		final Equivalence equivalence = (Equivalence) o;
		return leftSide.equals(equivalence.leftSide) && rightSide.equals(equivalence.rightSide);
	}

	@Override
	public int hashCode() {
		int result = leftSide.hashCode();
		result = 31 * result + rightSide.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return String.format("eqv[%s <-> %s]", leftSide.toString(), rightSide.toString());
	}
}
