package org.jboss.reddeer.core.condition;

import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.core.handler.WorkbenchPartHandler;

/**
 * Tests if view with entered toolTip value is active in 
 * current workbench
 * 
 * @author jjankovi
 * @deprecated since 0.8.0. Use {@link org.jboss.reddeer.core.condition.ViewWithTitleIsActive } instead.
 */
@Deprecated
public class ViewWithToolTipIsActive implements WaitCondition {

	private String toolTip;
	private WorkbenchPartHandler workbenchPartHandler;
	
	public ViewWithToolTipIsActive(String toolTip) {
		this.toolTip = toolTip;
		workbenchPartHandler = WorkbenchPartHandler.getInstance();
	}
	
	@Override
	public boolean test() {
		return toolTip.equals(workbenchPartHandler.getActiveViewTitle());
	}

	@Override
	public String description() {
		return "view with title '" + toolTip + "' is active";
	}

}
