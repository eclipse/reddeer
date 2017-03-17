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
package org.jboss.reddeer.eclipse.debug.ui.views.launch;

import java.util.List;

import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

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
