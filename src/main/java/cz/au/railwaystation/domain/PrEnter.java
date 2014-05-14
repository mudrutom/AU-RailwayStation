package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Predicate;
import cz.au.railwaystation.fol.Term;

public class PrEnter extends Predicate {

	public static final String NAME = "enter";

	public PrEnter(Term time, Term train, Term node) {
		super(NAME, time, train, node);
	}

}
