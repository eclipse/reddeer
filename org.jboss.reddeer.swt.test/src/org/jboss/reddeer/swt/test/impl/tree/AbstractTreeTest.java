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

import org.eclipse.swt.widgets.Tree;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.test.ui.views.TreeEventsListener;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.junit.Test;

public class AbstractTreeTest extends RedDeerTest {

	protected org.jboss.reddeer.swt.api.Tree tree;

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

  private void createTreeItems(Tree tree){
    removeTreeItems(tree);
    org.eclipse.swt.widgets.TreeItem itemA = createTreeItem(tree, "A");
    org.eclipse.swt.widgets.TreeItem itemAA = createTreeItem(itemA, "AA");
    createTreeItem(itemAA, "AAA");
    createTreeItem(itemAA, "AAB");
    
    org.eclipse.swt.widgets.TreeItem itemB = createTreeItem(tree, "B");
    createTreeItem(itemB, "BB");
    
    createTreeItem(tree, "C");
  }
  
  private org.eclipse.swt.widgets.TreeItem createTreeItem(final Tree tree, final String text){
    return Display.syncExec(new ResultRunnable<org.eclipse.swt.widgets.TreeItem>() {
      @Override
      public org.eclipse.swt.widgets.TreeItem run() {
        org.eclipse.swt.widgets.TreeItem item = new org.eclipse.swt.widgets.TreeItem(tree, 0);
        item.setText(new String[]{text});
        return item;
      }
    });
  }
  
  private org.eclipse.swt.widgets.TreeItem createTreeItem(
      final org.eclipse.swt.widgets.TreeItem treeItem, final String text) {
    return Display.syncExec(new ResultRunnable<org.eclipse.swt.widgets.TreeItem>() {
          @Override
          public org.eclipse.swt.widgets.TreeItem run() {
            org.eclipse.swt.widgets.TreeItem item = new org.eclipse.swt.widgets.TreeItem(
                treeItem, 0);
            item.setText(text);
            return item;
          }
        });

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
}
