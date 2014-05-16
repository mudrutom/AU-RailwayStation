package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Predicate;
import cz.au.railwaystation.fol.Term;

public class PrFree extends Predicate {

	public static final String NAME = "free";

	public PrFree(Term time, Term path) {
		super(NAME, time, path);
	}
	
}
