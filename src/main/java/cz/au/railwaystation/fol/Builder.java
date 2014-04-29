package cz.au.railwaystation.fol;

public class Builder {

	private Builder() {}

	public static Variable var(String name) {
		return new Variable(name);
	}

	public static Constant con(String name) {
		return new Constant(name);
	}

	public static Function fn(String name, Term... args) {
		return new Function(name, args);
	}

	public static Predicate pr(String name, Term... args) {
		return new Predicate(name, args);
	}

	public static Predicate pr(String name) {
		return new Predicate(name);
	}

	public static Negation neg(Formula formula) {
		return new Negation(formula);
	}

	public static Conjunction and(Formula... formulas) {
		return new Conjunction(formulas);
	}

	public static Disjunction or(Formula... formulas) {
		return new Disjunction(formulas);
	}

	public static Implication imp(Formula left, Formula right) {
		return new Implication(left, right);
	}

	public static Equivalence eqv(Formula left, Formula right) {
		return new Equivalence(left, right);
	}

	public static Equality eq(Term left, Term right) {
		return new Equality(left, right);
	}

	public static NonEquality neq(Term left, Term right) {
		return new NonEquality(left, right);
	}

	public static Quantification q(Formula formula) {
		return new Quantification(formula);
	}

}
