package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Predicate;
import cz.au.railwaystation.fol.Term;

public class PrTime extends Predicate {

	public static final String NAME = "time";

	public PrTime(Term time) {
		super(NAME, time);
	}
	
}
