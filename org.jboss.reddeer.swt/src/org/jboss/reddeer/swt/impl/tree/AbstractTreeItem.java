package org.jboss.reddeer.swt.impl.tree;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.swt.api.TreeItem;

public abstract class AbstractTreeItem implements TreeItem {

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	protected SWTBotTreeItem item;
	
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
	
	public void expand(){
		logger.debug("Expanding Tree Item");
		item.expand();
	}
	
	/**
	 * Returns all (direct) subnodes of this TreeItem
	 * 
	 * @return
	 */
	public List<TreeItem> getItems(){
		expand();
		List<TreeItem> list = new LinkedList<TreeItem>();
		for (SWTBotTreeItem treeItem : item.getItems()) {
			list.add(new DefaultTreeItem(treeItem.widget));
		}
		return list;
	}
	
	public TreeItem getItem (String text){
		item.expand();
		SWTBotTreeItem[] items = item.getItems();
		int index = 0;
		while (index < items.length && !items[index].getText().equals(text)){
			index++;
		}
		if (index < items.length){
			return new DefaultTreeItem(items[index].widget);
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
	public void check() {
		item.check();
	}
	
	public boolean isChecked() {
		return item.isChecked();
	}
}
