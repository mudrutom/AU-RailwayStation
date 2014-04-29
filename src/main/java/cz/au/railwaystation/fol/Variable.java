package cz.au.railwaystation.fol;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

public class Variable extends Term {

	private final String name;

	public Variable(String name) {
		this.name = checkNotNull(name);
	}

	public String getName() {
		return name;
	}

	@Override
	public Appendable print(Appendable out, OutputFormat format) throws IOException {
		return out.append(name.toUpperCase().replaceAll("[\\s]", "_"));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Variable)) return false;

		final Variable variable = (Variable) o;
		return name.equals(variable.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return String.format("var[%s]", name);
	}
}
