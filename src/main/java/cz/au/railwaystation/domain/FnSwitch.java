package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Function;
import cz.au.railwaystation.fol.Term;

public class FnSwitch extends Function {

	public static final String NAME = "switch";

	public FnSwitch(Term time, Term node) {
		super(NAME, time, node);
	}
	
}
