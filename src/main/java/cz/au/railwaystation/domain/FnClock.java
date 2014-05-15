package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Function;
import cz.au.railwaystation.fol.Term;

public class FnClock extends Function {

	public static final String NAME = "clock";

	public FnClock(Term time) {
		super(NAME, time);
	}

}
