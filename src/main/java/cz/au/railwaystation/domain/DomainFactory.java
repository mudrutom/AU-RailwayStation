package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Factory;
import cz.au.railwaystation.fol.Term;

public class DomainFactory extends Factory {

	protected DomainFactory() {}

	public static PrNode node(Term node) {
		return new PrNode(node);
	}

	public static PrPath path(Term path) {
		return new PrPath(path);
	}

	public static PrTime time(Term time) {
		return new PrTime(time);
	}

	public static PrTrain train(Term train) {
		return new PrTrain(train);
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

	public static FnGate gate(Term train) {
		return new FnGate(train);
	}

	public static PrConf conf(Term time, Term path) {
		return new PrConf(time, path);
	}

	public static PrFree free(Term time, Term path) {
		return new PrFree(time, path);
	}

	public static PrReady ready(Term time, Term path) {
		return new PrReady(time, path);
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
