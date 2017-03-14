/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.reddeer.core.test.condition;

import org.jboss.reddeer.core.condition.WidgetIsDisposed;
import org.jboss.reddeer.core.test.condition.shells.TableShell;
import org.jboss.reddeer.core.test.condition.shells.TreeShell;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
*
* @author Jan Novak <jnovak@redhat.com>
*/
@RunWith(RedDeerSuite.class)
public class WidgetIsDisposedTest {

	private TreeShell tree;
	private TableShell table;

	@Test
	public void testDisposedTree() {
		tree = new TreeShell();
		List<WidgetIsDisposed> disposeChecks = getTreeDisposeChecks();
		assertNotDisposed(disposeChecks);
		closeShells();
		assertDisposed(disposeChecks);
	}

	@Test
	public void testDisposedTable() {
		table = new TableShell();
		List<WidgetIsDisposed> disposeChecks = getTableDisposeChecks();
		assertNotDisposed(disposeChecks);
		closeShells();
		assertDisposed(disposeChecks);
	}
	
	@After
	public void closeShells() {
		if (tree != null) {
			tree.close();
			tree = null;
		}
		if (table != null) {
			table.close();
			table = null;
		}
	}

	private List<WidgetIsDisposed> getTreeDisposeChecks() {
		List<WidgetIsDisposed> disposeChecks = new ArrayList<>();
		disposeChecks.add(new WidgetIsDisposed(new DefaultShell().getSWTWidget()));

		for (TreeItem treeItem : tree.dfs()){
			disposeChecks.add(new WidgetIsDisposed(treeItem.getSWTWidget()));
		}

		return disposeChecks;
	}
	

	private List<WidgetIsDisposed> getTableDisposeChecks() {
		List<WidgetIsDisposed> disposeChecks = new ArrayList<>();
		disposeChecks.add(new WidgetIsDisposed(new DefaultShell().getSWTWidget()));

		for (TableItem tableItem : table.getFirstTwentyItems()){
			disposeChecks.add(new WidgetIsDisposed(tableItem.getSWTWidget()));
		}

		return disposeChecks;
	}


	private void assertDisposed(List<WidgetIsDisposed> disposeChecks) {
		for (WidgetIsDisposed disposeCheck : disposeChecks) {
			assertTrue("Fail, widget is not disposed!", disposeCheck.test());
		}
	}

	private void assertNotDisposed(List<WidgetIsDisposed> disposeChecks) {
		for (WidgetIsDisposed disposeCheck : disposeChecks) {
			assertFalse("Fail, widget is disposed!", disposeCheck.test());
		}
	}

}
