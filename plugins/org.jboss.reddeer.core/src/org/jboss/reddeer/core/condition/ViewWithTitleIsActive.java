package org.jboss.reddeer.core.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.core.handler.WorkbenchPartHandler;

/**
 * Condition is met when view with specified title is active.
 * 
 * @author mlabuda@redhat.com
 * @since 0.8.0.
 *
 */
public class ViewWithTitleIsActive extends AbstractWaitCondition {

	private String title;
	private WorkbenchPartHandler workbenchPartHandler;
	
	/**
	 * Creates new ViewWithTitleIsActive wait condition.
	 * 
	 * @param title title of desired active view
	 */
	public ViewWithTitleIsActive(String title) {
		this.title = title;
		workbenchPartHandler = WorkbenchPartHandler.getInstance();
	}
	
	@Override
	public boolean test() {
		return title.equals(workbenchPartHandler.getActiveViewTitle());
	}

	@Override
	public String description() {
		return "view with title '" + title + "' is active";
	}

}
