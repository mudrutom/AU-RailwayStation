package cz.au.railwaystation.fol;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

public class Implication extends Formula {

	private final Formula leftSide, rightSide;

	public Implication(Formula leftSide, Formula rightSide) {
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
		final String imp;
		switch (format) {
			case TPTP: imp = " => "; break;
			case LADR: imp = " -> "; break;
			default: throw new IllegalArgumentException(format.name());
		}
		leftSide.print(out.append('('), format).append(imp);
		rightSide.print(out, format).append(')');
		return out;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Implication)) return false;

		final Implication implication = (Implication) o;
		return leftSide.equals(implication.leftSide) && rightSide.equals(implication.rightSide);
	}

	@Override
	public int hashCode() {
		int result = leftSide.hashCode();
		result = 31 * result + rightSide.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return String.format("imp[%s -> %s]", leftSide.toString(), rightSide.toString());
	}
}
