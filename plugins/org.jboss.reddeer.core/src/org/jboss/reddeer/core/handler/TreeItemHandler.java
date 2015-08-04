package org.jboss.reddeer.core.handler;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.handler.WidgetHandler;

/**
 * Contains methods for handling UI operations on {@link TreeItem} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class TreeItemHandler {

	private static final Logger logger = Logger.getLogger(TreeItemHandler.class);
	
	private static TreeItemHandler instance;

	private TreeItemHandler() {

	}

	/**
	 * Gets instance of TreeItemHandler.
	 * 
	 * @return instance of TreeItemHandler
	 */
	public static TreeItemHandler getInstance() {
		if (instance == null) {
			instance = new TreeItemHandler();
		}
		return instance;
	}

	/**
	 * Gets text from cell of specified {@link TreeItem} on the position specified by index.
	 * 
	 * @param treeItem tree item to handle
	 * @param cellIndex index of cell to get text
	 * @return text of the cell
	 */
	public String getText(final TreeItem treeItem, final int cellIndex) {
		String text = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return treeItem.getText(cellIndex);
			}
		});
		return text;
	}

	/**
	 * Gets tool tip of specified {@link TreeItem}.
	 * 
	 * @param item item to handle
	 * @return tool tip text of specified tree item
	 */
	public String getToolTipText(final TreeItem item) {
		String text = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return item.getParent().getToolTipText();
			}
		});
		return text;
	}

	/**
	 * Finds out whether specified {@link TreeItem} is checked or not.
	 * 
	 * @param item item to handle
	 * @return true if specified tree item is expanded, false otherwise
	 */
	public boolean isExpanded(final TreeItem item) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return item.getExpanded();
			}
		});
	}
	
	
	/**
	 * Sets specified text to column on the position specified 
	 * by index in specified {@link TreeItem}. 
	 * 
	 * @param treeItem tree item to handle
	 * @param cellIndex index of cell to set text
	 * @param text text to set
	 */
	public void setText(final TreeItem treeItem, final int cellIndex, final String text) {
		Display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				treeItem.setText(cellIndex, text);
			}
		});
	}
	
	/**
	 * Selects specified {@link TreeItem}s in currently focused tree.
	 * @param treeItems tree items to select
	 */
	public void selectItems(final TreeItem... selection) {
		logger.info("Select tree items: ");
		final Tree swtTree = getParent(selection[0]);
		TreeHandler.getInstance().setFocus(swtTree);
		
		Display.syncExec(new Runnable() {
			public void run() {
				if (!(SWT.MULTI == (swtTree.getStyle() & SWT.MULTI)) 
						&& selection.length > 1) {
					throw new CoreLayerException("Tree does not support SWT.MULTI, cannot make multiple selections");
				}
				logger.debug("Set Tree selection");
				swtTree.setSelection(selection);
			}
		});
		TreeHandler.getInstance().notifySelect(swtTree);
		logger.debug("Selected Tree Items:");
		for (TreeItem treeItem : selection) {
			logger.debug("  " + WidgetHandler.getInstance().getText(treeItem));
		}
	}
	
	/**
	 * See {@link TreeItem#select()}.
	 * 
	 * @param swtTreeItem tree item to handle
	 */
	public void select(final TreeItem swtTreeItem) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				logger.debug("Selecting tree item: " + swtTreeItem.getText());
				swtTreeItem.getParent().setFocus();
				swtTreeItem.getParent().setSelection(swtTreeItem);
			}
		});
		logger.debug("Notify tree item "
				+ WidgetHandler.getInstance().getText(swtTreeItem)
				+ " about selection");
		TreeHandler.getInstance().notifyTree(swtTreeItem, TreeHandler.getInstance().createEventForTree(swtTreeItem, SWT.Selection));
		logger.info("Selected tree item: " + WidgetHandler.getInstance().getText(swtTreeItem));
	}
	
	/**
	 * See {@link TreeItem#getItem(String)}.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @param text text of tree item
	 * @return child item of specified tree item
	 */
	public TreeItem getItem(final TreeItem swtTreeItem,
			final String text) {
		logger.debug("Get child tree item " + text + " of tree item "
				+ WidgetHandler.getInstance().getText(swtTreeItem));
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
					return items[index];
				}
			}
		});
		if (result != null) {
			return result;
		} else {
			CoreLayerException exception = new CoreLayerException("Tree Item "
					+ this + " has no Tree Item with text " + text);
			exception.addMessageDetail("Tree Item " + this
					+ " has these direct children:");
			for (TreeItem treeItem : TreeHandler.getInstance().getSWTItems(getParent(swtTreeItem))) {
				exception.addMessageDetail("  " + getText(treeItem, 0));
			}
			throw exception;
		}
	}
	
	/**
	 * Gets children {@link TreeItem}s of specified {@link org.eclipse.swt.widgets.TreeItem}.
	 * @param swtTreeItem tree item to handle
	 * @return tree item children
	 */
	public List<TreeItem> getChildrenItems( final TreeItem swtTreeItem) {
		expand(swtTreeItem, TimePeriod.SHORT);
		return Display.syncExec(new ResultRunnable<List<TreeItem>>() {
			@Override
			public List<TreeItem> run() {
				org.eclipse.swt.widgets.TreeItem[] items = swtTreeItem.getItems();
				return Arrays.asList(items);
			}
		});
	}
	
	/**
	 * See {@link TreeItem#getParent()}.
	 * @param swtTreeItem tree item to handle
	 * @return parent tree of specified item
	 */
	public Tree getParent(final TreeItem swtTreeItem) {
		return Display.syncExec(new ResultRunnable<Tree>() {
			@Override
			public Tree run() {
				return swtTreeItem.getParent();
			}
		});
	}
	
	/**
	 * See {@link TreeItem#getPath()}.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @return path to specified tree item in tree
	 */
	public String[] getPath(final TreeItem swtTreeItem) {
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
	 * See {@link TreeItem#isChecked()}.
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
	 * See {@link TreeItem#setChecked(boolean)}.
	 * @param swtTreeItem tree item to handle
	 * @param check check value of specified tree item
	 */
	public void setChecked(final TreeItem swtTreeItem,
			final boolean check) {
		logger.debug((check ? "Check" : "Uncheck") + "Tree Item "
				+ WidgetHandler.getInstance().getText(swtTreeItem) + ":");
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				swtTreeItem.setChecked(check);
			}
		});
		logger.debug("Notify tree about check event");
		TreeHandler.getInstance().notifyTree(swtTreeItem,
				TreeHandler.getInstance().createEventForTree(swtTreeItem, SWT.Selection, SWT.CHECK));
		logger.info((check ? "Checked: " : "Unchecked: ") + WidgetHandler.getInstance().getText(swtTreeItem));
	}
	
	/**
	 * See {@link TreeItem#isSelected()}.
	 * 
	 * @param swtTreeItem tree item to handle
	 * @return true if specified item is selected, false otherwise
	 */
	public boolean isSelected(final TreeItem swtTreeItem) {
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
	 * @param swtTreeItem tree item to handle
	 */
	public void collapse(final TreeItem swtTreeItem) {
		logger.debug("Collapse Tree Item "
				+ WidgetHandler.getInstance().getText(swtTreeItem));
		if (isExpanded(swtTreeItem)) {
			Display.syncExec(new Runnable() {
				@Override
				public void run() {
					logger.debug("Setting tree item " + swtTreeItem.getText()
							+ " collapsed");
					swtTreeItem.setExpanded(false);
				}
			});
			logger.debug("Notify tree about collapse event");
			TreeHandler.getInstance().notifyTree(swtTreeItem,
					TreeHandler.getInstance().createEventForTree(swtTreeItem, SWT.Collapse));
		} else {
			logger.debug("Tree Item "
					+ WidgetHandler.getInstance().getText(swtTreeItem)
					+ " is already collapsed. No action performed");
		}
		logger.info("Collapsed: " + WidgetHandler.getInstance().getText(swtTreeItem));
	}
	
	/**
	 * See {@link TreeItem#expand}.
	 * @param swtTreeItem tree item to handle
	 */
	public void expand(final TreeItem swtTreeItem) {
		expand(swtTreeItem, TimePeriod.SHORT);
	}

	/**
	 * See {@link TreeItem#expand(TimePeriod)}.
	 * @param swtTreeItem tree item to handle
	 * @param timePeriod time period to wait for
	 */
	public void expand(final TreeItem swtTreeItem, TimePeriod timePeriod) {
		logger.debug("Expand Tree Item "
				+ WidgetHandler.getInstance().getText(swtTreeItem));

		final TreeExpandListener tel = new TreeExpandListener();

		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				swtTreeItem.getParent().addListener(SWT.Expand, tel);
			}
		});

		try {
			new WaitUntil(new TreeHeardExpandNotification(swtTreeItem, tel,
					false), timePeriod);
		} catch (WaitTimeoutExpiredException ex) {
			new WaitUntil(new TreeHeardExpandNotification(swtTreeItem, tel,
					true), timePeriod);
		} catch (CoreLayerException ex) {
			if (!swtTreeItem.isDisposed()) {
				throw ex;
			}
		}
		logger.info("Expanded: " + WidgetHandler.getInstance().getText(swtTreeItem));

		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				swtTreeItem.setExpanded(true);
				swtTreeItem.getParent().update();
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
					TreeHandler.getInstance().notifyTreeSync(treeItem,
							TreeHandler.getInstance().createEventForTree(treeItem, SWT.Expand));
				} else {
					TreeHandler.getInstance().notifyTree(treeItem,
							TreeHandler.getInstance().createEventForTree(treeItem, SWT.Expand));
				}
				return listener.isHeard();
			} else {
				logger.debug("Tree Item "
						+ WidgetHandler.getInstance().getText(treeItem)
						+ " is already expanded. No action performed");
			}
			return true;
		}

		@Override
		public String description() {
			return "tree heard expand notification";
		}

	}
}
