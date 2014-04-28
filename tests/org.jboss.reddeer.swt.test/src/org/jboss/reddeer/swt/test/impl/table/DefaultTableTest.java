package org.jboss.reddeer.swt.test.impl.table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.jboss.reddeer.swt.condition.TableContainsItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.junit.Test;

public class DefaultTableTest extends SWTLayerTestCase{

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
	
	@Test
	public void testMultiSelectionTable(){
		new DefaultTable().select(1,2,3,4,5);
	}
	
	@Test
	public void testMultiSelectionTableWithSingleSelection(){
		new DefaultTable().select(1);
	}
	
	@Test(expected = SWTLayerException.class)
	public void testMultiSelectionTableCheck(){
		new DefaultTable().getItem(2).setChecked(true);
	}
	
	@Test
	public void testSingleSelectionTable(){
		new DefaultTable(1).select(1);
	}
	
	@Test
	public void testTableContainsItem(){
		assertTrue(new DefaultTable(1).containsItem("x"));
		assertTrue(new DefaultTable(1).containsItem("line 5 in nowhere",6));
		assertFalse(new DefaultTable(1).containsItem("this is not in table"));
	}

	@Test
	public void testDeselect() {
		DefaultTable table = new DefaultTable();

		/* select at least something */
		table.select(1);

		int selected = 0;
		List<org.jboss.reddeer.swt.api.TableItem> items = table.getItems();
		for(int i = 0; i < items.size(); i++)
			selected += (items.get(i).isSelected() ? 1 : 0);

		assertTrue("Table should have at least one selected item", selected >= 1);

		/* deselect all */
		table.deselectAll();

		selected = 0;
		items = table.getItems();
		for(int i = 0; i < items.size(); i++)
			selected += (items.get(i).isSelected() ? 1 : 0);

		assertTrue("Table should have no selected items", selected == 0);
	}

	@Test(expected = SWTLayerException.class)
	public void testSingleSelectionTableWithMultiSelection(){
		new DefaultTable(1).select(1,2,3,4);
	}
	
	@Test(expected = SWTLayerException.class)
	public void testSingleSelectionTableCheck(){
		new DefaultTable(1).getItem(1).setChecked(true);
	}

	@Test
	public void testSelectTableItem(){
		new DefaultTable(1).getItem("line " + 100 + " in nowhere",6).select();
		assertTrue(new DefaultTable(1).getItem("line " + 100 + " in nowhere",6).isSelected());
	}
	
	@Test
	public void testCheckTableSelection(){
		new DefaultTable(2).select(1);
	}
	
	@Test(expected = SWTLayerException.class)
	public void testCheckTableWithMultiSelection(){
		new DefaultTable(2).select(1,2,3,4);
	}
	
	@Test
	public void testCheckTable(){
		new DefaultTable(2).getItem(1).setChecked(true);
	}
	
	@Test
	public void doubleClickOnTableItem(){
		org.jboss.reddeer.swt.api.TableItem item = new DefaultTable(1).getItem(0);
		assertEquals("x",item.getText());
		item.doubleClick();
		assertEquals("double click",item.getText());
	}
	
	@Test
	public void waitForTableItem(){
		new WaitUntil(new TableContainsItem(new DefaultTable(1), "!", 2));
	}
	
	@Test(expected = WaitTimeoutExpiredException.class)
	public void waitForNonExistingTableItem(){
		new WaitUntil(new TableContainsItem(new DefaultTable(1), "this does not exist", 2));
	}
	
	@Test
	public void indexOfTest(){
		DefaultTable table = new DefaultTable();
		assertEquals(1, table.indexOf(table.getItem(1)));
	}
	
}
