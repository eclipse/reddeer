/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.test.impl.table;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.matcher.WithIdMatcher;
import org.eclipse.reddeer.swt.api.Table;
import org.eclipse.reddeer.swt.condition.TableContainsItem;
import org.eclipse.reddeer.swt.exception.SWTLayerException;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.matcher.CheckedTableItemMatcher;
import org.junit.Test;

public class DefaultTableTest extends AbstractTableTest {

	@Test
	public void testMultiSelectionTable() {
		new DefaultTable().select(1, 2, 3, 4, 5);
	}

	@Test
	public void testMultiSelectionTableWithSingleSelection() {
		new DefaultTable().select(1);
	}

	@Test(expected = RedDeerException.class)
	public void testMultiSelectionTableCheck() {
		new DefaultTable().getItem(2).setChecked(true);
	}

	@Test
	public void testSingleSelectionTable() {
		new DefaultTable(1).select(1);
	}

	@Test
	public void testTableContainsItem() {
		assertTrue(new DefaultTable(1).containsItem("x"));
		assertTrue(new DefaultTable(1).containsItem("line 5 in nowhere", 6));
		assertFalse(new DefaultTable(1).containsItem("this is not in table"));
	}

	@Test
	public void testDeselect() {
		DefaultTable table = new DefaultTable();

		/* select at least something */
		table.select(1);

		int selected = 0;
		List<org.eclipse.reddeer.swt.api.TableItem> items = table.getItems();
		for (int i = 0; i < items.size(); i++)
			selected += (items.get(i).isSelected() ? 1 : 0);

		assertTrue("Table should have at least one selected item", selected >= 1);

		/* deselect all */
		table.deselectAll();

		selected = 0;
		items = table.getItems();
		for (int i = 0; i < items.size(); i++)
			selected += (items.get(i).isSelected() ? 1 : 0);

		assertTrue("Table should have no selected items", selected == 0);
	}

	@Test(expected = RedDeerException.class)
	public void testSingleSelectionTableWithMultiSelection() {
		new DefaultTable(1).select(1, 2, 3, 4);
	}

	@Test(expected = RedDeerException.class)
	public void testSingleSelectionTableCheck() {
		new DefaultTable(1).getItem(1).setChecked(true);
	}

	@Test
	public void testSelectTableItem() {
		new DefaultTable(1).getItem("line " + 100 + " in nowhere", 6).select();
		assertTrue(new DefaultTable(1).getItem("line " + 100 + " in nowhere", 6).isSelected());
	}

	@Test
	public void testCheckTableSelection() {
		new DefaultTable(2).select(1);
	}

	@Test(expected = RedDeerException.class)
	public void testCheckTableWithMultiSelection() {
		new DefaultTable(2).select(1, 2, 3, 4);
	}

	@Test
	public void testCheckTable() {
		DefaultTable t = new DefaultTable(2);
		t.getItem(1).setChecked(true);

		assertThat(t.getItems(new CheckedTableItemMatcher()).size(), is(1));
	}

	@Test
	public void doubleClickOnTableItem() {
		org.eclipse.reddeer.swt.api.TableItem item = new DefaultTable(1).getItem(0);
		assertEquals("x", item.getText());
		item.doubleClick();
		assertEquals("double click", item.getText());
	}

	@Test
	public void waitForTableItem() {
		new WaitUntil(new TableContainsItem(new DefaultTable(1), "!", 2));
	}

	@Test(expected = WaitTimeoutExpiredException.class)
	public void waitForNonExistingTableItem() {
		new WaitUntil(new TableContainsItem(new DefaultTable(1), "this does not exist", 2));
	}

	@Test
	public void indexOfTest() {
		DefaultTable table = new DefaultTable();
		assertEquals(1, table.indexOf(table.getItem(1)));
	}

	@Test
	public void testHeaders() {
		DefaultTable table = new DefaultTable();
		List<String> headers = table.getHeaders();
		assertTrue("Header on index 0 is not correct", headers.get(0).equals(" "));
		assertTrue("Header on index 1 is not correct", headers.get(1).equals("C"));
		assertTrue("Header on index 2 is not correct", headers.get(2).equals("!"));
		assertTrue("Header on index 3 is not correct", headers.get(3).equals("Description"));
		assertTrue("Header on index 4 is not correct", headers.get(4).equals("Resource"));
		assertTrue("Header on index 5 is not correct", headers.get(5).equals("In Folder"));
		assertTrue("Header on index 6 is not correct", headers.get(6).equals("Location"));
	}

	@Test
	public void testGetIndexOfHeader() {
		DefaultTable table = new DefaultTable();
		assertTrue("Header index is not correct for header ' '", table.getHeaderIndex(" ") == 0);
		assertTrue("Header index is not correct for header 'C'", table.getHeaderIndex("C") == 1);
		assertTrue("Header index is not correct for header '!'", table.getHeaderIndex("!") == 2);
		assertTrue("Header index is not correct for header 'Description'", table.getHeaderIndex("Description") == 3);
		assertTrue("Header index is not correct for header 'Resource'", table.getHeaderIndex("Resource") == 4);
		assertTrue("Header index is not correct for header 'In Folder'", table.getHeaderIndex("In Folder") == 5);
		assertTrue("Header index is not correct for header 'Location'", table.getHeaderIndex("Location") == 6);
	}

	@Test
	public void testGetHeaderOnSpecifiedIndex() {
		DefaultTable table = new DefaultTable();
		assertTrue("Header on index 0 is not correct", table.getHeader(0).equals(" "));
		assertTrue("Header on index 1 is not correct", table.getHeader(1).equals("C"));
		assertTrue("Header on index 2 is not correct", table.getHeader(2).equals("!"));
		assertTrue("Header on index 3 is not correct", table.getHeader(3).equals("Description"));
		assertTrue("Header on index 4 is not correct", table.getHeader(4).equals("Resource"));
		assertTrue("Header on index 5 is not correct", table.getHeader(5).equals("In Folder"));
		assertTrue("Header on index 6 is not correct", table.getHeader(6).equals("Location"));
	}

	@Test(expected = SWTLayerException.class)
	public void testGetNegativeIndexHeader() {
		new DefaultTable().getHeader(-1);
	}

	@Test(expected = SWTLayerException.class)
	public void testGetTooHighIndexHeader() {
		new DefaultTable().getHeader(10);
	}

	@Test(expected = SWTLayerException.class)
	public void testGetIndexOfNonexistingHeader() {
		new DefaultTable().getHeaderIndex("I do not exist");
	}

	@Test
	public void testGetSelectedItemsSingle() {
		org.eclipse.reddeer.swt.api.Table table = new DefaultTable();
		table.select(2);
		List<org.eclipse.reddeer.swt.api.TableItem> selectedTableItems = table.getSelectedItems();
		int numSelected = selectedTableItems.size();
		assertTrue("Only one Table Item has to be selected but " + numSelected + " are.", numSelected == 1);
		org.eclipse.reddeer.swt.api.TableItem expectedSelectedItem = table.getItem(2);
		org.eclipse.reddeer.swt.api.TableItem selectedItem = selectedTableItems.get(0);
		assertTrue("Selected tree item is not correct: " + selectedItem.getText(),
				expectedSelectedItem.getText(6).equals(selectedItem.getText(6)));
	}

	@Test
	public void testGetSelectedItemsMulti() {
		org.eclipse.reddeer.swt.api.Table table = new DefaultTable();
		table.select(1, 3);
		List<org.eclipse.reddeer.swt.api.TableItem> selectedTableItems = table.getSelectedItems();
		int numSelected = selectedTableItems.size();
		assertTrue("Two Table Item has to be selected but " + numSelected + " are.", numSelected == 2);
		org.eclipse.reddeer.swt.api.TableItem expectedSelectedItem = table.getItem(1);
		org.eclipse.reddeer.swt.api.TableItem selectedItem = selectedTableItems.get(0);
		assertTrue("Selected tree item is not correct: " + selectedItem.getText(),
				expectedSelectedItem.getText(6).equals(selectedItem.getText(6)));
		expectedSelectedItem = table.getItem(3);
		selectedItem = selectedTableItems.get(1);
		assertTrue("Selected tree item is not correct: " + selectedItem.getText(),
				expectedSelectedItem.getText(6).equals(selectedItem.getText(6)));

	}

	@Test
	public void testFindingTableBytId() {
		// index is started by 0
		Table expectedTable = new DefaultTable(1);
		Table foundTable = new DefaultTable(new WithIdMatcher("id", "table2"));
		assertEquals(expectedTable.getSWTWidget(), foundTable.getSWTWidget());
	}

}
