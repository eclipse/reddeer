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

public abstract class AbstractTreeItem implements TreeItem {

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	protected SWTBotTreeItem item;
	
	protected String[] path;
	
	public AbstractTreeItem(Control control) {
		this(control, 0);
	}
	
	public AbstractTreeItem(Control control, String... treeItemPath) {
		this(control, 0, treeItemPath);
	}
	
	public AbstractTreeItem(Control control, int treeIndex, String... treeItemPath) {
		this(control, treeIndex, 0, treeItemPath);
	}
	
	public AbstractTreeItem(Control control, int treeItemIndex) {
		this(control, 0, treeItemIndex);
	}
	
	public AbstractTreeItem(Control control, int treeIndex, int treeItemIndex) {
		this(control, treeIndex, treeItemIndex, (String[]) null);
	}
	
	@SuppressWarnings("unchecked")
	public AbstractTreeItem(Control control, int treeIndex, int treeItemIndex, String... treeItemPath) {
		SWTBotTree tree = new SWTBotTree((org.eclipse.swt.widgets.Tree) 
				WidgetLookup.getInstance().
				activeWidget(allOf(widgetOfType(org.eclipse.swt.widgets.Tree.class)), control, treeIndex));
		
		int size = tree.getAllItems().length;
		if (size - treeItemIndex < 1) {
			throw new SWTLayerException("No matching tree item found");
		}
		if (treeItemPath == null) {
			item = tree.getAllItems()[treeIndex];
			path = new String[] {item.getText()};
		} else {
			List<String> tiPath = new ArrayList<String>(Arrays.asList(treeItemPath));
			// wait method maybe will be needed here
			item = tree.getTreeItem(tiPath.get(0));
			tiPath.remove(0);
			for (String treeItemNode : tiPath) {
				item.expand();
				// wait method maybe will be needed here
				item = item.getNode(treeItemNode);
			}
			path = treeItemPath;
		}
		
	}
	
	public void select() {
		item.select();
	}
	
	public String getText() {
		String text = item.getText();
		return text;
	}
	
	public String getToolTipText() {
		String toolTipText = item.getToolTipText();
		return toolTipText;
	}
	
	@Override
	public String getCell(int index) {
		return item.cell(index);
	}
	
	public String[] getPath() {
		return path;
	}
	
	public void expand(){
		logger.debug("Expanding Tree Item");
		item.expand();
	}
	
	public void collapse() {
		logger.debug("Collapsing Tree Item");
		item.collapse();
	}

	protected List<TreeItem> getItems(boolean shellItem) {
		expand();
		List<TreeItem> items = new LinkedList<TreeItem>();
		for (SWTBotTreeItem childrenTreeItem : item.getItems()) {
			String[] treeItemPath = new String[] {childrenTreeItem.getText()};
			if (shellItem) {
				items.add(new ShellTreeItem(joinTwoArrays(getPath(), treeItemPath)));
			} else {
				items.add(new ViewTreeItem(joinTwoArrays(getPath(), treeItemPath)));
			}
		}
		return items;
	}
	
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
				return new ShellTreeItem(joinTwoArrays(getPath(), treeItemPath));
			} else {
				return new ViewTreeItem(joinTwoArrays(getPath(), treeItemPath));
			}
		}
		else{
			throw new WidgetNotFoundException("There is no Tree Item with text " + text);
		}
	}
	
	public void doubleClick(){
		logger.debug("Double Click Tree Item " + item.getText());
		item.doubleClick();
	}
	
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
	
	public boolean isChecked() {
		return item.isChecked();
	}
	
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
