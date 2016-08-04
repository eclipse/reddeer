/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.core.lookup;

import java.util.List;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.exception.Thrower;
import org.jboss.reddeer.core.reference.DefaultReferencedComposite;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.jboss.reddeer.core.lookup.WidgetLookup;
import org.jboss.reddeer.core.lookup.ToolBarLookup;

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
	 * Gets workbench tool item matching specified matcher.
	 * 
	 * @param matcher matcher to match workbench tool item
	 * @return workbench tool item matching specified matcher
	 * @deprecated sice 1.1.0
	 */
	public ToolItem getWorkbenchToolItem(Matcher<String> matcher) {
		ToolBarLookup tl = ToolBarLookup.getInstance();
		List<ToolBar> workbenchToolBars = tl.getWorkbenchToolBars();
		
		Shell swtShell = ShellLookup.getInstance().getWorkbenchShell();
		if(swtShell == null){
			Thrower.objectIsNull(swtShell, "There is no active workbench shell");
		}
		
		final ToolItem ti = getToolItem(new DefaultReferencedComposite((Control) swtShell), 0, matcher);
		
		ToolBar tb = Display.syncExec(new ResultRunnable<ToolBar>() {
			@Override
			public ToolBar run() {
				return ti.getParent();
			}
		});
		if (workbenchToolBars.contains(tb)) {
			return ti;
		} else {
			Thrower.objectIsNull(ti, "ToolItem matching " + matcher.toString()
					+ " cannot be found in any workbench toolbar");
		}
		return null;
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
		 * @see org.jboss.reddeer.core.reference.ReferencedComposite#getControl()
		 */
		@Override
		public Control getControl() {
			return control;
		}
		
	}
	
}
