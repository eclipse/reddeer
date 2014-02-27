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
public class UIFormSectionHandler {

	private static UIFormSectionHandler instance = null;

	private UIFormSectionHandler() {
	}

	public static UIFormSectionHandler getInstance() {
		if (instance == null)
			instance = new UIFormSectionHandler();
		return instance;
	}
	
	public void setExpanded(final Section section, final boolean expanded) {
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				section.setExpanded(expanded);
			}
		});
	}
}
