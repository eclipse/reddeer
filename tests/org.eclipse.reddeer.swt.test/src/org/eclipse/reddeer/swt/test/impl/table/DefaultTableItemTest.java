/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
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
import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.table.DefaultTableItem;
import org.eclipse.reddeer.swt.test.SWTLayerTestCase;
import org.junit.Test;

public class DefaultTableItemTest extends SWTLayerTestCase{

	@Override
	protected void createControls(Shell shell){
		shell.setLayout(new GridLayout());
		createMULTITable(shell);
		createSINGLETable(shell);
	}
	
	private void createMULTITable(Shell shell){
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		
		Table table1 = new Table(shell, SWT.MULTI);
		table1.setLinesVisible (true);
		table1.setHeaderVisible (true);
		table1.setLayout(new GridLayout());
		table1.setLayoutData(data);
		String[] titles = {" ", "C", "!", "Description", "Resource", "In Folder", "Location"};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (table1, SWT.NONE);
			column.setText (titles [i]);
		}	
		int count = 128;
		for (int i=0; i<count; i++) {
			TableItem item = new TableItem (table1, SWT.NONE);
			item.setText (0, 0 + " " + i);
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
		String[] titles = {" ", "C", "!", "Description", "Resource", "In Folder", "Location"};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (table2, SWT.NONE);
			column.setText (titles [i]);
		}	
		int count = 128;
		for (int i=0; i<count; i++) {
			TableItem item = new TableItem (table2, SWT.NONE);
			item.setText (0, 1 + " " + i);
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
				Table table = ((Table)arg0.widget);
				int index = table.getSelectionIndex();
				TableItem doubleClickedItem = table.getItem(index);
				doubleClickedItem.setText(0,"single click");
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
	
	@Test
	public void defaultConstructor(){ 
		assertThat(new DefaultTableItem().getText(), is("0 0"));
	}
	
	@Test
	public void referencedComposite(){
		assertThat(new DefaultTableItem(new DefaultTable(1)).getText(), is("1 0"));
	}
	
	@Test
	public void index(){
		assertThat(new DefaultTableItem(1).getText(), is("0 1"));
	}
	
	@Test
	public void referencedComposite_index(){
		assertThat(new DefaultTableItem(new DefaultTable(1), 1).getText(), is("1 1"));
	}
	
	@Test
	public void text(){
		assertThat(new DefaultTableItem("0 3").getText(), is("0 3"));
	}
	
	@Test
	public void referencedComposite_text(){
		assertThat(new DefaultTableItem(new DefaultTable(1), "1 3").getText(), is("1 3"));
	}
	
	@Test
	public void click(){
		DefaultTableItem ti = new DefaultTableItem(new DefaultTable(1), "1 2");
		ti.click();
		assertEquals("single click",ti.getText());
	}
}
