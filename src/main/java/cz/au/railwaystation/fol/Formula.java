package cz.au.railwaystation.fol;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public abstract class Formula {

	private String type = "axiom";
	private String label = null;
	private String comment = null;

	public Formula type(String type) {
		checkArgument(StringUtils.isNotBlank(type));
		this.type = type;
		return this;
	}

	public Formula label(String label) {
		this.label = label;
		return this;
	}

	public Formula comment(String comment) {
		this.comment = comment;
		return this;
	}

	public Appendable printFormula(Appendable out, OutputFormat format) throws  IOException {
		checkNotNull(out);
		checkNotNull(format);

		final String prefix, suffix;
		switch (format) {
			case TPTP:
				checkState(label != null);
				prefix = String.format("fof(%s, %s, (%n   ", label.replaceAll("[\\s]", "_"), type);
				suffix = String.format("%n)).");
				break;
			case LADR:
				prefix = "";
				suffix = StringUtils.isBlank(label) ? "." : String.format(" # label(%s).", label);
				break;
			default: throw new IllegalArgumentException(format.name());
		}

		if (comment != null) {
			out.append("% ").append(comment).append(String.format("%n"));
		}
		return print(out.append(prefix), format).append(suffix).append(String.format("%n"));
	}

	public abstract Appendable print(Appendable out, OutputFormat format) throws IOException;

}
