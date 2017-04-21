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
package org.eclipse.reddeer.eclipse.debug.ui.views.launch;

import java.util.List;

import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Debug View
 * 
 * @author Andrej Podhradsky
 *
 */
public class LaunchView extends WorkbenchView {

	/**
	 * Instantiates a new debug view.
	 */
	public LaunchView() {
		super("Debug");
	}

	/**
	 * Gets a text of a selected tree item.
	 * 
	 * @return text of a selected tree item
	 */
	public String getSelectedText() {
		TreeItem selectedItem = getSelectedItem();
		if (selectedItem == null) {
			return null;
		}
		return selectedItem.getText();
	}

	/**
	 * Gets a selected tree item.
	 * 
	 * @return selected tree item
	 */
	public TreeItem getSelectedItem() {
		open();
		List<TreeItem> selectedItems = new DefaultTree().getSelectedItems();
		if (selectedItems.isEmpty()) {
			return null;
		}
		return selectedItems.get(0);
	}

}
