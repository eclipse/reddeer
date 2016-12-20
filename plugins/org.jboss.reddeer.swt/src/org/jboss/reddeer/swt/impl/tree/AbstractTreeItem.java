/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.impl.tree;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.handler.TreeHandler;
import org.jboss.reddeer.core.handler.TreeItemHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.lookup.TreeItemLookup;
import org.jboss.reddeer.core.matcher.TreeItemTextMatcher;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.TreeItemHasMinChildren;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Basic TreeItem class is abstract class for all Tree Item implementations
 * 
 * @author jjankovi, mlabuda@redhat.com
 * 
 */
public abstract class AbstractTreeItem extends AbstractWidget<org.eclipse.swt.widgets.TreeItem> implements TreeItem {

	private static final Logger logger = Logger.getLogger(AbstractTreeItem.class);

	private TreeHandler treeHandler = TreeHandler.getInstance();
	private TreeItemHandler treeItemHandler = TreeItemHandler.getInstance();
	
	protected AbstractTreeItem(org.eclipse.swt.widgets.TreeItem swtWidget) {
		super(swtWidget);
	}

	/**
	 * See {@link TreeItem}.
	 */
	@Override
	public void select() {
		logger.info("Select tree item " + getText());
		treeItemHandler.select(swtWidget);
	}

	/**
	 * See {@link TreeItem}.
	 *
	 * @return the text
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}

	/**
	 * See {@link TreeItem}.
	 *
	 * @return the tool tip text
	 */
	@Override
	public String getToolTipText() {
		return TreeItemHandler.getInstance().getToolTipText(swtWidget);
	}

	/**
	 * See {@link TreeItem}.
	 *
	 * @param index the index
	 * @return the cell
	 */
	@Override
	public String getCell(final int index) {
		return TreeItemHandler.getInstance().getText(swtWidget, index);
	}

	/**
	 * See {@link TreeItem}.
	 *
	 * @return the path
	 */
	@Override
	public String[] getPath() {
		return treeItemHandler.getPath(swtWidget);
	}

	/**
	 * See {@link TreeItem}.
	 */
	@Override
	public void expand() {
		expand(TimePeriod.SHORT);
	}

	/**
	 * See {@link TreeItem}.
	 *
	 * @param timePeriod the time period
	 */
	@Override
	public void expand(TimePeriod timePeriod) {
		logger.info("Expand tree item " + getText() + " and wait with time period " 
			+ timePeriod.getSeconds());
		treeItemHandler.expand(swtWidget, timePeriod);
	}

	/**
	 * See {@link TreeItem}.
	 */
	@Override
	public void collapse() {
		logger.info("Collapse tree item " + getText());
		treeItemHandler.collapse(swtWidget);
	}

	/**
	 * Return direct descendant specified with parameters.
	 *
	 * @param text            text of tree item which should be returned
	 * @return direct descendant specified with parameters
	 */
	public TreeItem getItem(final String text) {
		return new DefaultTreeItem(treeItemHandler.getItem(swtWidget, text));
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.TreeItem#getItem(java.lang.String[])
	 */
	@Override
	public TreeItem getItem(String... path) {
		org.eclipse.swt.widgets.TreeItem swtItem = TreeItemLookup.getInstance().getTreeItem(getSWTWidget(), 0, createMatchers(path));
		return new DefaultTreeItem(swtItem);
	}

	/**
	 * See {@link TreeItem}.
	 */
	@Override
	public void doubleClick() {
		logger.info("Double click tree item " + getText());
		select();
		logger.debug("Notify tree about mouse double click event");
		treeHandler.notifyTree(getSWTWidget(), treeHandler.createEventForTree(
				getSWTWidget(), SWT.MouseDoubleClick));
		logger.debug("Notify tree about default selection event");
		treeHandler.notifyTree(getSWTWidget(), treeHandler.createEventForTree(
				getSWTWidget(), SWT.DefaultSelection));
		
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				// flush events
			}
		});
		
		logger.debug("Double click successfull");
	}

	/**
	 * See {@link TreeItem}.
	 *
	 * @return true, if is selected
	 */
	@Override
	public boolean isSelected() {
		return treeItemHandler.isSelected(swtWidget);
	}

	/**
	 * See {@link TreeItem}.
	 *
	 * @param check the new checked
	 */
	@Override
	public void setChecked(final boolean check) {
		logger.info("Check tree item " + getText());
		treeItemHandler.setChecked(swtWidget, check);
	}

	/**
	 * See {@link TreeItem}.
	 *
	 * @return true, if is checked
	 */
	@Override
	public boolean isChecked() {
		return treeItemHandler.isChecked(swtWidget);
	}

	/**
	 * Return swt widget of Tree Item.
	 *
	 * @return the SWT widget
	 */
	public org.eclipse.swt.widgets.TreeItem getSWTWidget() {
		return swtWidget;
	}

	/**
	 * Returns children tree items.
	 *
	 * @return the items
	 */
	@Override
	public List<TreeItem> getItems() {
		expand(TimePeriod.SHORT);
		LinkedList<TreeItem> items = new LinkedList<TreeItem>();
		List<org.eclipse.swt.widgets.TreeItem> eclipseItems = treeItemHandler.getChildrenItems(swtWidget);
		for (org.eclipse.swt.widgets.TreeItem swtTreeItem : eclipseItems) {
			items.addLast(new DefaultTreeItem(swtTreeItem));
		}
		return items;
	}

	/**
	 * See {@link TreeItem}.
	 *
	 * @return the parent
	 */
	@Override
	public Tree getParent() {
		return new DefaultTree(treeItemHandler.getParent(swtWidget));
	}

	/**
	 * See {@link TreeItem}.
	 *
	 * @return true, if is expanded
	 */
	@Override
	public boolean isExpanded() {
		return treeItemHandler.isExpanded(swtWidget);
	}

	/**
	 * See {@link TreeItem}.
	 *
	 * @param minItemsCount the min items count
	 */
	@Override
	public void expand(int minItemsCount) {
		expand(minItemsCount, TimePeriod.SHORT);
	}

	/**
	 * See {@link TreeItem}.
	 *
	 * @param minItemsCount the min items count
	 * @param timePeriod the time period
	 */
	@Override
	public void expand(int minItemsCount, TimePeriod timePeriod) {
		logger.info("Expand tree item " + getText() + 
			" and wait for at least " + minItemsCount + " items");
		expand();
		new WaitUntil(new TreeItemHasMinChildren(this, minItemsCount),
				timePeriod);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer("TreeItem: ");
		boolean isFirst = true;
		for (String pathItem : this.getPath()) {
			if (isFirst) {
				isFirst = false;
			} else {
				result.append(" > ");
			}
			result.append(pathItem);

		}
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.TreeItem#setText(java.lang.String, int)
	 */
	@Override
	public void setText(String text, int index) {
		logger.info("Set text to tree item at index " + index + ": " + text);
		TreeItemHandler.getInstance().setText(swtWidget, index, text);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.TreeItem#setText(java.lang.String)
	 */
	@Override
	public void setText(String text) {
		logger.info("Set text to tree item: " + text);
		TreeItemHandler.getInstance().setText(swtWidget, 0, text);
	}
	
	private Matcher<org.eclipse.swt.widgets.TreeItem>[] createMatchers(String[] treeItemPath) {
		@SuppressWarnings("unchecked")
		Matcher<org.eclipse.swt.widgets.TreeItem>[] matchers = new Matcher[treeItemPath.length];
		for (int i = 0; i < treeItemPath.length; i++){
			matchers[i] = new TreeItemTextMatcher(treeItemPath[i]);
		}
		return matchers;
	}
	
	class TreeItemTexts {
		private String nonStyledText;
		private String[] styledTexts;
		
		public TreeItemTexts(String nonStyledText, String[] styledTexts) {
			this.nonStyledText = nonStyledText;
			this.styledTexts = styledTexts;
		}
		
		public String getNonStyledText() {
			return nonStyledText;
		}
		
		public String[] getStyledTexts() {
			return styledTexts;
		}
	}
}