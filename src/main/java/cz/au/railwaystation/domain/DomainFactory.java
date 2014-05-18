package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Constant;
import cz.au.railwaystation.fol.Factory;
import cz.au.railwaystation.fol.Function;
import cz.au.railwaystation.fol.Predicate;
import cz.au.railwaystation.fol.Term;
import cz.au.railwaystation.fol.Variable;

import static com.google.common.base.Preconditions.checkState;

public class DomainFactory extends Factory {

	public static final String ERR_MSG = "USE_CONS is true";

	private static boolean USE_CONS = true;

	protected DomainFactory() {}

	public static void useConstantsAsParameters(boolean use) {
		USE_CONS = use;
	}

	public static Predicate at(Term time, Term train, Constant node) {
		final String name = USE_CONS ? "at" : "at_" + node.getName();
		return USE_CONS ? new Predicate(name, time, train, node) : new Predicate(name, time, train);
	}

	public static Predicate at(Term time, Term train, Variable node) {
		checkState(!USE_CONS, ERR_MSG);
		return new Predicate("at", time, train, node);
	}

	public static Predicate enter(Term time, Term train, Constant node) {
		final String name = USE_CONS ? "enter" : "enter_" + node.getName();
		return USE_CONS ? new Predicate(name, time, train, node) : new Predicate(name, time, train);
	}

	public static Predicate enter(Term time, Term train, Variable node) {
		checkState(!USE_CONS, ERR_MSG);
		return new Predicate("enter", time, train, node);
	}

	public static Predicate goes(Term time, Constant node) {
		final String name = USE_CONS ? "goes" : "goes_" + node.getName();
		return USE_CONS ? new Predicate(name, time, node) : new Predicate(name, time);
	}

	public static Predicate goes(Term time, Variable node) {
		checkState(!USE_CONS, ERR_MSG);
		return new Predicate("goes", time, node);
	}

	public static Predicate open(Term time, Constant node) {
		final String name = USE_CONS ? "open" : "open_" + node.getName();
		return USE_CONS ? new Predicate(name, time, node) : new Predicate(name, time);
	}

	public static Predicate open(Term time, Variable node) {
		checkState(!USE_CONS, ERR_MSG);
		return new Predicate("open", time, node);
	}

	public static Function swtch(Term time, Constant node) {
		final String name = USE_CONS ? "switch" : "switch_" + node.getName();
		return USE_CONS ? new Function(name, time, node) : new Function(name, time);
	}

	public static Function swtch(Term time, Variable node) {
		checkState(!USE_CONS, ERR_MSG);
		return new Function("switch", time, node);
	}

	public static Function gate(Term train) {
		return new Function("gate", train);
	}

	public static Predicate conf(Term time, Constant path) {
		final String name = USE_CONS ? "conf" : "conf_" + path.getName();
		return USE_CONS ? new Predicate(name, time, path) : new Predicate(name, time);
	}

	public static Predicate conf(Term time, Variable path) {
		checkState(!USE_CONS, ERR_MSG);
		return new Predicate("conf", time, path);
	}

	public static Predicate free(Term time, Constant path) {
		final String name = USE_CONS ? "free" : "free_" + path.getName();
		return USE_CONS ? new Predicate(name, time, path) : new Predicate(name, time);
	}

	public static Predicate free(Term time, Variable path) {
		checkState(!USE_CONS, ERR_MSG);
		return new Predicate("free", time, path);
	}

	public static Predicate ready(Term time, Constant path) {
		final String name = USE_CONS ? "ready" : "ready_" + path.getName();
		return USE_CONS ? new Predicate(name, time, path) : new Predicate(name, time);
	}

	public static Predicate ready(Term time, Variable path) {
		checkState(!USE_CONS, ERR_MSG);
		return new Predicate("ready", time, path);
	}

	public static Function clock(Term time) {
		return new Function("clock", time);
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
