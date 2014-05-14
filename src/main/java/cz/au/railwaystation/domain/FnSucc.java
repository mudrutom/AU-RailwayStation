package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Function;
import cz.au.railwaystation.fol.Term;

public class FnSucc extends Function {

	public static final String NAME = "succ";

	public FnSucc(Term i) {
		super(NAME, i);
	}

}
