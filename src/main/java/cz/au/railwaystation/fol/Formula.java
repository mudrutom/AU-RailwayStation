package cz.au.railwaystation.fol;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public abstract class Formula {

	private String label = null;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Appendable printFormula(Appendable out, OutputFormat format) throws  IOException {
		checkNotNull(out);
		checkNotNull(format);

		final String prefix, suffix;
		switch (format) {
			case TPTP:
				checkState(label != null);
				prefix = String.format("fof(%s, axiom, (\n   ", label.replaceAll("[\\s]", "_"));
				suffix = String.format("\n)).");
				break;
			case LADR:
				prefix = "";
				suffix = StringUtils.isBlank(label) ? "." : String.format(" # label(%s).", label);
				break;
			default: throw new IllegalArgumentException(format.name());
		}

		return print(out.append(prefix), format).append(suffix).append(String.format("\n"));
	}

	public abstract Appendable print(Appendable out, OutputFormat format) throws IOException;

}
