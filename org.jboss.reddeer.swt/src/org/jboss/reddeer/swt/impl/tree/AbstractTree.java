package org.jboss.reddeer.swt.impl.tree;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;

public abstract class AbstractTree implements Tree {

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	protected SWTBotTree tree;

	public AbstractTree(Control control) {
		this(control, 0);
	}
	
	@SuppressWarnings("unchecked")
	public AbstractTree(Control control, int index) {
		this(allOf(widgetOfType(org.eclipse.swt.widgets.Tree.class)), control, index);
	}
	
	public AbstractTree(Matcher<org.eclipse.swt.widgets.Tree> matcher, Control control) {
		this(matcher, control, 0);
	}
		
	public AbstractTree(Matcher<org.eclipse.swt.widgets.Tree> matcher, Control control, int index) {
		tree = new SWTBotTree((org.eclipse.swt.widgets.Tree) 
				WidgetLookup.getInstance().
				activeWidget(matcher, control, index));
	}
	
	protected List<TreeItem> getItems(boolean shellItem){
		List<TreeItem> list = new LinkedList<TreeItem>();
		for (SWTBotTreeItem treeItem : tree.getAllItems()) {
			TreeItem item = null;
			if (shellItem) {
				item = new ShellTreeItem(treeItem.getText());
			} else {
				item = new ViewTreeItem(treeItem.getText());
			}
			list.add(item);
		}
		return list;	
	}
	
	public List<TreeItem> getAllItems(){
		List<TreeItem> list = new LinkedList<TreeItem>();
		list.addAll(getAllItemsRecursive(getItems()));
		return list;
	}

	private List<TreeItem> getAllItemsRecursive(List<TreeItem> parentItems){
		List<TreeItem> list = new LinkedList<TreeItem>();
		
		for (TreeItem item : parentItems) {
			list.add(item);
			list.addAll(getAllItemsRecursive(item.getItems()));
		}
		return list;
	}
}
