package cz.au.railwaystation.fol;

import static com.google.common.base.Preconditions.checkNotNull;

public class Equality extends Formula {

	private final Formula leftSide, rightSide;

	public Equality(Formula leftSide, Formula rightSide) {
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Equality)) return false;

		final Equality equality = (Equality) o;
		return leftSide.equals(equality.leftSide) && rightSide.equals(equality.rightSide);
	}

	@Override
	public int hashCode() {
		int result = leftSide.hashCode();
		result = 31 * result + rightSide.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return leftSide.toString() + " = " + rightSide.toString();
	}
}
