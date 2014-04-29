package cz.au.railwaystation.fol;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

public class NonEquality extends Formula {

	private final Term leftSide, rightSide;

	public NonEquality(Term leftSide, Term rightSide) {
		this.leftSide = checkNotNull(leftSide);
		this.rightSide = checkNotNull(rightSide);
	}

	public Term getLeftSide() {
		return leftSide;
	}

	public Term getRightSide() {
		return rightSide;
	}

	@Override
	public Appendable print(Appendable out, OutputFormat format) throws IOException {
		leftSide.print(out.append('('), format).append(" != ");
		rightSide.print(out, format).append(')');
		return out;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof NonEquality)) return false;

		final NonEquality nonEquality = (NonEquality) o;
		return leftSide.equals(nonEquality.leftSide) && rightSide.equals(nonEquality.rightSide);
	}

	@Override
	public int hashCode() {
		int result = leftSide.hashCode();
		result = 31 * result + rightSide.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return String.format("neq[%s != %s]", leftSide.toString(), rightSide.toString());
	}
}
