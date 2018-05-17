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
package org.eclipse.reddeer.core.lookup;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolItem;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.lookup.WidgetLookup;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Tool item lookup provides methods for looking up various tool items.
 * 
 * @author rhopp
 *
 */
public class ToolItemLookup {
	
	private static final Logger logger = Logger.getLogger(ToolItemLookup.class);

	private static ToolItemLookup instance;

	private ToolItemLookup() { }
	
	/**
	 * Gets instance of ToolItemLookup.
	 * 
	 * @return ToolItemLookup instance
	 */
	public static ToolItemLookup getInstance() {
		if (instance == null) {
			instance = new ToolItemLookup();
		}
		return instance;
	}

	/**
	 * Gets tool item matching specified matcher with specified index located within specified referenced composite.
	 * 
	 * @param rc referenced composite to search for tool items
	 * @param index index of tool item
	 * @param matchers matchers to match tool item
	 * @return tool item matching specified matcher with specified index located within specified referenced composite
	 */
	public ToolItem getToolItem(ReferencedComposite rc, int index,
			Matcher<?>... matchers) {
		
		if (rc == null){
			rc = findReferencedComposite();
		}
		return WidgetLookup.getInstance().activeWidget(rc, ToolItem.class, index,
				matchers);
	}
	
	/**
	 * Finds current referenced composite.
	 *
	 * @return the referenced composite
	 */
	public ReferencedComposite findReferencedComposite(){
		Control control = null;
		Control activeWidgetParentControl = WidgetLookup.getInstance().getActiveWidgetParentControl();
		if (activeWidgetParentControl instanceof Shell){
			control = activeWidgetParentControl;
		}else{
			control = getWorkbenchControl(activeWidgetParentControl);
		}	
		return new GenericReferencedComposite(control);
	}

	private Control getWorkbenchControl(final Control activeWorkbenchPartReference) {
		Control control = Display.syncExec(new ResultRunnable<Control>() {

			@Override
			public Control run() {
				Control control = activeWorkbenchPartReference;
				while (!((control instanceof CTabFolder) || (control instanceof Shell))) {
										control = control.getParent();
									}
				return control;
			}
		});
		return control;
	}
	
	protected class GenericReferencedComposite implements ReferencedComposite{
		
		private Control control;
		
		/**
		 * Instantiates a new generic referenced composite.
		 *
		 * @param control the control
		 */
		public GenericReferencedComposite(Control control) {
			this.control = control;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.reddeer.core.reference.ReferencedComposite#getControl()
		 */
		@Override
		public Control getControl() {
			return control;
		}
		
	}
	
}
