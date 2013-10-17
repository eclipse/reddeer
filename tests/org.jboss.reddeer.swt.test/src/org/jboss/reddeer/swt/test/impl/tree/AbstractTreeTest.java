package org.jboss.reddeer.swt.test.impl.tree;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.widgets.Tree;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.test.ui.views.TreeEventsListener;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class AbstractTreeTest extends RedDeerTest {

	protected org.jboss.reddeer.swt.api.Tree tree;

	/* Any number > 1 would do */
	protected static final int TREE_COLUMN_COUNT = 3;

	private boolean threadAlreadyRunning = false;
	private Thread generateDynamicTreeItems = null;

	@Override
	public void setUp() {
		super.setUp();
  }
	
	@SuppressWarnings("unchecked")
	public void checkItems() {
		
		List<TreeItem> items = tree.getItems();
		
		assertThat(items.size(), is(3));
		assertThat(items, hasItems(item("A"), item("B"), item("C")));
	}

	@SuppressWarnings("unchecked")
	public void checkAllItems(){
		
		List<TreeItem> items = tree.getAllItems();
		assertThat(items.size(), is(7));
		assertThat(items, hasItems(
				item("A"), item("AA"), item("AAA"), item("AAB"), 
				item("B"), item("BB"), 
				item("C")));
	}
	
	private TreeItemTextMatcher item(String text){
		return new TreeItemTextMatcher(text);
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
	public void testFindExistingItemByTreeAndPathAndCell() {
		int cellIndex = 1;

		createTreeItems(tree.getSWTWidget(), cellIndex);

		String expectedText = "AAB";

		DefaultTree dt = new DefaultTree();
		DefaultTreeItem dfi = new DefaultTreeItem(dt, cellIndex, "A", "AA", "AAB");
		assertTrue(String.format("Found Tree Item has to have text '%s', '%s' found instead",
				expectedText, dfi.getCell(cellIndex)),
				dfi.getCell(cellIndex).equals(expectedText));
	}

	@Test
	public void testColumnCount() {
		createTreeItems(tree.getSWTWidget());

		DefaultTree dt = new DefaultTree();

		assertTrue(String.format("DefaultTree should have %d columns, %d reported",
				TREE_COLUMN_COUNT, dt.getColumnCount()), dt.getColumnCount() == TREE_COLUMN_COUNT);
	}

	@SuppressWarnings("unused")
	@Test(expected = SWTLayerException.class)
  public void testFindNonExistingItemByPath(){
	  createTreeItems(tree.getSWTWidget());
	  DefaultTreeItem dfi = new DefaultTreeItem("A","AA","NONEXISTINGTEXT");
  }

  @Test
  public void testFindExistingItemByIndex() {
    createTreeItems(tree.getSWTWidget());
    DefaultTreeItem dfi = new DefaultTreeItem(2);
    final String expectedTreeItemText = "C";
    assertTrue("Founded Tree Item has to have text " + expectedTreeItemText
        + " but has " + dfi.getText(), dfi.getText()
        .equals(expectedTreeItemText));
  }
  @SuppressWarnings("unused")
  @Test(expected = SWTLayerException.class)
  public void testFindNonExistingItemByIndex() {
    createTreeItems(tree.getSWTWidget());
    DefaultTreeItem dfi = new DefaultTreeItem(3);
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
    TreeEventsListener treeEventsListener = new TreeEventsListener(dfi0.getParent().getSWTWidget());
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
    TreeEventsListener treeEventsListener = new TreeEventsListener(dfi.getParent().getSWTWidget());
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
  public void testGetItems_noItems() {
    removeTreeItems(tree.getSWTWidget());
    List<TreeItem> items = tree.getItems();
    assertTrue(items.isEmpty());
  }

  @Test
  public void testGetItems() {
    createTreeItems(tree.getSWTWidget());
    checkItems();
  }

  @Test
  public void testGetAllItems_noItems(){
    removeTreeItems(tree.getSWTWidget());
    List<TreeItem> items = tree.getAllItems();
    assertTrue(items.isEmpty());
  }

  @Test
  public void testGetAllItems(){
    createTreeItems(tree.getSWTWidget());
    checkAllItems();
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
  
  private void removeTreeItems (final Tree tree){
    Display.syncExec(new Runnable() {
      @Override
      public void run() {
        tree.removeAll();
      }
    });
  }

  private void createTreeItems(Tree tree, int cellIndex) {
	  assertTrue(String.format("cellIndex set to %d, cannot fit into testing tree with %d columns",
				cellIndex, TREE_COLUMN_COUNT), cellIndex < TREE_COLUMN_COUNT);

	  removeTreeItems(tree);

	    org.eclipse.swt.widgets.TreeItem itemA = createTreeItem(tree, "A", cellIndex);
	    org.eclipse.swt.widgets.TreeItem itemAA = createTreeItem(itemA, "AA", cellIndex);
	    createTreeItem(itemAA, "AAA", cellIndex);
	    createTreeItem(itemAA, "AAB", cellIndex);

	    org.eclipse.swt.widgets.TreeItem itemB = createTreeItem(tree, "B", cellIndex);
	    createTreeItem(itemB, "BB", cellIndex);

	    createTreeItem(tree, "C", cellIndex);
  }

  private void createTreeItems(Tree tree) {
	  createTreeItems(tree, 0);
  }

  private org.eclipse.swt.widgets.TreeItem createTreeItem(
		  final Tree tree, final String text, final int cellIndex) {
	  return Display.syncExec(new ResultRunnable<org.eclipse.swt.widgets.TreeItem>() {
		  @Override
		  public org.eclipse.swt.widgets.TreeItem run() {
			  org.eclipse.swt.widgets.TreeItem item = new org.eclipse.swt.widgets.TreeItem(tree, 0);
			  item.setText(cellIndex, text);
			  return item;
		  }
	  });
  }

  private org.eclipse.swt.widgets.TreeItem createTreeItem(final Tree tree, final String text) {
	  return createTreeItem(tree, text, 0);
  }

  private org.eclipse.swt.widgets.TreeItem createTreeItem(
		  final org.eclipse.swt.widgets.TreeItem treeItem, final String text, final int cellIndex) {
	    return Display.syncExec(new ResultRunnable<org.eclipse.swt.widgets.TreeItem>() {
	          @Override
	          public org.eclipse.swt.widgets.TreeItem run() {
	        	  org.eclipse.swt.widgets.TreeItem item = new org.eclipse.swt.widgets.TreeItem(treeItem, 0);
	        	  item.setText(cellIndex, text);
	        	  return item;
	          }
	        });

	  }

  private org.eclipse.swt.widgets.TreeItem createTreeItem(
	      final org.eclipse.swt.widgets.TreeItem treeItem, final String text) {
	  return createTreeItem(treeItem, text, 0);
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
    
	private class TreeItemTextMatcher extends TypeSafeMatcher<TreeItem> {

		private String expectedText;
		
		public TreeItemTextMatcher(String exptectedText) {
			this.expectedText = exptectedText;
		}
		
		@Override
		public void describeTo(Description description) {
			description.appendText("tree item with text " + expectedText);
		}

		@Override
		public boolean matchesSafely(TreeItem item) {
			return item.getText().equals(expectedText);
		}
		
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

	public boolean isThreadAlreadyRunning() {
		return threadAlreadyRunning;
	}

	public synchronized void setThreadAlreadyRunning(boolean threadAlreadyRunning) {
		this.threadAlreadyRunning = threadAlreadyRunning;
	}
	@After
	public void cleanUp(){
		if (generateDynamicTreeItems != null){
			generateDynamicTreeItems.interrupt();
		}
	}
}
