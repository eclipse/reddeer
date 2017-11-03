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
package org.eclipse.reddeer.swt.test.impl.table;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.reddeer.swt.test.SWTLayerTestCase;

/**
 *
 * @author Jan Novak <jnovak@redhat.com>
 */
public abstract class AbstractTableTest extends SWTLayerTestCase {

	@Override
	protected void createControls(Shell shell){
		shell.setLayout(new GridLayout());
		createMULTITable(shell);
		createSINGLETable(shell);
		createCHECKTable(shell);
	}

	private void createMULTITable(Shell shell){
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;

		Table table1 = new Table(shell, SWT.MULTI);
		table1.setLinesVisible (true);
		table1.setHeaderVisible (true);
		table1.setLayout(new GridLayout());
		table1.setLayoutData(data);
		table1.setData("id", "table1");
		String[] titles = {" ", "C", "!", "Description", "Resource", "In Folder", "Location"};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (table1, SWT.NONE);
			column.setText (titles [i]);
		}
		int count = 128;
		for (int i=0; i<count; i++) {
			TableItem item = new TableItem (table1, SWT.NONE);
			item.setText (0, "x");
			item.setText (1, "y");
			item.setText (2, "!");
			item.setText (3, "this stuff behaves the way I expect");
			item.setText (4, "almost everywhere");
			item.setText (5, "some.folder");
			item.setText (6, "line " + i + " in nowhere");
		}
		for (int i=0; i<titles.length; i++) {
			table1.getColumn (i).pack();
		}
	}

	private void createSINGLETable(Shell shell){
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;

		Table table2 = new Table(shell, SWT.SINGLE);
		table2.setLinesVisible (true);
		table2.setHeaderVisible (true);
		table2.setLayout(new GridLayout());
		table2.setLayoutData(data);
		table2.setData("id", "table2");
		String[] titles = {" ", "C", "!", "Description", "Resource", "In Folder", "Location"};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (table2, SWT.NONE);
			column.setText (titles [i]);
		}
		int count = 128;
		for (int i=0; i<count; i++) {
			TableItem item = new TableItem (table2, SWT.NONE);
			item.setText (0, "x");
			item.setText (1, "y");
			item.setText (2, "!");
			item.setText (3, "this stuff behaves the way I expect");
			item.setText (4, "almost everywhere");
			item.setText (5, "some.folder");
			item.setText (6, "line " + i + " in nowhere");
		}
		for (int i=0; i<titles.length; i++) {
			table2.getColumn (i).pack();
		}

		table2.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {

			}

			@Override
			public void mouseDown(MouseEvent arg0) {

			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				Table table = ((Table)arg0.widget);
				int index = table.getSelectionIndex();
				TableItem doubleClickedItem = table.getItem(index);
				doubleClickedItem.setText(0,"double click");
			}
		});
	}

	private void createCHECKTable(Shell shell){
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		Table table3 = new Table(shell, SWT.CHECK);
		table3.setLinesVisible (true);
		table3.setHeaderVisible (true);
		table3.setLayout(new GridLayout());
		table3.setLayoutData(data);
		table3.setData("id", "table3");
		String[] titles = {" ", "C", "!", "Description", "Resource", "In Folder", "Location"};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (table3, SWT.NONE);
			column.setText (titles [i]);
		}
		int count = 128;
		for (int i=0; i<count; i++) {
			TableItem item = new TableItem (table3, SWT.NONE);
			item.setText (0, "x");
			item.setText (1, "y");
			item.setText (2, "!");
			item.setText (3, "this stuff behaves the way I expect");
			item.setText (4, "almost everywhere");
			item.setText (5, "some.folder");
			item.setText (6, "line " + i + " in nowhere");
		}
		for (int i=0; i<titles.length; i++) {
			table3.getColumn (i).pack();
		}
	}

}
