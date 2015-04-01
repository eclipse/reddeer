package org.jboss.reddeer.core.condition;

import org.eclipse.ui.IViewPart;
import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.core.lookup.WorkbenchPartLookup;

/**
 * Tests if view with entered toolTip value is active in 
 * current workbench
 * 
 * @author jjankovi
 *
 */
public class ViewWithToolTipIsActive implements WaitCondition {

	private String toolTip;
	
	public ViewWithToolTipIsActive(String toolTip) {
		this.toolTip = toolTip;
	}
	
	@Override
	public boolean test() {
		for (IViewPart view : WorkbenchPartLookup.getInstance().getOpenViews()) {
			if (view.getTitleToolTip().equals(toolTip)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String description() {
		return "view with title '" + toolTip + "' is active";
	}

}
