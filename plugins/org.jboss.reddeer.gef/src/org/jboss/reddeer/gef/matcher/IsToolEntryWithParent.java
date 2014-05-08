package org.jboss.reddeer.gef.matcher;

import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Matches tool entry with a given parent.
 * 
 * @author apodhrad
 *
 */
public class IsToolEntryWithParent extends BaseMatcher<PaletteEntry> {

	private String label;

	public IsToolEntryWithParent(String label) {
		this.label = label;
	}

	@Override
	public boolean matches(Object obj) {
		if (obj instanceof ToolEntry) {
			ToolEntry toolEntry = (ToolEntry) obj;
			PaletteContainer parent = toolEntry.getParent();
			return parent != null && parent.getLabel().equals(label);
		}
		return false;
	}

	@Override
	public void describeTo(Description desc) {

	}

}
