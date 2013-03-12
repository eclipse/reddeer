package org.jboss.reddeer.swt.impl.tree;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Default tree item implementation
 * 
 * @author jjankovi
 *
 */
public class DefaultTreeItem implements TreeItem {

protected final Logger logger = Logger.getLogger(this.getClass());
	
	protected SWTBotTreeItem item;
	private int treeIndex;
	
	protected String[] path;
	
	/**
	 * Default parameter-less constructor
	 */
	public DefaultTreeItem() {
		this(0);
	}
	
	/**
	 * Tree item with specified path will be constructed 
	 * 
	 * @param treeItemPath
	 */
	public DefaultTreeItem(String... treeItemPath) {
		this(0, treeItemPath);
	}
	
	/**
	 * Tree item with specified tree index and path will be constructed
	 * 
	 * @param treeIndex
	 * @param treeItemPath
	 */
	public DefaultTreeItem(int treeIndex, String... treeItemPath) {
		this(treeIndex, 0, treeItemPath);
	}
	
	/**
	 * Tree item with specified tree item index will be constructed
	 * 
	 * @param treeItemIndex
	 */
	public DefaultTreeItem(int treeItemIndex) {
		this(0, treeItemIndex);
	}
	
	/**
	 * Tree item with specified tree and tree item index will be constructed
	 * 
	 * @param treeIndex
	 * @param treeItemIndex
	 */
	public DefaultTreeItem(int treeIndex, int treeItemIndex) {
		this(treeIndex, treeItemIndex, (String[]) null);
	}
	
	/**
	 * Tree item with specified tree index, tree item index and tree item path 
	 * will be constructed
	 * 
	 * @param treeIndex
	 * @param treeItemIndex
	 * @param treeItemPath
	 */
	@SuppressWarnings("unchecked")
	public DefaultTreeItem(int treeIndex, int treeItemIndex, String... treeItemPath) {
		SWTBotTree tree = new SWTBotTree((org.eclipse.swt.widgets.Tree) 
				WidgetLookup.getInstance().
				activeWidget(allOf(widgetOfType(
						org.eclipse.swt.widgets.Tree.class)), treeIndex));
		this.treeIndex=treeIndex;
		int size = tree.getAllItems().length;
		if (size - treeItemIndex < 1) {
			throw new SWTLayerException("No matching tree item found");
		}
		if (treeItemPath == null) {
			new WaitUntil(new TreeHasChildren(tree));
			item = tree.getAllItems()[treeItemIndex];
			path = new String[] {item.getText()};
		} else {
			List<String> tiPath = new ArrayList<String>(Arrays.asList(treeItemPath));
			new WaitUntil(new TreeHasChildren(tree));
			item = tree.getTreeItem(tiPath.get(0));
			tiPath.remove(0);
			for (String treeItemNode : tiPath) {
				new WaitUntil(new TreeItemFoundAfterExpanding(item, treeItemNode));
				item = item.getNode(treeItemNode);
			}
			path = treeItemPath;
		}
		
	}
	
	@Override
	public void select() {
		new WaitUntil(new TreeItemIsSelected(item));
	}
	
	@Override
	public String getText() {
		String text = item.getText();
		return text;
	}
	
	@Override
	public String getToolTipText() {
		String toolTipText = item.getToolTipText();
		return toolTipText;
	}
	
	@Override
	public String getCell(int index) {
		return item.cell(index);
	}
	
	@Override
	public String[] getPath() {
		return path;
	}
	
	@Override
	public void expand(){
		logger.debug("Expanding Tree Item");
		item.expand();
	}
	
	@Override
	public void collapse() {
		logger.debug("Collapsing Tree Item");
		item.collapse();
	}
	
	/**
	 * Returns all direct descendants from current tree item
	 * @param shellItem			help to decide if child tree 
	 * 							item is from shell/view environment 
	 * @return					list of all direct descendants
	 */
	public List<TreeItem> getItems() {
		expand();
		List<TreeItem> items = new LinkedList<TreeItem>();
		for (SWTBotTreeItem childrenTreeItem : item.getItems()) {
			String[] treeItemPath = new String[] {childrenTreeItem.getText()};
			items.add(new DefaultTreeItem(treeIndex,joinTwoArrays(getPath(), treeItemPath)));
			
		}
		return items;
	}
	
	/**
	 * Return direct descendant specified with parameters
	 * @param text				text of tree item which should be returned	
	 * @param shellTreeItem		specifies if returned tree item 
	 * 							is from shell/view environment
	 * @return					direct descendant specified with parameters
	 */
	public TreeItem getItem(String text){
		expand();
		SWTBotTreeItem[] items = item.getItems();
		int index = 0;
		while (index < items.length && !items[index].getText().equals(text)){
			index++;
		}
		if (index < items.length){
			String[] treeItemPath = new String[] {text};
			return new DefaultTreeItem(treeIndex,joinTwoArrays(getPath(), treeItemPath));
		}
		else{
			throw new WidgetNotFoundException("There is no Tree Item with text " + text);
		}
	}
	
	@Override
	public void doubleClick(){
		logger.debug("Double Click Tree Item " + item.getText());
		item.doubleClick();
	}
	
	@Override
	public boolean isSelected(){
		return item.isSelected();
	}
	
	@Override
	public boolean isDisposed() {
		return item.widget.isDisposed();
	}
	
	@Override
	public void setChecked(boolean check) {
		if (check){
			item.check();	
		} else {
			item.uncheck();
		}
	}
	
	@Override
	public boolean isChecked() {
		return item.isChecked();
	}
	
	/**
	 * Return swt widget of Tree Item 
	 */
	public org.eclipse.swt.widgets.TreeItem getSWTWidget() {
		return item.widget;
	}
	
	private String[] joinTwoArrays(String[] array1, String[] array2) {
		String[] finalArray= new String[array1.length + array2.length];
		System.arraycopy(array1, 0, finalArray, 0, array1.length);
		System.arraycopy(array2, 0, finalArray, array1.length, array2.length);
		return finalArray;
	}

}

/**
 * Condition is fulfilled when tree item has children 
 * 
 * @author jjankovi
 *
 */
class TreeItemFoundAfterExpanding implements WaitCondition {

	private String treeItemNode;
	private SWTBotTreeItem item;
	
	public TreeItemFoundAfterExpanding(SWTBotTreeItem item, String treeItemNode) {
		super();
		this.treeItemNode = treeItemNode;
		this.item = item;
	}
	
	@Override
	public boolean test() {
		item.collapse();
		item.expand();
		return nodeIsFound(treeItemNode);		
	}

	@Override
	public String description() {
		return "Tree item '" + treeItemNode + "' not found.";
	}
	
	private boolean nodeIsFound(String treeItemNode) {
		
		try {
			item.getNode(treeItemNode);
			System.out.println(treeItemNode + " was found");
			return true;
		} catch (WidgetNotFoundException wnfe) {
			item.collapse();
			return false;
		}
		
	}
	
}

/**
 * Condition is fulfilled when tree has children 
 * 
 * @author jjankovi
 *
 */
class TreeHasChildren implements WaitCondition {

	private SWTBotTree tree;
	
	public TreeHasChildren(SWTBotTree tree) {
		super();
		this.tree = tree;
	}
	
	@Override
	public boolean test() {
		return tree.getAllItems().length > 0;		
	}

	@Override
	public String description() {
		return "Tree has no childre";
	}
	
}

/**
 * Condition is fulfilled when tree item is selected
 * 
 * @author jjankovi
 *
 */
class TreeItemIsSelected implements WaitCondition {

	private SWTBotTreeItem item; 
	
	public TreeItemIsSelected(SWTBotTreeItem item) {
		super();
		this.item = item;
	}

	@Override
	public boolean test() {
		item.select();
		return item.isSelected();
	}

	@Override
	public String description() {
		return "Tree item '" + item.getText() + "' cannot be selected.";
	}
	
}
