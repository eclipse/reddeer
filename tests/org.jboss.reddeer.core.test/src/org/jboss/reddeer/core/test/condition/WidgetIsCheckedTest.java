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

import org.jboss.reddeer.core.condition.WidgetIsChecked;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.test.condition.shells.TableShell;
import org.jboss.reddeer.core.test.condition.shells.TreeShell;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
*
* @author Jan Novak <jnovak@redhat.com>
*/
@RunWith(RedDeerSuite.class)
public class WidgetIsCheckedTest {

	private TreeShell tree;
	private TableShell table;

	@Test
	public void testTreeItems() {
		tree = new TreeShell();
		for (TreeItem treeItem : tree.dfs()) {
			WidgetIsChecked checkedCondition = new WidgetIsChecked(treeItem.getSWTWidget());

			boolean isChecked = treeItem.isChecked();
			assertEquals(isChecked, checkedCondition.test());
			treeItem.setChecked(!isChecked);
			assertEquals(!isChecked, checkedCondition.test());
		}
	}

	@Test
	public void testTableItems() {
		table = new TableShell();
		for (TableItem tableItem : table.getFirstTwentyItems()) {
			WidgetIsChecked checked = new WidgetIsChecked(tableItem.getSWTWidget());

			boolean isChecked = tableItem.isChecked();
			assertEquals(isChecked, checked.test());
			tableItem.setChecked(!isChecked);
			assertEquals(!isChecked, checked.test());
		}
	}

	@Test
	public void testException() {
		try {
			new WidgetIsChecked(new DefaultShell().getSWTWidget()).test();
			fail("No exception was thrown!");
		} catch (Exception e){
			if (!(e.getCause() instanceof CoreLayerException)){
				fail("Unexpected exception!");
			}
		}
	}

	@After
	public void clean() {
		if (tree != null) {
			tree.close();
			tree = null;
		}
		if (table != null) {
			table.close();
			table = null;
		}
	}

}
