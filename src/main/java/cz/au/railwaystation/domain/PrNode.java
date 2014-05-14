package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Predicate;
import cz.au.railwaystation.fol.Term;

public class PrNode extends Predicate {

	public static final String NAME = "node";

	public PrNode(Term node) {
		super(NAME, node);
	}
	
}
