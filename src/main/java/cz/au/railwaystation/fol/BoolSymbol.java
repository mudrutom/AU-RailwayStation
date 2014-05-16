package cz.au.railwaystation.fol;

import java.io.IOException;

public class BoolSymbol extends Formula {

	private final boolean value;

	public BoolSymbol(boolean value) {
		this.value = value;
	}

	public boolean get() {
		return value;
	}

	@Override
	public Appendable print(Appendable out, OutputFormat format) throws IOException {
		final String t, f;
		switch (format) {
			case TPTP: t = "$true"; f = "$false"; break;
			case LADR: t = "$T"; f = "$F"; break;
			default: throw new IllegalArgumentException(format.name());
		}
		return out.append(value ? t : f);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BoolSymbol)) return false;

		final BoolSymbol that = (BoolSymbol) o;
		return value == that.value;
	}

	@Override
	public int hashCode() {
		return value ? 1 : 0;
	}

	@Override
	public String toString() {
		return String.format("b[%b]", value);
	}
}
