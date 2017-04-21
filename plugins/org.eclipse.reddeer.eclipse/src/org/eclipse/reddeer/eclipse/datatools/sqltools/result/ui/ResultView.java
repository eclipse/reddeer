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
package org.eclipse.reddeer.eclipse.datatools.sqltools.result.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.platform.RunningPlatform;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.swt.api.Tree;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.TreeHasChildren;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;

/**
 * SQL Result View RedDeer implementation
 * 
 * @author Jiri Peterka
 *
 */
public class ResultView extends WorkbenchView {

	/**
	 * Create instance and looks for Result view.
	 */
	public ResultView() {
		super("SQL Results");
	}

	/**
	 * Return SQL results.
	 * 
	 * @return list of SQLResults
	 */
	public List<SQLResult> getResults() {
		open();
		Tree tree = getViewTree();
		List<TreeItem> items = tree.getItems();
		List<SQLResult> results = new ArrayList<SQLResult>();

		for (TreeItem item : items) {

			results.add(new SQLResult(item.getCell(0), item.getCell(1), item
					.getCell(2), item.getCell(3)));

		}
		return results;
	}

	/**
	 * Removes all visible results from the SQL Result View.
	 */
	public void removeAllResults() {
		open();
		Tree tree = getViewTree();
		new WaitUntil(new TreeHasChildren(tree),TimePeriod.DEFAULT, false);
		String tooltip = "Remove All Visible Results (Shift+Delete)";
		if (RunningPlatform.isOSX()) {
			tooltip = "Remove All Visible Results (⇧⌦)";
		}
		DefaultToolItem item = new DefaultToolItem(cTabItem.getFolder(), tooltip);
		item.click();
		new WaitWhile(new TreeHasChildren(tree), TimePeriod.LONG);
	}
	
	private Tree getViewTree(){
		return new DefaultTree(cTabItem);
	}

}
