package cz.au.railwaystation.fol;

import static com.google.common.base.Preconditions.checkNotNull;

public class Constant extends Term {

	private final String name;

	public Constant(String name) {
		this.name = checkNotNull(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Constant)) return false;

		final Constant constant = (Constant) o;
		return name.equals(constant.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}
}
