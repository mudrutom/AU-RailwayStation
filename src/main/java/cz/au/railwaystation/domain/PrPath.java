package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Predicate;
import cz.au.railwaystation.fol.Term;

public class PrPath extends Predicate {

	public static final String NAME = "path";

	public PrPath(Term path) {
		super(NAME, path);
	}
	
}
