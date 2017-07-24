/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.debug.ui.views.breakpoints;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.exception.SWTLayerException;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.tree.AbstractTreeItem;

/**
 * Represents a breakpoint which is defined as a tree item in the Breakpoints
 * view.
 * 
 * @author Andrej Podhradsky
 * @author Tomas Sedmik
 *
 */
public class Breakpoint extends AbstractTreeItem {

	private static Logger log = Logger.getLogger(Breakpoint.class);

	/**
	 * Constructs a breakpoint as a tree item.
	 * 
	 * @param treeItem
	 *            tree item in the Breakpoints view
	 */
	protected Breakpoint(TreeItem treeItem) {
		super(treeItem.getSWTWidget());
	}

	/**
	 * Removes the breakpoint.
	 */
	public void remove() {
		doOperation("Remove");
	}

	/**
	 * Disables the breakpoint.
	 */
	public void disable() {
		doOperation("Disable");
		new WaitUntil(new AbstractWaitCondition() {

			@Override
			public boolean test() {
				return !isChecked();
			}
		});
	}

	/**
	 * Enables the breakpoint.
	 */
	public void enable() {
		doOperation("Enable");
		new WaitUntil(new AbstractWaitCondition() {

			@Override
			public boolean test() {
				return isChecked();
			}
		});
	}

	/**
	 * Tries to perform given operation on the breakpoint. Operations are
	 * selected from the context menu of the breakpoint.
	 * 
	 * @param operation
	 *            Name of operation in the context menu
	 */
	public void doOperation(String operation) {
		log.debug("Performing '" + operation + "' on breakpoint: " + getText());
		select();
		ContextMenuItem menuitem = new ContextMenuItem(operation);
		if (menuitem.isEnabled()) {
			menuitem.select();
			log.debug("Operation '" + operation + "' was performed");
		} else {
			log.debug("Operation '" + operation + "' was NOT performed (item is not enabled)");
		}
	}

	/**
	 * Checks whether is the breakpoint enabled.
	 *
	 * @return true, if is enabled
	 */
	public boolean isEnabled() {
		return isOperationEnabled("Disable");
	}

	/**
	 * Checks whether is given operation enabled in the context menu of the
	 * breakpoint.
	 *
	 * @param operation            Name of operation in the context menu
	 * @return true - operation is available and enabled, false - otherwise
	 */
	public boolean isOperationEnabled(String operation) {
		log.debug("Checking operation '" + operation + "' on breakpoint:" + getText());
		select();
		try {
			ContextMenuItem menuitem = new ContextMenuItem(operation);
			return menuitem.isEnabled();
		} catch (SWTLayerException | CoreLayerException ex) {
			log.debug("Operation '" + operation + "' not found!");
			return false;
		}
	}
}
