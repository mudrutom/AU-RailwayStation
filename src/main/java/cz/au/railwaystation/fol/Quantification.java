package cz.au.railwaystation.fol;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Quantification extends Formula {

	private final Formula formula;
	private final List<Variable> variables;
	private final Set<Variable> general, existential;

	public Quantification(Formula formula) {
		this.formula = checkNotNull(formula);
		variables = new LinkedList<Variable>();
		general = new LinkedHashSet<Variable>();
		existential = new LinkedHashSet<Variable>();
	}

	public Formula getFormula() {
		return formula;
	}

	public Quantification forAll(Variable... allVars) {
		checkNotNull(allVars);
		for (Variable variable : allVars) {
			checkNotNull(variable);
			checkArgument(!general.contains(variable) && !existential.contains(variable));
			variables.add(variable);
			general.add(variable);
		}
		return this;
	}

	public Quantification exists(Variable... existsVars) {
		checkNotNull(existsVars);
		for (Variable variable : existsVars) {
			checkNotNull(variable);
			checkArgument(!general.contains(variable) && !existential.contains(variable));
			variables.add(variable);
			existential.add(variable);
		}
		return this;
	}

	public List<Variable> getVariables() {
		return Collections.unmodifiableList(variables);
	}

	public Set<Variable> getGeneral() {
		return Collections.unmodifiableSet(general);
	}

	public Set<Variable> getExistential() {
		return Collections.unmodifiableSet(existential);
	}

	@Override
	public Appendable print(Appendable out, OutputFormat format) throws IOException {
		final String all, exists;
		switch (format) {
			case TPTP:
				all = "![%s]: ";
				exists = "?[%s]: ";
				break;
			case LADR:
				all = "all %s ";
				exists = "exists %s ";
				break;
			default: throw new IllegalArgumentException(format.name());
		}

		out.append('(');
		final StringBuilder sb = new StringBuilder();
		for (Variable variable : variables) {
			String var = variable.print(sb.delete(0, sb.length()), format).toString();
			if (general.contains(variable)) {
				out.append(String.format(all, var));
			}
			if (existential.contains(variable)) {
				out.append(String.format(exists, var));
			}
		}
		return formula.print(out, format).append(')');
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Quantification)) return false;

		final Quantification quantification = (Quantification) o;
		return formula.equals(quantification.formula) && variables.equals(quantification.variables);
	}

	@Override
	public int hashCode() {
		int result = formula.hashCode();
		result = 31 * result + variables.hashCode();
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (Variable variable : variables) {
			if (general.contains(variable)) {
				sb.append("all_").append(variable.toString()).append(' ');
			}
			if (existential.contains(variable)) {
				sb.append("ex_").append(variable.toString()).append(' ');
			}
		}
		return sb.append(formula.toString()).toString();
	}
}
