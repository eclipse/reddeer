package org.jboss.reddeer.swt.impl.tree;

import java.util.List;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.TreeItemHasMinChildren;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.TreeHandler;
import org.jboss.reddeer.swt.handler.TreeItemHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Basic TreeItem class is abstract class for all Tree Item implementations
 * 
 * @author jjankovi
 * 
 */
public abstract class AbstractTreeItem implements TreeItem {

	protected final Logger logger = Logger.getLogger(this.getClass());

	protected org.eclipse.swt.widgets.TreeItem swtTreeItem;
	
	private TreeHandler treeHandler = TreeHandler.getInstance();

	protected AbstractTreeItem(org.eclipse.swt.widgets.TreeItem swtTreeItem) {
		if (swtTreeItem != null) {
			this.swtTreeItem = swtTreeItem;
		} else {
			throw new SWTLayerException(
					"SWT Tree Item passed to constructor is null");
		}

	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void select() {
		treeHandler.select(swtTreeItem);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtTreeItem);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public String getToolTipText() {
		return TreeItemHandler.getInstance().getToolTipText(swtTreeItem);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public String getCell(final int index) {
		return TreeItemHandler.getInstance().getText(swtTreeItem,index);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public String[] getPath() {
		return treeHandler.getPath(swtTreeItem);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void expand() {
		expand(TimePeriod.SHORT);
	}
	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void expand(TimePeriod timePeriod) {
		treeHandler.expand(swtTreeItem, timePeriod);
	}
	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void collapse() {
		treeHandler.collapse(swtTreeItem);
	}

	/**
	 * Return direct descendant specified with parameters
	 * 
	 * @param text
	 *            text of tree item which should be returned
	 * @return direct descendant specified with parameters
	 */
	public TreeItem getItem(final String text) {
		return treeHandler.getItem(swtTreeItem, text);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void doubleClick() {
		logger.debug("Double Click Tree Item " + getText());
		select();
		logger.debug("Notify tree about mouse double click event");
		treeHandler.notifyTree(getSWTWidget(),treeHandler.createEventForTree(getSWTWidget(),SWT.MouseDoubleClick));
		logger.debug("Notify tree about default selection event");
		treeHandler.notifyTree(getSWTWidget(),treeHandler.createEventForTree(getSWTWidget(),SWT.DefaultSelection));
		logger.info("Double Clicked on: " + this);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public boolean isSelected() {
		return treeHandler.isSelected(swtTreeItem);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public boolean isDisposed() {
		return swtTreeItem.isDisposed();
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void setChecked(final boolean check) {
		treeHandler.setChecked(swtTreeItem, check);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public boolean isChecked() {
		return treeHandler.isChecked(swtTreeItem);
	}

	/**
	 * Return swt widget of Tree Item
	 */
	public org.eclipse.swt.widgets.TreeItem getSWTWidget() {
		return swtTreeItem;
	}

	/**
	 * Returns children tree items
	 * 
	 * @return
	 */
	@Override
	public List<TreeItem> getItems() {
		expand(TimePeriod.SHORT);
		return treeHandler.getChildrenItems(swtTreeItem);
	}

	/**
	 * See {@link TreeItem}
	 */
	@Override
	public Tree getParent() {
		return treeHandler.getParent(swtTreeItem);
	}
	/**
	 * See {@link TreeItem}
	 */
	@Override
	public boolean isExpanded() {
		return treeHandler.isExpanded(swtTreeItem);
	}
	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void expand(int minItemsCount) {
		expand(minItemsCount, TimePeriod.SHORT);
	}
	/**
	 * See {@link TreeItem}
	 */
	@Override
	public void expand(int minItemsCount , TimePeriod timePeriod) {
		expand();
		new WaitUntil(new TreeItemHasMinChildren(this, minItemsCount), timePeriod);
	}
	@Override
	public String toString(){
		StringBuffer result = new StringBuffer("TreeItem: ");
		boolean isFirst = true;
		for (String pathItem : this.getPath()){
			if (isFirst){
				isFirst = false;
			} else{
				result.append(" > ");	
			}
			result.append(pathItem);
			
		}
		return result.toString();
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtTreeItem);
	}
}