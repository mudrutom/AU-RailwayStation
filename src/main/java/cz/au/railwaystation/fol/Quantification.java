package cz.au.railwaystation.fol;

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

	public Quantification forAll(Variable variable) {
		checkNotNull(variable);
		checkArgument(!general.contains(variable));
		checkArgument(!existential.contains(variable));
		variables.add(variable);
		general.add(variable);
		return this;
	}

	public Quantification exists(Variable variable) {
		checkNotNull(variable);
		checkArgument(!general.contains(variable));
		checkArgument(!existential.contains(variable));
		variables.add(variable);
		existential.add(variable);
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Quantification)) return false;

		final Quantification that = (Quantification) o;
		return formula.equals(that.formula) && variables.equals(that.variables);
	}

	@Override
	public int hashCode() {
		int result = formula.hashCode();
		result = 31 * result + variables.hashCode();
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("(");
		for (Variable variable : variables) {
			if (general.contains(variable)) {
				sb.append("all ").append(variable).append(' ');
			}
			if (existential.contains(variable)) {
				sb.append(" exists ").append(variable).append(' ');
			}
		}
		return sb.append(formula).append(')').toString();
	}
}
