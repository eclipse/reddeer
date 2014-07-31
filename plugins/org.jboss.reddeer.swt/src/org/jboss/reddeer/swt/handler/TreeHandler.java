package org.jboss.reddeer.swt.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.impl.tree.internal.BasicTree;
import org.jboss.reddeer.swt.impl.tree.internal.BasicTreeItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Contains methods for handling UI operations on
 * {@link org.eclipse.swt.widgets.Tree} widgets.
 * 
 * @author Vlado Pakan
 * 
 */
public class TreeHandler {

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
	public List<TreeItem> getItems(final org.eclipse.swt.widgets.Tree swtTree) {
		return Display.syncExec(new ResultRunnable<List<TreeItem>>() {
			@Override
			public List<TreeItem> run() {
				LinkedList<TreeItem> result = new LinkedList<TreeItem>();
				for (org.eclipse.swt.widgets.TreeItem swtTreeItem : swtTree
						.getItems()) {
					result.addLast(new BasicTreeItem(swtTreeItem));
				}
				return result;
			}
		});
	}

	/**
	 * Selects specified {@link TreeItem}s in currently focused tree.
	 * 
	 * @param treeItems tree items to select
	 */
	public void selectItems(final TreeItem... treeItems) {
		final org.eclipse.swt.widgets.Tree swtTree = treeItems[0].getParent()
				.getSWTWidget();
		setFocus(swtTree);
		Display.syncExec(new Runnable() {
			public void run() {
				List<org.eclipse.swt.widgets.TreeItem> selection = new ArrayList<org.eclipse.swt.widgets.TreeItem>();
				for (TreeItem treeItem : treeItems) {
					selection.add(treeItem.getSWTWidget());
				}
				if (!(SWT.MULTI == (swtTree.getStyle() & SWT.MULTI)) 
						&& treeItems.length > 1) {
					throw new SWTLayerException("Tree does not support SWT.MULTI, cannot make multiple selections");
				}
				swtTree.setSelection(selection
						.toArray(new org.eclipse.swt.widgets.TreeItem[] {}));
			}
		});
		notifySelect(swtTree);
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
	 * Unselects all tree item on specified {@link org.eclipse.swt.widgets.Tree}.
	 * 
	 * @param swtTree tree to handle
	 */
	public void unselectAllItems(final org.eclipse.swt.widgets.Tree swtTree) {
		Display.syncExec(new Runnable() {
			public void run() {
				swtTree.deselectAll();
			}
		});
		notifySelect(swtTree);
	}

	/**
	 * See {@link TreeItem#select()}.
	 * 
	 * @param swtTreeItem tree item to handle
	 */
	public void select(final org.eclipse.swt.widgets.TreeItem swtTreeItem) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				swtTreeItem.getParent().setFocus();
				swtTreeItem.getParent().setSelection(swtTreeItem);
			}
		});
		notifyTree(swtTreeItem, createEventForTree(swtTreeItem, SWT.Selection));
	}

	/**
	 * See {@link TreeItem#getParent()}.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @return parent tree of specified item
	 */
	public Tree getParent(final org.eclipse.swt.widgets.TreeItem swtTreeItem) {
		return Display.syncExec(new ResultRunnable<Tree>() {
			@Override
			public Tree run() {
				return new BasicTree(swtTreeItem.getParent());
			}
		});
	}

	/**
	 * See {@link TreeItem#getPath()}.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @return path to specified tree item in tree
	 */
	public String[] getPath(final org.eclipse.swt.widgets.TreeItem swtTreeItem) {
		return Display.syncExec(new ResultRunnable<String[]>() {
			@Override
			public String[] run() {
				org.eclipse.swt.widgets.TreeItem swttiDummy = swtTreeItem;
				LinkedList<String> items = new LinkedList<String>();
				while (swttiDummy != null) {
					items.addFirst(swttiDummy.getText());
					swttiDummy = swttiDummy.getParentItem();
				}
				return items.toArray(new String[0]);
			}
		});
	}

	/**
	 * See {@link TreeItem#expand}.
	 * 
	 * @param swtTreeItem tree item to handle
	 */
	public void expand(final org.eclipse.swt.widgets.TreeItem swtTreeItem) {
		expand(swtTreeItem, TimePeriod.SHORT);
	}

	/**
	 * See {@link TreeItem#expand(TimePeriod)}.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @param timePeriod time period to wait for
	 */
	public void expand(final org.eclipse.swt.widgets.TreeItem swtTreeItem,
			TimePeriod timePeriod) {

		final TreeExpandListener tel = new TreeExpandListener();

		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				swtTreeItem.getParent().addListener(SWT.Expand, tel);
			}
		});

		try {
			new WaitUntil(new TreeHeardExpandNotification(swtTreeItem, tel,
					false));
		} catch (WaitTimeoutExpiredException ex) {
			new WaitUntil(new TreeHeardExpandNotification(swtTreeItem, tel,
					true));
		}

		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				swtTreeItem.setExpanded(true);
			}
		});
	}

	/**
	 * See {@link TreeItem#isChecked()}.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @return true if specified item is checked, false otherwise
	 */
	public boolean isChecked(final org.eclipse.swt.widgets.TreeItem swtTreeItem) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return swtTreeItem.getChecked();
			}
		});
	}

	/**
	 * See {@link TreeItem#isExpanded()}.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @return true if specified item is expanded, false otherwise
	 */
	public boolean isExpanded(final org.eclipse.swt.widgets.TreeItem swtTreeItem) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return swtTreeItem.getExpanded();
			}
		});
	}

	/**
	 * See {@link TreeItem#isSelected()}.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @return true if specified item is selected, false otherwise
	 */
	public boolean isSelected(final org.eclipse.swt.widgets.TreeItem swtTreeItem) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return Arrays.asList(swtTreeItem.getParent().getSelection())
						.contains(swtTreeItem);
			}
		});
	}

	/**
	 * See {@link TreeItem#collapse()}.
	 * 
	 * @param swtTreeItem tree item to handle
	 */
	public void collapse(final org.eclipse.swt.widgets.TreeItem swtTreeItem) {
		if (isExpanded(swtTreeItem)) {
			Display.syncExec(new Runnable() {
				@Override
				public void run() {
					swtTreeItem.setExpanded(false);
				}
			});
			notifyTree(swtTreeItem,
					createEventForTree(swtTreeItem, SWT.Collapse));
		}
	}

	/**
	 * See {@link TreeItem#getItem(String)}.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @param text text of tree item
	 * @return child item of specified tree item
	 */
	public TreeItem getItem(final org.eclipse.swt.widgets.TreeItem swtTreeItem,
			final String text) {
		expand(swtTreeItem);
		TreeItem result = Display.syncExec(new ResultRunnable<TreeItem>() {
			@Override
			public TreeItem run() {
				org.eclipse.swt.widgets.TreeItem[] items = swtTreeItem
						.getItems();
				boolean isFound = false;
				int index = 0;
				while (!isFound && index < items.length) {
					if (items[index].getText().equals(text)) {
						isFound = true;
					} else {
						index++;
					}
				}
				if (!isFound) {
					return null;
				} else {
					return new BasicTreeItem(items[index]);
				}
			}
		});
		if (result != null) {
			return result;
		} else {
			SWTLayerException exception = new SWTLayerException("Tree Item "
					+ this + " has no Tree Item with text " + text);
			exception.addMessageDetail("Tree Item " + this
					+ " has these direct children:");
			for (TreeItem treeItem : getItems(getParent(swtTreeItem)
					.getSWTWidget())) {
				exception.addMessageDetail("  " + treeItem.getText());
			}
			throw exception;
		}
	}

	private void notifySelect(final org.eclipse.swt.widgets.Tree swtTree) {
		Display.syncExec(new Runnable() {
			public void run() {
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
		event.widget = getParent(swtTreeItem).getSWTWidget();
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

	/**
	 * See {@link TreeItem#setChecked(boolean)}.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @param check check value of specified tree item
	 */
	public void setChecked(final org.eclipse.swt.widgets.TreeItem swtTreeItem,
			final boolean check) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				swtTreeItem.setChecked(check);
			}
		});
		notifyTree(swtTreeItem,
				createEventForTree(swtTreeItem, SWT.Selection, SWT.CHECK));
	}

	/**
	 * Gets children {@link TreeItem}s of specified {@link org.eclipse.swt.widgets.TreeItem}.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @return tree item children
	 */
	public List<TreeItem> getChildrenItems(
			final org.eclipse.swt.widgets.TreeItem swtTreeItem) {
		expand(swtTreeItem, TimePeriod.SHORT);
		return Display.syncExec(new ResultRunnable<List<TreeItem>>() {
			@Override
			public List<TreeItem> run() {
				LinkedList<TreeItem> result = new LinkedList<TreeItem>();
				org.eclipse.swt.widgets.TreeItem[] items = swtTreeItem
						.getItems();
				for (org.eclipse.swt.widgets.TreeItem swtTreeItem : items) {
					result.addLast(new BasicTreeItem(swtTreeItem));
				}
				return result;
			}
		});
	}

	private class TreeExpandListener implements Listener {

		private boolean heard = false;

		@Override
		public void handleEvent(Event arg0) {
			heard = true;
		}

		public boolean isHeard() {
			return heard;
		}

	}

	private class TreeHeardExpandNotification implements WaitCondition {

		private org.eclipse.swt.widgets.TreeItem treeItem;
		private TreeExpandListener listener;
		private boolean sync;

		public TreeHeardExpandNotification(
				org.eclipse.swt.widgets.TreeItem treeItem,
				TreeExpandListener listener, boolean sync) {
			this.treeItem = treeItem;
			this.listener = listener;
			this.sync = sync;
		}

		@Override
		public boolean test() {
			if (!isExpanded(treeItem)) {
				if (sync) {
					notifyTreeSync(treeItem,
							createEventForTree(treeItem, SWT.Expand));
				} else {
					notifyTree(treeItem,
							createEventForTree(treeItem, SWT.Expand));
				}
				return listener.isHeard();
			}
			return true;
		}

		@Override
		public String description() {
			return "tree heard expand notification";
		}
	}
}
