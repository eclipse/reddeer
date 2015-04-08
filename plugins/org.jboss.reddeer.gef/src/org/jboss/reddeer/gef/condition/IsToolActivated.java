package org.jboss.reddeer.gef.condition;

import org.jboss.reddeer.gef.api.Palette;
import org.jboss.reddeer.common.condition.WaitCondition;

/**
 * Returns true if a given tool is active in the specified palette.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class IsToolActivated implements WaitCondition {

	private Palette palette;
	private String tool;

	/**
	 * Constructs a condition which is fulfilled if a given tool is active in the specified palette.
	 * 
	 * @param palette
	 *            Palette
	 * @param label
	 *            Tool label
	 */
	public IsToolActivated(Palette palette, String label) {
		this.palette = palette;
		this.tool = label;
	}

	@Override
	public boolean test() {
		return palette.getActiveTool().equals(tool);
	}

	@Override
	public String description() {
		return "tool with label '" + tool + "' is activated";
	}

}
