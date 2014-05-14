package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Predicate;
import cz.au.railwaystation.fol.Term;

public class PrOpen extends Predicate {

	public static final String NAME = "open";

	public PrOpen(Term time, Term node) {
		super(NAME, time, node);
	}
	
}
