package org.jboss.reddeer.swt.test.impl.tree;

import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * Provides setup method for tree testing tests
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractTreeTest extends SWTLayerTestCase {

	/* Any number > 1 would do */
	protected static final int TREE_COLUMN_COUNT = 3;
	
	private Tree swtTree;
	
	@Override
	protected void createControls(Shell shell) {
		swtTree = new Tree(shell, SWT.BORDER|SWT.CHECK|SWT.MULTI);

		TreeColumn column = new TreeColumn(swtTree, SWT.LEFT);
		column.setText("Column1");
		column.setWidth(200);
		
		TreeColumn column2 = new TreeColumn(swtTree, SWT.CENTER);
		column2.setText("Column2");
		column2.setWidth(200);
		
		TreeColumn column3 = new TreeColumn(swtTree, SWT.RIGHT);
		column3.setText("Column3");
		column3.setWidth(200);
	}
	
	protected void createTreeItems(Tree tree) {
		createTreeItems(tree, 0);
	}
	
	protected void createTreeItems(Tree tree, int cellIndex) {
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

	protected org.eclipse.swt.widgets.TreeItem createTreeItem(Tree tree, String text) {
		return createTreeItem(tree, text, 0);
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
	
	protected void removeTreeItems (final Tree tree){
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				tree.removeAll();
			}
		});
	}
}
