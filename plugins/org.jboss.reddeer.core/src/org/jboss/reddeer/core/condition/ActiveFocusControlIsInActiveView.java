package org.jboss.reddeer.core.condition;

import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.core.lookup.WidgetLookup;
import org.jboss.reddeer.core.lookup.WorkbenchPartLookup;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * Condition is met when active focused control is in active view.
 * 
 * @author Vlado Pakan
 *
 */
public class ActiveFocusControlIsInActiveView extends AbstractWaitCondition {
	@Override
	public boolean test() {
		// get active workbench part control (active view)
		final Control workbenchControl = WorkbenchPartLookup.getInstance()
				.getWorkbenchControl(WorkbenchPartLookup.getInstance().findActiveWorkbenchPartReference());
		
		// get focused control
		final Control focusedControl = WidgetLookup.getInstance().getFocusControl();
		Boolean result = Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				Control itParent = focusedControl;
				while (itParent != workbenchControl && itParent != null && !itParent.isDisposed()) {
					itParent = itParent.getParent();
				}
				return itParent != null;
			}
		});
		return result;
	}
	
	@Override
	public String description() {
		return "control has specified parent";
	}
}
