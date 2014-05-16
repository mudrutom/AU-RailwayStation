package cz.au.railwaystation.domain;

import cz.au.railwaystation.fol.Predicate;
import cz.au.railwaystation.fol.Term;

public class PrTrain extends Predicate {

	public static final String NAME = "train";

	public PrTrain(Term train) {
		super(NAME, train);
	}
	
}
