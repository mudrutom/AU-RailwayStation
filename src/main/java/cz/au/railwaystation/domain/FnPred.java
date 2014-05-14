package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Function;
import cz.au.railwaystation.fol.Term;

public class FnPred extends Function {

	public static final String NAME = "pred";

	public FnPred(Term i) {
		super(NAME, i);
	}

}
