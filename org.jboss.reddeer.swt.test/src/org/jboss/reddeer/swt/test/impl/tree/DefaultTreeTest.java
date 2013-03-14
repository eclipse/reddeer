package org.jboss.reddeer.swt.test.impl.tree;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;
import org.junit.Before;
import org.junit.Test;
/**
 * Tests for DefaulTree
 * @author vlado pakan
 *
 */
public class DefaultTreeTest {
	
	private CustomViewImpl customView = new CustomViewImpl();
	
	@Before
	public void openView() {
		customView.open();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getItems() {
		
		List<TreeItem> items = new DefaultTree().getItems();
		assertThat(items.size(), is(3));
		assertThat(items, hasItems(item("A"), item("B"), item("C")));
	}
	@Test
  public void selectItems() {
    
	  DefaultTreeItem tiA = new DefaultTreeItem("A");
	  DefaultTreeItem tiB = new DefaultTreeItem("B");
	  DefaultTreeItem tiC = new DefaultTreeItem("C");
	  DefaultTreeItem tiAAA = new DefaultTreeItem("A","AA","AAA");
	  DefaultTreeItem tiBB = new DefaultTreeItem("B","BB");
	  
    new DefaultTree().selectItems(new TreeItem[]{tiAAA, tiBB, tiC});
    
    assertTrue("TreeItem " + tiAAA.getText() + " is not selected" , tiAAA.isSelected());
    assertTrue("TreeItem " + tiBB.getText() + " is not selected" , tiBB.isSelected());
    assertTrue("TreeItem " + tiC.getText() + " is not selected" , tiC.isSelected());
    assertTrue("TreeItem " + tiA.getText() + " is selected" , !tiA.isSelected());
    assertTrue("TreeItem " + tiB.getText() + " is selected" , !tiB.isSelected());
    
  }
	@SuppressWarnings("unchecked")
	@Test
	public void getAllItems() {
		List<TreeItem> items = new DefaultTree().getAllItems();
		assertThat(items.size(), is(7));
		assertThat(items, hasItems(
				item("A"), item("AA"), item("AAA"), item("AAB"), 
				item("B"), item("BB"), 
				item("C")));
	}
	
	private TreeItemTextMatcher item(String text){
		return new TreeItemTextMatcher(text);
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
	
	class CustomViewImpl extends WorkbenchView {

    private static final String TEST_CATEGORY = "Red Deer Test SWT";
    
    private static final String VIEW_TEXT = "Custom View";
		
		public CustomViewImpl() {
			super(TEST_CATEGORY, VIEW_TEXT);
		}
	}

}
