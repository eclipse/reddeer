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
package org.jboss.reddeer.core.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TreeColumn;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on
 * {@link org.eclipse.swt.widgets.Tree} widgets.
 * 
 * @author Vlado Pakan
 * 
 */
public class TreeHandler {

	private static final Logger logger = Logger.getLogger(TreeHandler.class);
	
	private static TreeHandler instance;

	private TreeHandler() {

	}

	/**
	 * Gets instance of TreeHandler.
	 * 
	 * @return instance of TreeHandler
	 */
	public static TreeHandler getInstance() {
		if (instance == null) {
			instance = new TreeHandler();
		}
		return instance;
	}

	/**
	 * Gets all direct descendants in specified {@link org.eclipse.swt.widgets.Tree}.
	 * 
	 * @param swtTree tree to handle
	 * @return descendants of specified tree
	 */
	public List<org.eclipse.swt.widgets.TreeItem> getSWTItems(final org.eclipse.swt.widgets.Tree swtTree) {
		return Display.syncExec(new ResultRunnable<List<org.eclipse.swt.widgets.TreeItem>>() {
			@Override
			public List<org.eclipse.swt.widgets.TreeItem> run() {
				return Arrays.asList(swtTree.getItems());
			}
		});
	}

	/**
	 * Sets focus on specified {@link org.eclipse.swt.widgets.Tree}.
	 * 
	 * @param swtTree tree to focus
	 */
	public void setFocus(final org.eclipse.swt.widgets.Tree swtTree) {
		Display.syncExec(new Runnable() {
			public void run() {
				if (!swtTree.isFocusControl()) {
					logger.debug("Set focus to Tree");
					swtTree.forceFocus();
				}
			}
		});
	}

	/**
	 * Gets count of columns of specified {@link org.eclipse.swt.widgets.Tree}.
	 * 
	 * @param swtTree tree to handle
	 * @return count of columns of specified tree
	 */
	public int getColumnCount(final org.eclipse.swt.widgets.Tree swtTree) {
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return swtTree.getColumnCount();
			}
		});
	}

	/**
	 * Gets text of header columns of specified {@link org.eclipse.swt.widgets.Tree}.
	 * 
	 * @param swtTree tree to get header columns text
	 * @return list of text from individual header columns
	 */
	public List<String> getHeaderColumns(final org.eclipse.swt.widgets.Tree swtTree) {
        return Display.syncExec(new ResultRunnable<List<String>>() {
            @Override
            public List<String> run() {
                List<String> columnTexts = new ArrayList<String>();
                TreeColumn[] columns = swtTree.getColumns();
                for (TreeColumn column: columns) {
                    columnTexts.add(column.getText());
                }
                return columnTexts;
            }
        });
    }
	
	/**
	 * Unselects all tree item on specified {@link org.eclipse.swt.widgets.Tree}.
	 * 
	 * @param swtTree tree to handle
	 */
	public void unselectAllItems(final org.eclipse.swt.widgets.Tree swtTree) {
		Display.syncExec(new Runnable() {
			public void run() {
				logger.debug("Unselect all tree items");
				swtTree.deselectAll();
			}
		});
		notifySelect(swtTree);
		logger.info("All tree items unselected");
	}
	
	/**
	 * Gets a selection of a given {@link org.eclipse.swt.widgets.Tree}.
	 * 
	 * @param swtTree tree
	 * @return a selection of the tree
	 */
	public org.eclipse.swt.widgets.TreeItem[] getSelection(final org.eclipse.swt.widgets.Tree swtTree) {
		return Display.syncExec(new ResultRunnable<org.eclipse.swt.widgets.TreeItem[]>() {

			@Override
			public org.eclipse.swt.widgets.TreeItem[] run() {
				return swtTree.getSelection();
			}

		});
	}

	/**
	 * Notify select.
	 *
	 * @param swtTree the swt tree
	 */
	public void notifySelect(final org.eclipse.swt.widgets.Tree swtTree) {
		Display.syncExec(new Runnable() {
			public void run() {
				logger.debug("Notify Tree about selection event");
				swtTree.notifyListeners(SWT.Selection,
						createSelectionEvent(swtTree));
			}
		});
	}

	private Event createSelectionEvent(
			final org.eclipse.swt.widgets.Tree swtTree) {
		Event event = new Event();
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.widget = swtTree;
		event.type = SWT.Selection;
		return event;
	}

	/**
	 * Creates the event for specified {@link org.eclipse.swt.widgets.TreeItem} 
	 * of specified type and with empty details.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @param type type of the event
	 * @return event of specified type for specified tree item
	 */
	public Event createEventForTree(
			final org.eclipse.swt.widgets.TreeItem swtTreeItem, int type) {
		return createEventForTree(swtTreeItem, type, SWT.NONE);
	}

	/**
	 * Creates the event for specified {@link org.eclipse.swt.widgets.TreeItem} 
	 * of specified type.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @param type type of the event
	 * @param detail details of the event
	 * @return event of specified type for specified tree item with specified details
	 */
	public Event createEventForTree(
			final org.eclipse.swt.widgets.TreeItem swtTreeItem, int type,
			int detail) {
		Event event = new Event();
		event.type = type;
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.item = swtTreeItem;
		event.widget = TreeItemHandler.getInstance().getParent(swtTreeItem);
		event.detail = detail;
		return event;
	}

	/**
	 * Notifies listeners of specified {@link org.eclipse.swt.widgets.TreeItem}
	 * about specified event asynchronously. Field for event type in specified 
	 * event has to be set properly.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @param event event for specified tree item
	 */
	public void notifyTree(final org.eclipse.swt.widgets.TreeItem swtTreeItem,
			final Event event) {
		Display.asyncExec(new Runnable() {
			public void run() {
				swtTreeItem.getParent().notifyListeners(event.type, event);
			}
		});
	}
	
	/**
	 * Notifies listeners of specified {@link org.eclipse.swt.widgets.TreeItem}
	 * about specified event asynchronously. Field for event type in specified 
	 * event has to be set properly.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @param eventType - type of event
	 * @param event event for specified tree item
	 */
	public void notifyTree(final org.eclipse.swt.widgets.TreeItem swtTreeItem,
			final int eventType , final Event event) {
		Display.asyncExec(new Runnable() {
			public void run() {
				swtTreeItem.getParent().notifyListeners(eventType, event);
			}
		});
	}

	/**
	 * Notifies listeners of specified {@link org.eclipse.swt.widgets.TreeItem}
	 * about specified event synchronously. Field for event type in specified 
	 * event has to be set properly.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @param event event for specified tree item
	 */
	public void notifyTreeSync(final org.eclipse.swt.widgets.TreeItem swtTreeItem,
			final Event event) {
		Display.syncExec(new Runnable() {
			public void run() {
				swtTreeItem.getParent().notifyListeners(event.type, event);
			}
		});
	}
}
