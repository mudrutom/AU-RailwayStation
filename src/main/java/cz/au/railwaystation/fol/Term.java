package cz.au.railwaystation.fol;

import java.io.IOException;

public abstract class Term {

	public abstract Appendable print(Appendable out, OutputFormat format) throws IOException;

}
