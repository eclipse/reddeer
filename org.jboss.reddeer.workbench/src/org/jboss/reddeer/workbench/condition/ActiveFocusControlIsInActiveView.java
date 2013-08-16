package org.jboss.reddeer.workbench.condition;

import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;
import org.jboss.reddeer.swt.lookup.impl.WorkbenchLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Returns true if active Control is in active View
 * 
 * @author Vlado Pakan
 *
 */
public class ActiveFocusControlIsInActiveView implements WaitCondition {
	@Override
	public boolean test() {
		// get active workbench part control (active view)
		final Control workbenchControl = WorkbenchLookup
				.getWorkbenchControl(WorkbenchLookup.findActiveWorkbenchPart());
		// get focused control
		final Control focusedControl = WidgetLookup.getInstance().getFocusControl();
		Boolean result = Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				Control itParent = focusedControl;
				while (itParent != workbenchControl && itParent != null) {
					itParent = itParent.getParent();
				}
				return itParent != null;
			}
		});
		return result;
	}
	
	@Override
	public String description() {
		return "Control has specified parent";
	}
}
