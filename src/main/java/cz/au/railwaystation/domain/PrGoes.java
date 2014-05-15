package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Predicate;
import cz.au.railwaystation.fol.Term;

public class PrGoes extends Predicate {

	public static final String NAME = "goes";

	public PrGoes(Term time, Term node) {
		super(NAME, time, node);
	}
	
}
