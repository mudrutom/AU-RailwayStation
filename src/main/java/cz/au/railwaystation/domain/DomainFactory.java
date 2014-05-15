package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Factory;
import cz.au.railwaystation.fol.Term;

public class DomainFactory extends Factory {

	protected DomainFactory() {}

	public static PrNode node(Term node) {
		return new PrNode(node);
	}

	public static PrAt at(Term time, Term train, Term node) {
		return new PrAt(time, train, node);
	}

	public static PrEnter enter(Term time, Term train, Term node) {
		return new PrEnter(time, train, node);
	}

	public static PrOpen open(Term time, Term node) {
		return new PrOpen(time, node);
	}

	public static PrGoes goes(Term time, Term node) {
		return new PrGoes(time, node);
	}

	public static FnSwitch swtch(Term time, Term node) {
		return new FnSwitch(time, node);
	}

	public static FnClock clock(Term time) {
		return new FnClock(time);
	}

	public static PrLess less(Term i, Term j) {
		return new PrLess(i, j);
	}

	public static FnSucc succ(Term i) {
		return new FnSucc(i);
	}

	public static FnPred pred(Term i) {
		return new FnPred(i);
	}

}
