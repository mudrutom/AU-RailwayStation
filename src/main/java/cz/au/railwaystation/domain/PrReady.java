package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Predicate;
import cz.au.railwaystation.fol.Term;

public class PrReady extends Predicate {

	public static final String NAME = "ready";

	public PrReady(Term time, Term path) {
		super(NAME, time, path);
	}
	
}
