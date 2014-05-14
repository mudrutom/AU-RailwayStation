package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Predicate;
import cz.au.railwaystation.fol.Term;

public class PrLess extends Predicate {

	public static final String NAME = "less";

	public PrLess(Term i, Term j) {
		super(NAME, i, j);
	}
	
}
