package org.jboss.reddeer.uiforms.handler;

import org.eclipse.ui.forms.widgets.Section;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.uiforms.section.UIFormSection;

/**
 * Helper class for running methods on {@link UIFormSection} that require sync/async exec on UI thread. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class SectionHandler {

	private static SectionHandler instance = null;

	private SectionHandler() {
	}

	public static SectionHandler getInstance() {
		if (instance == null)
			instance = new SectionHandler();
		return instance;
	}
	
	/**
	 * Sets section's state
	 * 
	 * @param section
	 * @param expanded
	 */
	public void setExpanded(final Section section, final boolean expanded) {
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				section.setExpanded(expanded);
			}
		});
	}
}
