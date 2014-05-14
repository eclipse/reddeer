package org.jboss.reddeer.gef.matcher;

import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Returns true if an object is instance of ToolEntry.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class IsToolEntry extends BaseMatcher<PaletteEntry> {

	@Override
	public boolean matches(Object obj) {
		return obj instanceof ToolEntry;
	}

	@Override
	public void describeTo(Description desc) {
		desc.appendText("is ToolEntry");
	}

}
