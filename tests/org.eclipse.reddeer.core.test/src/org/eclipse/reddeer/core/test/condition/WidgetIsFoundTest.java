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
package org.eclipse.reddeer.core.test.condition;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.condition.WidgetIsFound;
import org.eclipse.reddeer.core.matcher.ClassMatcher;
import org.eclipse.reddeer.core.test.condition.matchers.TableItemWithRegExpMatcher;
import org.eclipse.reddeer.core.test.condition.shells.TableShell;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 *
 * @author Jan Novak <jnovak@redhat.com>
 * @author odockal <odockal@redhat.com>
 */
public class WidgetIsFoundTest {

	private TableShell table;

	@Before
	public void setUp() {
		table = new TableShell();
	}

	@Test
	public void testSimple() {
		WidgetIsFound isFound = new WidgetIsFound(TableItem.class);
		assertTrue(isFound.test());
	}

	@Test
	public void testMatcher() {
		int searchIndex = 3;
		String regExp = "[\\s.]*line " + searchIndex + " in nowhere[\\s.]*";

		WidgetIsFound isFound = new WidgetIsFound(TableItem.class, new TableItemWithRegExpMatcher(regExp));
		assertTrue(isFound.test());
	}

	@Test
	public void testParent() {
		for (DefaultTable table : new ArrayList<>(
				Arrays.asList(new DefaultTable(0), new DefaultTable(1), new DefaultTable(2)))) {

			WidgetIsFound isFound = new WidgetIsFound(TableItem.class, table.getControl(),
					new ClassMatcher(TableItem.class));
			assertTrue(isFound.test());
			assertFoundInCorrectTable(isFound, table);
		}
	}

	@Test
	public void testIndex() {
		for (int testedIndex = 0, testMax = 5; testedIndex < testMax; testedIndex++) {
			WidgetIsFound isFound = new WidgetIsFound(TableItem.class, new DefaultShell().getControl(), testedIndex,
					new ClassMatcher(TableItem.class));
			assertTrue(isFound.test());
			assertIndexMatch(testedIndex, isFound);
		}
	}

	@Test
	public void testNotFoundAfterClose() {
		List<WidgetIsFound> conditions = new ArrayList<>();

		for (int testedIndex = 0, testMax = 5; testedIndex < testMax; testedIndex++) {
			WidgetIsFound isFound = new WidgetIsFound(TableItem.class, new DefaultShell().getControl(), testedIndex,
					new ClassMatcher(TableItem.class));
			conditions.add(testedIndex, isFound);
		}

		closeTable();
		for (WidgetIsFound condition : conditions) {
			assertFalse(condition.test());
		}
	}
	
	@Test
	public void testGetResult() {
		WidgetIsFound isFound = new WidgetIsFound(TableItem.class);
		assertTrue(isFound.test());	
		assertThat(isFound.getResult(), instanceOf(TableItem.class));
		closeTable();
		assertFalse(isFound.test());
		assertNull(isFound.getResult());
	}

	@After
	public void closeTable() {
		if (table != null) {
			table.close();
			table = null;
		}
	}

	private void assertFoundInCorrectTable(WidgetIsFound isFound, DefaultTable table) {
		Table containingTable = getParent((TableItem) isFound.getResult());
		int actual = getTableStyle(containingTable);
		int expected = getTableStyle(table.getSWTWidget());
		assertEquals(expected, actual);
	}

	private Table getParent(TableItem item) {
		return Display.syncExec((ResultRunnable<Table>) item::getParent);
	}

	private int getTableStyle(Table table) {
		return Display.syncExec((ResultRunnable<Integer>) table::getStyle);
	}

	private void assertIndexMatch(int expectedIndex, WidgetIsFound isFound) {
		String indexCell = table.getIndexCell((TableItem) isFound.getResult());
		assertTrue(indexCell.contains(String.valueOf(expectedIndex)));
	}

}
