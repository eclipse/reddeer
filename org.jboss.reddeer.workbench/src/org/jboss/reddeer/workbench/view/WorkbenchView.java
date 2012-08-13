package org.jboss.reddeer.workbench.view;

import org.jboss.reddeer.swt.util.Bot;

/**
 * Represents view which is already opened in workbench
 * 
 * @author jjankovi
 *
 */
public class WorkbenchView extends View {

	public WorkbenchView(String title) {
		viewObject = Bot.get().viewByTitle(title);
	}
	
}
