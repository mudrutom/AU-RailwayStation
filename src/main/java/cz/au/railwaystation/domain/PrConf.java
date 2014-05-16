package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Predicate;
import cz.au.railwaystation.fol.Term;

public class PrConf extends Predicate {

	public static final String NAME = "conf";

	public PrConf(Term time, Term path) {
		super(NAME, time, path);
	}
	
}
