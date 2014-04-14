package org.jboss.reddeer.swt.test.impl.tree;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
import org.junit.Test;

public class ViewTreeTest extends AbstractTreeTest {
	private CustomViewImpl customView = new CustomViewImpl();
	@Override
	public void setUp() {
		super.setUp();

		/* reset view */
		try {
			customView.close();
		} catch (UnsupportedOperationException e) {
		}
		customView.open();

		tree = new DefaultTree();
		final Tree swtTree = tree.getSWTWidget();

		/* tree is already created with the first column */
		org.jboss.reddeer.swt.util.Display.syncExec(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < TREE_COLUMN_COUNT - 1; i++) {
					TreeColumn column = new TreeColumn(swtTree, SWT.LEFT);
					column.setWidth(200);
				}
			}
		});
	}
	
	@Test
	public void testGetItems() {
		checkItems();
	}
	
	@Test
	public void testGetAllItems() {
    checkAllItems();
  }
	
	class CustomViewImpl extends WorkbenchView {

		private static final String TEST_CATEGORY = "Red Deer Test SWT";
		
		private static final String VIEW_TEXT = "Custom View";
		
		public CustomViewImpl() {
			super(TEST_CATEGORY, VIEW_TEXT);
		}
	}

}
