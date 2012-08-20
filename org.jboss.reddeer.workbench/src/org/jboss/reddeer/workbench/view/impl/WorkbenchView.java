package org.jboss.reddeer.workbench.view.impl;

import org.jboss.reddeer.workbench.view.View;

/**
 * Represents general workbench view
 * 
 * @author jjankovi
 *
 */
public class WorkbenchView extends View {

	public WorkbenchView(String viewTitle) {
		super(viewTitle);
	}
	
	public WorkbenchView(String category, String viewTitle) {
		super(category, viewTitle);
	}
	
}
