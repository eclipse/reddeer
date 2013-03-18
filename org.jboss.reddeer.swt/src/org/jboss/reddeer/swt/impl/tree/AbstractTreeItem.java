package org.jboss.reddeer.swt.impl.tree;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Basic TreeItem class is abstract class for all Tree Item implementations
 * @author jjankovi
 *
 */
public abstract class AbstractTreeItem implements TreeItem {

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	protected SWTBotTreeItem item;
	private int treeIndex;
	
	protected String[] path;
	
	/**
	 * @deprecated As of release 0.4, replaced by 
	 * {@link org.jboss.reddeer.swt.impl.tree.DefaultTreeItem}
	 */
	@Deprecated
	public AbstractTreeItem(Control control) {
		this(control, 0);
	}
	
	/**
	 * @deprecated As of release 0.4, replaced by 
	 * {@link org.jboss.reddeer.swt.impl.tree.DefaultTreeItem}
	 */
	@Deprecated
	public AbstractTreeItem(Control control, String... treeItemPath) {
		this(control, 0, treeItemPath);
	}
	
	/**
	 * @deprecated As of release 0.4, replaced by 
	 * {@link org.jboss.reddeer.swt.impl.tree.DefaultTreeItem}
	 */
	@Deprecated
	public AbstractTreeItem(Control control, int treeIndex, String... treeItemPath) {
		this(control, treeIndex, 0, treeItemPath);
	}
	
	/**
	 * @deprecated As of release 0.4, replaced by 
	 * {@link org.jboss.reddeer.swt.impl.tree.DefaultTreeItem}
	 */
	@Deprecated
	public AbstractTreeItem(Control control, int treeItemIndex) {
		this(control, 0, treeItemIndex);
	}
	
	/**
	 * @deprecated As of release 0.4, replaced by 
	 * {@link org.jboss.reddeer.swt.impl.tree.DefaultTreeItem}
	 */
	@Deprecated
	public AbstractTreeItem(Control control, int treeIndex, int treeItemIndex) {
		this(control, treeIndex, treeItemIndex, (String[]) null);
	}
	
	/**
	 * @deprecated As of release 0.4, replaced by 
	 * {@link org.jboss.reddeer.swt.impl.tree.DefaultTreeItem}
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public AbstractTreeItem(Control control, int treeIndex, int treeItemIndex, String... treeItemPath) {
		SWTBotTree tree = new SWTBotTree((org.eclipse.swt.widgets.Tree) 
				WidgetLookup.getInstance().
				activeWidget(allOf(widgetOfType(
						org.eclipse.swt.widgets.Tree.class)), control, treeIndex));
		this.treeIndex=treeIndex;
		int size = tree.getAllItems().length;
		if (size - treeItemIndex < 1) {
			throw new SWTLayerException("No matching tree item found");
		}
		if (treeItemPath == null) {
			item = tree.getAllItems()[treeItemIndex];
			path = new String[] {item.getText()};
		} else {
			List<String> tiPath = new ArrayList<String>(Arrays.asList(treeItemPath));
			// wait method maybe will be needed here
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
		item.select();
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
	protected List<TreeItem> getItems(boolean shellItem) {
		expand();
		List<TreeItem> items = new LinkedList<TreeItem>();
		for (SWTBotTreeItem childrenTreeItem : item.getItems()) {
			String[] treeItemPath = new String[] {childrenTreeItem.getText()};
			if (shellItem) {
				items.add(new ShellTreeItem(treeIndex,joinTwoArrays(getPath(), treeItemPath)));
			} else {
				items.add(new ViewTreeItem(treeIndex,joinTwoArrays(getPath(), treeItemPath)));
			}
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
	protected TreeItem getItem (String text, boolean shellTreeItem){
		expand();
		SWTBotTreeItem[] items = item.getItems();
		int index = 0;
		while (index < items.length && !items[index].getText().equals(text)){
			index++;
		}
		if (index < items.length){
			String[] treeItemPath = new String[] {text};
			if (shellTreeItem) {
				return new ShellTreeItem(treeIndex,joinTwoArrays(getPath(), treeItemPath));
			} else {
				return new ViewTreeItem(treeIndex,joinTwoArrays(getPath(), treeItemPath));
			}
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