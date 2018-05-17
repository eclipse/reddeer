/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.condition;

import org.eclipse.swt.widgets.Control;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.lookup.WidgetLookup;
import org.eclipse.reddeer.workbench.core.lookup.WorkbenchPartLookup;

/**
 * Condition is met when active focused control is in active view.
 * 
 * @author Vlado Pakan
 *
 */
public class ActiveFocusControlIsInActiveView extends AbstractWaitCondition {
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		// get active workbench part control (active view)
		final Control workbenchControl = WorkbenchPartLookup.getInstance()
				.getWorkbenchControl(WorkbenchPartLookup.getInstance().getActiveWorkbenchPartReference());
		
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
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "control has specified parent";
	}
}
