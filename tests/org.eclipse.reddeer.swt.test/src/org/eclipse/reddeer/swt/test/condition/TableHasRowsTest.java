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
package org.eclipse.reddeer.swt.test.condition;

import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.condition.TableHasRows;
import org.eclipse.reddeer.swt.impl.table.AbstractTableItem;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.test.SWTLayerTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests TableHasRow condition
 * 
 * @author Josef Kopriva
 *
 */
@RunWith(RedDeerSuite.class)
public class TableHasRowsTest extends SWTLayerTestCase {

	/*
	 * Create controls for layout and table.
	 * 
	 */
	@Override
	protected void createControls(Shell shell) {
		shell.setLayout(new GridLayout());
		createSINGLETable(shell);
		createEmptyTable(shell);
	}

	/*
	 * Creates table with filled values.
	 * 
	 * @param shell shell for tables
	 */
	private void createSINGLETable(Shell shell) {
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;

		Table table2 = new Table(shell, SWT.SINGLE);
		table2.setLinesVisible(true);
		table2.setHeaderVisible(true);
		table2.setLayout(new GridLayout());
		table2.setLayoutData(data);
		String[] titles = { " ", "C", "!", "Description", "Resource", "In Folder", "Location" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table2, SWT.NONE);
			column.setText(titles[i]);
		}
		int count = 128;
		for (int i = 0; i < count; i++) {
			TableItem item = new TableItem(table2, SWT.NONE);
			item.setText(0, "" + i);
			item.setText(1, "a" + 1);
			item.setText(2, "b" + i);
			item.setText(3, "c " + i);
			item.setText(4, "d" + i);
			item.setText(5, "e" + i);
			item.setText(6, "f" + i);
		}
		for (int i = 0; i < titles.length; i++) {
			table2.getColumn(i).pack();
		}

		table2.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				Table table = ((Table) arg0.widget);
				int index = table.getSelectionIndex();
				TableItem doubleClickedItem = table.getItem(index);
				doubleClickedItem.setText(0, "single click");
			}

			@Override
			public void mouseDown(MouseEvent arg0) {

			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				Table table = ((Table) arg0.widget);
				int index = table.getSelectionIndex();
				TableItem doubleClickedItem = table.getItem(index);
				doubleClickedItem.setText(0, "double click");
			}
		});
	}

	/*
	 * Creates empty table.
	 * 
	 * @param shell shell for tables
	 */
	private void createEmptyTable(Shell shell) {
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;

		Table table1 = new Table(shell, SWT.MULTI);
		table1.setLinesVisible(true);
		table1.setHeaderVisible(true);
		table1.setLayout(new GridLayout());
		table1.setLayoutData(data);
		String[] titles = { " ", "C", "!", "Description", "Resource", "In Folder", "Location" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table1, SWT.NONE);
			column.setText(titles[i]);
		}
		for (int i = 0; i < titles.length; i++) {
			table1.getColumn(i).pack();
		}
	}

	@Test
	public void tableHasRows() {
		assertThat("Table has rows", (new TableHasRows(new DefaultTable())).test());
	}

	@Test
	public void tableHasNotRows() {
		assertThat("Table has not rows", !(new TableHasRows(new DefaultTable(1))).test());
	}

	@Test
	public void tableCellColumnTest() {
		assertThat("First column in table has cell with 126",
				(new TableHasRows(new DefaultTable(0), new ColumnMatcher("126", 0)).test()));
	}

	@Test
	public void tableCellColumnNotTest() {
		assertThat("Table has not cell with regex .*abc.*",
				!(new TableHasRows(new DefaultTable(0), new ColumnMatcher("128", 2))).test());
	}

	@Test
	public void tableHasRowOutOfBoundExceptionTest() {
		assertThat("Table has not column 10", !new TableHasRows(new DefaultTable(), new ColumnMatcher("f", 10)).test());
	}

	@Test
	public void tableContainsCellWithTextTest() {
		assertThat("Table has not column 10", !new TableHasRows(new DefaultTable(), new AllTableMatcher("c5")).test());
	}

	/*
	 * ColumnMatcher for testing.
	 * 
	 * @param text text searched in specific column
	 * 
	 * @param column column with text
	 */
	public class ColumnMatcher extends BaseMatcher<TableItem> {
		private String text;
		private int column;

		public ColumnMatcher(String text, int column) {
			this.text = text;
			this.column = column;
		}

		public void describeTo(Description description) {
			description.appendText("Table cell contains text: " + text + "in column: " + column);
		}

		@Override
		public boolean matches(Object item) {
			AbstractTableItem i = (AbstractTableItem) item;
			return i.getText(column).contains(text);
		}
	}

	/*
	 * AllTableMatcher for testing.
	 * 
	 * @param text text searched in whole table
	 */
	public class AllTableMatcher extends BaseMatcher<TableItem> {
		private String text;

		public AllTableMatcher(String text) {
			this.text = text;
		}

		public void describeTo(Description description) {
			description.appendText("Table contains at least one cell with text: " + text);
		}

		@Override
		public boolean matches(Object item) {
			AbstractTableItem ti = (AbstractTableItem) item;
			for (int i = 0; i < 7; i++) {
				if (ti.getText(i).contains(text)) {
					return true;
				}
			}
			return false;
		}
	}
}
