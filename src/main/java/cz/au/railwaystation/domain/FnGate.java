package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Function;
import cz.au.railwaystation.fol.Term;

public class FnGate extends Function {

	public static final String NAME = "gate";

	public FnGate(Term train) {
		super(NAME, train);
	}

}
