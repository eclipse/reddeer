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
package org.eclipse.reddeer.swt.test.impl.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.matcher.TreeItemRegexMatcher;
import org.eclipse.reddeer.core.matcher.TreeItemTextMatcher;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.swt.test.ui.views.TreeEventsListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DefaultTreeItemTest extends AbstractTreeTest {

	protected org.eclipse.reddeer.swt.api.Tree tree;
	
	private boolean threadAlreadyRunning = false;
	private Thread generateDynamicTreeItems = null;

	@Override
	protected void createControls(Shell shell) {
		super.createControls(shell);
	}
	
	@Before
	public void initTree(){
		tree = new DefaultTree();
	}

	@Test
	public void testFindExistingItemByPath(){
		createTreeItems(tree.getSWTWidget());
		final String expectedTreeItemText = "AAB";
		DefaultTreeItem dfi = new DefaultTreeItem("A","AA",expectedTreeItemText);
		assertTrue("Founded Tree Item has to have text " + expectedTreeItemText
				+ " but has " + dfi.getText(),
				dfi.getText().equals(expectedTreeItemText));
	}
	
	@Test
	public void testGetParentItem(){
		createTreeItems(tree.getSWTWidget());
		DefaultTreeItem dfi = new DefaultTreeItem("A","AA");
		TreeItem parentItem = dfi.getParentItem();
		assertEquals("A",parentItem.getText());
		
	}

	@SuppressWarnings("unused")
	@Test(expected = CoreLayerException.class)
	public void testFindNonExistingItemByPath(){
		createTreeItems(tree.getSWTWidget());
		DefaultTreeItem dfi = new DefaultTreeItem("A","AA","NONEXISTINGTEXT");
	}
	
	@Test
	public void getItem() {
		int cellIndex = 0;

		createTreeItems(tree.getSWTWidget(), cellIndex);

		String expectedText = "AA";

		DefaultTree dt = new DefaultTree();
		TreeItem dfi = new DefaultTreeItem(dt, "A").getItem("AA");

		assertTrue(String.format("Found Tree Item has to have text '%s', '%s' found instead",
				expectedText, dfi.getCell(cellIndex)),
				dfi.getCell(cellIndex).equals(expectedText));
	}
	
	@Test
	public void getItemByPath() {
		int cellIndex = 0;

		createTreeItems(tree.getSWTWidget(), cellIndex);

		String expectedText = "AAB";

		DefaultTree dt = new DefaultTree();
		TreeItem dfi = new DefaultTreeItem(dt, "A").getItem("AA", "AAB");
		assertTrue(String.format("Found Tree Item has to have text '%s', '%s' found instead",
				expectedText, dfi.getCell(cellIndex)),
				dfi.getCell(cellIndex).equals(expectedText));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindExistingItemByPathMatchers() {
		int cellIndex = 1;

		createTreeItems(tree.getSWTWidget(), cellIndex);

		String expectedText = "AAB";

		DefaultTree dt = new DefaultTree();
		DefaultTreeItem dfi = new DefaultTreeItem(dt, 
				new TreeItemTextMatcher("A", cellIndex), 
				new TreeItemTextMatcher("AA", cellIndex),
				new TreeItemTextMatcher("AAB", cellIndex));
		assertTrue(String.format("Found Tree Item has to have text '%s', '%s' found instead",
				expectedText, dfi.getCell(cellIndex)),
				dfi.getCell(cellIndex).equals(expectedText));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindUsingRegexMatchersAndIndex() {
		TreeItemRegexMatcher aMatcher = new TreeItemRegexMatcher("A");
		TreeItemRegexMatcher aPlusMatcher = new TreeItemRegexMatcher("A+");
		TreeItemRegexMatcher aPlusBMatcher = new TreeItemRegexMatcher("AA.");
		
		createTreeItems(tree.getSWTWidget());

		String expected;
		DefaultTreeItem dfi;

		expected = "AAB";
		dfi = new DefaultTreeItem(1, aMatcher, aPlusMatcher, aPlusBMatcher);
		assertEquals(String.format("Found item with text '%s', '%s' expected", dfi.getText(), expected),
				expected, dfi.getText());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindUsingIndex() {
		createTreeItems(tree.getSWTWidget());
		new DefaultTreeItem(0);
	}

	@Test
	public void testExpandCollapse(){
		createTreeItems(tree.getSWTWidget());
		DefaultTreeItem dfi = new DefaultTreeItem("A","AA");
		TreeEventsListener treeEventsListener = new TreeEventsListener(dfi.getParent().getSWTWidget());
		treeEventsListener.addListeners();
		if (dfi.isExpanded()){
			dfi.collapse();
			assertTrue("TreeItem is not collapsed",!dfi.isExpanded());
			assertNotNull("Collapse event was not fired",treeEventsListener.getCollapsedTreeItem());
			assertTrue("Incorrect tree item was collapsed: " + treeEventsListener.getCollapsedTreeItem().getText(),
					treeEventsListener.getCollapsedTreeItem().getText().equals(dfi.getText()));
			treeEventsListener.resetListeningWatchers();
			dfi.expand();
			assertTrue("TreeItem is not expanded",dfi.isExpanded());
			assertNotNull("Expand event was not fired",treeEventsListener.getExpandedTreeItem());
			assertTrue("Incorrect tree item was expanded: " + treeEventsListener.getExpandedTreeItem().getText(),
					treeEventsListener.getExpandedTreeItem().getText().equals(dfi.getText()));
		}
		else{
			dfi.expand();
			assertTrue("TreeItem is not expanded",dfi.isExpanded());
			assertNotNull("Expand event was not fired",treeEventsListener.getExpandedTreeItem());
			assertTrue("Incorrect tree item was expanded: " + treeEventsListener.getExpandedTreeItem().getText(),
					treeEventsListener.getExpandedTreeItem().getText().equals(dfi.getText()));
			treeEventsListener.resetListeningWatchers();
			dfi.collapse();
			assertTrue("TreeItem is not collapsed",!dfi.isExpanded());
			assertNotNull("Collapse event was not fired",treeEventsListener.getCollapsedTreeItem());
			assertTrue("Incorrect tree item was collapsed: " + treeEventsListener.getCollapsedTreeItem().getText(),
					treeEventsListener.getCollapsedTreeItem().getText().equals(dfi.getText()));
		}
		treeEventsListener.removeListeners();
	}
	@Test
	public void testCheckUncheck(){
		createTreeItems(tree.getSWTWidget());
		DefaultTreeItem dfi = new DefaultTreeItem("A","AA");
		TreeEventsListener treeEventsListener = new TreeEventsListener(dfi.getParent().getSWTWidget());
		treeEventsListener.addListeners();
		dfi.setChecked(true);
		assertTrue("TreeItem is not checked",dfi.isChecked());
		assertTrue("Selection event was not fired",treeEventsListener.wasSelectionEvent());
		assertTrue("Incorrect tree item was selected: " + treeEventsListener.getSelectedTreeItem().getText(),
				treeEventsListener.getSelectedTreeItem().getText().equals(dfi.getText()));
		assertTrue("Selection event has not been check event",
				treeEventsListener.wasCheckEvent());
		treeEventsListener.resetListeningWatchers();
		dfi.setChecked(false);
		assertTrue("TreeItem is checked",!dfi.isChecked());
		assertTrue("Selection event was not fired",treeEventsListener.wasSelectionEvent());
		assertTrue("Incorrect tree item was selected: " + treeEventsListener.getSelectedTreeItem().getText(),
				treeEventsListener.getSelectedTreeItem().getText().equals(dfi.getText()));
		assertTrue("Selection event has not been check event",
				treeEventsListener.wasCheckEvent());
		treeEventsListener.removeListeners();
	}
	@Test
	public void testSelect(){
		createTreeItems(tree.getSWTWidget());
		DefaultTreeItem dfi = new DefaultTreeItem("A","AA");
		TreeEventsListener treeEventsListener = new TreeEventsListener(dfi.getParent().getSWTWidget());
		treeEventsListener.addListeners();
		dfi.select();
		assertTrue("TreeItem is not selected",dfi.isSelected());
		assertTrue("Selection event was not fired",treeEventsListener.wasSelectionEvent());
		assertTrue("Incorrect tree item was selected: " + treeEventsListener.getSelectedTreeItem().getText(),
				treeEventsListener.getSelectedTreeItem().getText().equals(dfi.getText()));
		assertFalse("Selection event has been check event",
				treeEventsListener.wasCheckEvent());
		treeEventsListener.removeListeners();
	}
	@Test
	public void testSelectUnselectItems(){
		createTreeItems(tree.getSWTWidget());
		DefaultTreeItem dfi0 = new DefaultTreeItem("A","AA","AAA");
		DefaultTreeItem dfi1 = new DefaultTreeItem("B","BB");
		DefaultTreeItem dfi2 = new DefaultTreeItem("A","AA","AAB");
		DefaultTreeItem dfi3 = new DefaultTreeItem("C");
		DefaultTreeItem dfiNotSelected = new DefaultTreeItem("A","AA");
		TreeEventsListener treeEventsListener = new TreeEventsListener(
				dfi0.getParent().getSWTWidget());
		treeEventsListener.addListeners();
		dfi0.getParent().selectItems(dfi0,dfi1,dfi2,dfi3);
		assertTrue("TreeItem " + printFormattedStringArray(dfi0.getPath())
				+ " is not selected",dfi0.isSelected());
		assertTrue("TreeItem " + printFormattedStringArray(dfi1.getPath())
				+ " is not selected",dfi1.isSelected());
		assertTrue("TreeItem " + printFormattedStringArray(dfi2.getPath())
				+ " is not selected",dfi2.isSelected());
		assertTrue("TreeItem " + printFormattedStringArray(dfi3.getPath())
				+ " is not selected",dfi3.isSelected());
		assertFalse("TreeItem " + printFormattedStringArray(dfi3.getPath())
				+ " is selected",dfiNotSelected.isSelected());
		assertTrue("Selection event was not fired",treeEventsListener.wasSelectionEvent());
		assertFalse("Selection event has been check event",
				treeEventsListener.wasCheckEvent());
		treeEventsListener.resetListeningWatchers();
		dfi0.getParent().unselectAllItems();
		assertFalse("Selection event has been check event",
				treeEventsListener.wasCheckEvent());
		assertFalse("TreeItem " + printFormattedStringArray(dfi0.getPath())
				+ " is selected",dfi0.isSelected());
		assertFalse("TreeItem " + printFormattedStringArray(dfi1.getPath())
				+ " is selected",dfi1.isSelected());
		assertFalse("TreeItem " + printFormattedStringArray(dfi2.getPath())
				+ " is selected",dfi2.isSelected());
		assertFalse("TreeItem " + printFormattedStringArray(dfi3.getPath())
				+ " is selected",dfi3.isSelected());
		assertFalse("TreeItem " + printFormattedStringArray(dfi3.getPath())
				+ " is selected",dfiNotSelected.isSelected());

		treeEventsListener.removeListeners();
	}
	@Test
	public void testDoubleClick(){
		createTreeItems(tree.getSWTWidget());
		DefaultTreeItem dfi = new DefaultTreeItem("A","AA");
		TreeEventsListener treeEventsListener = new TreeEventsListener(
				dfi.getParent().getSWTWidget());
		treeEventsListener.addListeners();
		dfi.doubleClick();
		assertTrue("Selection event was not fired",treeEventsListener.wasSelectionEvent());
		assertTrue("MouseDoubleClick event was not fired",treeEventsListener.wasMouseDoubleClickEvent());
		assertNotNull("DefaultSelection event was not fired",treeEventsListener.getDefaultSelectedTreeItem());
		assertTrue("TreeItem is not selected",dfi.isSelected());
		assertTrue("Incorrect tree item was selected: " + treeEventsListener.getDefaultSelectedTreeItem().getText(),
				treeEventsListener.getDefaultSelectedTreeItem().getText().equals(dfi.getText()));
		treeEventsListener.removeListeners();
	}
	@Test
	public void testGetPath(){
		createTreeItems(tree.getSWTWidget());
		String[] expectedPath = new String[] {"A","AA","AAA"};
		DefaultTreeItem dfi = new DefaultTreeItem(expectedPath);
		String[] path = dfi.getPath();
		if (!Arrays.equals(path, expectedPath)){
			fail("Expected path for tree item is:\n" +
					printFormattedStringArray(expectedPath) +
					"\nReturned path is:\n" +
					printFormattedStringArray(path)); 
		}
	}
	@Test
	public void testGetCell() {
		createTreeItems(tree.getSWTWidget());
		final String itemLabel = "AAA";
		DefaultTreeItem dfi = new DefaultTreeItem("A", "AA", itemLabel);
		String cellLabel = dfi.getCell(0);
		assertTrue("Cell [0] of tree item has to be " + itemLabel 
				+ " but is " + cellLabel,
				cellLabel.equals(itemLabel));
		cellLabel = dfi.getCell(1);
		assertTrue("Cell [1] of tree item has to be empty but is" + cellLabel,
				cellLabel.length() == 0);
	}

	private String printFormattedStringArray (String[] array){
		StringBuffer sb = new StringBuffer();
		for (String item : array){
			sb.append(item);
			sb.append(",");
		}
		if (sb.length() > 0){
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	@Test
	public void testDynamicExpand() {
		createDynamicTreeItems(tree.getSWTWidget(), 2000, 1000, 5);
		DefaultTreeItem dfi = new DefaultTreeItem("A");
		if (dfi.isExpanded()) {
			dfi.collapse();
		}
		int expectedNumItems= 3;
		dfi.expand(expectedNumItems,TimePeriod.getCustom(10));
		int numItems = dfi.getItems().size();
		assertTrue("Tree Item " + dfi.getText() + " has to have " 
				+ expectedNumItems + " but has " + numItems,
				numItems >= expectedNumItems);
		// stops thread generating tree items
		if (generateDynamicTreeItems != null){
			generateDynamicTreeItems.interrupt();
		}
		// check if wait fails when short time out is used
		createDynamicTreeItems(tree.getSWTWidget(), 2000, 1000, 5);
		dfi = new DefaultTreeItem("A");
		if (dfi.isExpanded()) {
			dfi.collapse();
		}
		boolean wasException = false;
		try{
			dfi.expand(expectedNumItems,TimePeriod.getCustom(3));
		} catch (WaitTimeoutExpiredException wtee){
			wasException = true;
		}
		assertTrue("WaitTimeoutExpiredException was not thrown" ,
				wasException);
	}

	private void createDynamicTreeItems(final Tree tree, final int initSleep,
			final int delay, final int count) {
		removeTreeItems(tree);
		createTreeItem(tree, "A");
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				tree.addTreeListener(new TreeListener() {
					@Override
					public void treeExpanded(TreeEvent arg0) {
						addDynamicTreeItems(
								(org.eclipse.swt.widgets.TreeItem) arg0.item,
								initSleep, delay, count);
					}

					@Override
					public void treeCollapsed(TreeEvent arg0) {
					}
				});
			}
		});
	}
	
	private void addDynamicTreeItems(
			final org.eclipse.swt.widgets.TreeItem tiExpanded,
			final int initSleep, final int delay, final int count) {
		if (!isThreadAlreadyRunning()){
			setThreadAlreadyRunning(true);
			generateDynamicTreeItems = new Thread (new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(initSleep);
						for (int index = 0; index < count; index++) {
							Thread.sleep(delay);
							final String itemLabel = "A" + index;
							if (Display.getDisplay() != null){
								Display.syncExec(new Runnable() {
									@Override
									public void run() {
										org.eclipse.swt.widgets.TreeItem item = new org.eclipse.swt.widgets.TreeItem(
												tiExpanded, SWT.None);
										item.setText(itemLabel);
										if (itemLabel.equals("A0")){
											tiExpanded.setExpanded(true);
										}
									}
								});
							}
						}	
						setThreadAlreadyRunning(false);
					} catch (InterruptedException e) {
					}
				}
			});
			generateDynamicTreeItems.start();
		}
	}

	private boolean isThreadAlreadyRunning() {
		return threadAlreadyRunning;
	}

	private  synchronized void setThreadAlreadyRunning(boolean threadAlreadyRunning) {
		this.threadAlreadyRunning = threadAlreadyRunning;
	}
	
	@After
	public void cleanUp(){
		if (generateDynamicTreeItems != null){
			generateDynamicTreeItems.interrupt();
		}
	}
}
