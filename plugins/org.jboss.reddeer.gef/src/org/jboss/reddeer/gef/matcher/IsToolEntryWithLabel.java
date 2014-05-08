package org.jboss.reddeer.gef.matcher;

import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Matches tool entry with a given label.
 * 
 * @author apodhrad
 *
 */
public class IsToolEntryWithLabel extends BaseMatcher<PaletteEntry> {

	private String label;

	public IsToolEntryWithLabel(String label) {
		this.label = label;
	}

	@Override
	public boolean matches(Object obj) {
		if (obj instanceof ToolEntry) {
			ToolEntry toolEntry = (ToolEntry) obj;
			return toolEntry.getLabel().equals(label);
		}
		return false;
	}

	@Override
	public void describeTo(Description desc) {
		desc.appendText("is ToolEntry with label '" + label + "'");
	}

}
