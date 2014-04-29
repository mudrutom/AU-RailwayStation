package cz.au.railwaystation.fol;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Function extends Term {

	private final String name;
	private final Term[] args;
	private final int arity;

	public Function(String name, Term... args) {
		this.name = checkNotNull(name);
		this.args = checkNotNull(args);
		arity = args.length;
		checkArgument(arity > 0);
	}

	public String getName() {
		return name;
	}

	public Term[] getArgs() {
		return args;
	}

	public int getArity() {
		return arity;
	}

	public Term getArg(int index) {
		checkArgument(index < arity);
		return args[index];
	}

	public void setArg(int index, Term term) {
		checkArgument(index < arity);
		args[index] = checkNotNull(term);
	}

	@Override
	public Appendable print(Appendable out, OutputFormat format) throws IOException {
		out.append(name.toLowerCase().replaceAll("[\\s]", "_")).append('(');
		for (Iterator<Term> i = Arrays.asList(args).iterator(); i.hasNext(); ) {
			i.next().print(out, format).append(i.hasNext() ? ", " : ")");
		}
		return out;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Function)) return false;

		final Function function = (Function) o;
		return name.equals(function.name) && arity == function.arity && Arrays.equals(args, function.args);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + arity;
		result = 31 * result + Arrays.hashCode(args);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("fn[").append(name).append('(');
		for (Iterator<Term> i = Arrays.asList(args).iterator(); i.hasNext(); ) {
			sb.append(i.next().toString()).append(i.hasNext() ? ", " : ")");
		}
		return sb.append(']').toString();
	}
}
