package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Predicate;
import cz.au.railwaystation.fol.Term;

public class PrAt extends Predicate {

	public static final String NAME = "at";

	public PrAt(Term time, Term train, Term node) {
		super(NAME, time, train, node);
	}

}
