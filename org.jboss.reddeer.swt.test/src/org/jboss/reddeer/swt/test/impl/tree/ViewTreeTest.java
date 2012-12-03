package org.jboss.reddeer.swt.test.impl.tree;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.ViewTree;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;
import org.junit.Before;
import org.junit.Test;

public class ViewTreeTest {
	
	private CustomViewImpl customView = new CustomViewImpl();
	
	@Before
	public void openView() {
		customView.open();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getItems() {
		List<TreeItem> items = new ViewTree().getItems();
		assertThat(items.size(), is(3));
		assertThat(items, hasItems(item("A"), item("B"), item("C")));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getAllItems() {
		List<TreeItem> items = new ViewTree().getAllItems();
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
