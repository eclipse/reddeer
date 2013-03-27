package org.jboss.reddeer.swt.test.ui.views;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.ui.part.ViewPart;

public class CustomView extends ViewPart {

	private Tree tree;
	private Label label;
	private Text text;
	
	@Override
	public void createPartControl(Composite composite) {
		
		label = new Label(composite, SWT.NONE);
		label.setText("Name:");
		text = new Text(composite, SWT.NONE);
		text.setText("Original text");
		
		tree = new Tree(composite, SWT.BORDER);
		tree.setVisible(true);
		createTreeItems(tree);
	}

	@Override
	public void setFocus() {
		tree.setFocus();
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

}
