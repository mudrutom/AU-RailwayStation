package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Factory;
import cz.au.railwaystation.fol.Function;
import cz.au.railwaystation.fol.Predicate;
import cz.au.railwaystation.fol.Term;

public class DomainFactory extends Factory {

	protected DomainFactory() {}

	public static Predicate at(Term time, Term train, Term node) {
		return new Predicate("at", time, train, node);
	}

	public static Predicate enter(Term time, Term train, Term node) {
		return new Predicate("enter", time, train, node);
	}

	public static Predicate open(Term time, Term node) {
		return new Predicate("open", time, node);
	}

	public static Predicate goes(Term time, Term node) {
		return new Predicate("goes", time, node);
	}

	public static Function swtch(Term time, Term node) {
		return new Function("switch", time, node);
	}

	public static Function clock(Term time) {
		return new Function("clock", time);
	}

	public static Function gate(Term train) {
		return new Function("gate", train);
	}

	public static Predicate conf(Term time, Term path) {
		return new Predicate("conf", time, path);
	}

	public static Predicate free(Term time, Term path) {
		return new Predicate("free", time, path);
	}

	public static Predicate ready(Term time, Term path) {
		return new Predicate("ready", time, path);
	}

	public static Predicate less(Term i, Term j) {
		return new Predicate("less", i, j);
	}

	public static Function succ(Term i) {
		return new Function("succ", i);
	}

	public static Function pred(Term i) {
		return new Function("pred", i);
	}

}
