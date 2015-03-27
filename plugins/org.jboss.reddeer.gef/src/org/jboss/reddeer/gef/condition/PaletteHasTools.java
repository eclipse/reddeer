package org.jboss.reddeer.gef.condition;

import org.jboss.reddeer.gef.api.Palette;
import org.jboss.reddeer.common.condition.WaitCondition;

/**
 * Returns true if a given palette has the specified number of tools or more.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class PaletteHasTools implements WaitCondition {

	private Palette palette;
	private int numberOfTools;

	/**
	 * Contructs a condition which is fulfilled if a given palette has at least 1 tool.
	 * 
	 * @param palette
	 *            Palette
	 */
	public PaletteHasTools(Palette palette) {
		this(palette, 1);
	}

	/**
	 * Contructs a condition which is fulfilled if a given palette has at least the specified number of tools.
	 * 
	 * @param palette
	 *            Palette
	 * @param numberOfTools
	 *            Number of tools
	 */
	public PaletteHasTools(Palette palette, int numberOfTools) {
		this.palette = palette;
		this.numberOfTools = numberOfTools;
	}

	@Override
	public boolean test() {
		return palette.getTools().size() >= numberOfTools;
	}

	@Override
	public String description() {
		return "palette has " + numberOfTools + " tools";
	}

}
