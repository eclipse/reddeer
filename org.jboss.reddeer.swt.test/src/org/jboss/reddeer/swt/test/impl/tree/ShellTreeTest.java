package org.jboss.reddeer.swt.test.impl.tree;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.AbstractTree;
import org.jboss.reddeer.swt.lookup.impl.ShellLookup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShellTreeTest {

	private Tree swtTree;

	private AbstractTree tree;

	@Before
	public void setup(){
		UIThreadRunnable.syncExec(new VoidResult() {

			@Override
			public void run() {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				shell.setText("Testing shell");
				swtTree = new Tree(shell, SWT.BORDER);
				shell.open();
				shell.setFocus();
			}
		});

		tree = new ShellTreeImpl();
	}
	
	@After
	public void cleanup() {
		UIThreadRunnable.syncExec(new VoidResult() {

			@Override
			public void run() {
				for (Shell shell : org.jboss.reddeer.swt.
						util.Display.getDisplay().getShells()) {
					if (shell.getText().equals("Testing shell")) {
						shell.dispose();
						break;
					}
				}
			}
		});
	}
	
	@Test
	public void getItems_noItems() {
		List<TreeItem> items = tree.getItems();

		assertTrue(items.isEmpty());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getItems() {
		createTreeItems(swtTree);
		
		List<TreeItem> items = tree.getItems();
		assertThat(items.size(), is(3));
		assertThat(items, hasItems(item("A"), item("B"), item("C")));
	}

	@Test
	public void getAllItems_noItems(){
		List<TreeItem> items = tree.getAllItems();

		assertTrue(items.isEmpty());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAllItems(){
		createTreeItems(swtTree);
		
		List<TreeItem> items = tree.getAllItems();
		assertThat(items.size(), is(7));
		assertThat(items, hasItems(
				item("A"), item("AA"), item("AAA"), item("AAB"), 
				item("B"), item("BB"), 
				item("C")));
	}

	private void createTreeItems(Tree tree){
		org.eclipse.swt.widgets.TreeItem itemA = createTreeItem(tree, "A");
		org.eclipse.swt.widgets.TreeItem itemAA = createTreeItem(itemA, "AA");
		createTreeItem(itemAA, "AAA");
		createTreeItem(itemAA, "AAB");
		
		org.eclipse.swt.widgets.TreeItem itemB = createTreeItem(tree, "B");
		createTreeItem(itemB, "BB");
		
		createTreeItem(tree, "C");
	}
	
	private org.eclipse.swt.widgets.TreeItem createTreeItem(final Tree tree, final String text){
		return UIThreadRunnable.syncExec(new Result<org.eclipse.swt.widgets.TreeItem>() {

			@Override
			public org.eclipse.swt.widgets.TreeItem run() {
				org.eclipse.swt.widgets.TreeItem item = new org.eclipse.swt.widgets.TreeItem(tree, 0);
				item.setText(text);
				return item;
			}
		});
	}
	
	private org.eclipse.swt.widgets.TreeItem createTreeItem(final org.eclipse.swt.widgets.TreeItem treeItem, final String text){
		return UIThreadRunnable.syncExec(new Result<org.eclipse.swt.widgets.TreeItem>() {

			@Override
			public org.eclipse.swt.widgets.TreeItem run() {
				org.eclipse.swt.widgets.TreeItem item = new org.eclipse.swt.widgets.TreeItem(treeItem, 0);
				item.setText(text);
				return item;
			}
		});
	}
	
	class ShellTreeImpl extends AbstractTree {

		public ShellTreeImpl() {
			super(new ShellLookup().getActiveShell());
		}

		@Override
		public List<TreeItem> getItems() {
			return getItems(true);
		}
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
}
