package org.jboss.reddeer.swt.test.impl.tree;

import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;
import org.junit.Test;

public class ViewTreeTest extends AbstractTreeTest {
	
	private CustomViewImpl customView = new CustomViewImpl();
	@Override
	public void setUp() {
		super.setUp();
		customView.open();
		tree = new DefaultTree();
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
