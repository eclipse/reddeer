package org.jboss.reddeer.swt.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.tree.internal.BasicTree;
import org.jboss.reddeer.swt.impl.tree.internal.BasicTreeItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Contains methods that handle UI operations on {@link org.eclipse.swt.widgets.Tree} widgets. 
 * 
 * @author Vlado Pakan
 * 
 */
public class TreeHandler {

	private static TreeHandler instance;
	private final Logger logger = Logger.getLogger(this.getClass());

	private TreeHandler() {

	}

	/**
	 * Creates and returns instance of TreeHandler class
	 * 
	 * @return
	 */
	public static TreeHandler getInstance() {
		if (instance == null) {
			instance = new TreeHandler();
		}
		return instance;
	}

	/**
	 * Returns Tree items
	 * 
	 * @param swtTree
	 * @return
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
	 * @see org.jboss.reddeer.swt.api.Tree#selectItems(TreeItem...)
	 * @param treeItems
	 */
	public void selectItems(final TreeItem... treeItems) {
		logger.debug("Selecting tree items: ");
		final org.eclipse.swt.widgets.Tree swtTree = treeItems[0].getParent().getSWTWidget();
		setFocus(swtTree);
		Display.syncExec(new Runnable() {
			public void run() {
				List<org.eclipse.swt.widgets.TreeItem> selection = new ArrayList<org.eclipse.swt.widgets.TreeItem>();
				for (TreeItem treeItem : treeItems) {
					logger.debug("Tree item to add to selection: "
							+ treeItem.getSWTWidget().getText());
					selection.add(treeItem.getSWTWidget());
				}
				if (!(SWT.MULTI == (swtTree.getStyle() & SWT.MULTI))
						&& treeItems.length > 1)
					logger.warn("Tree does not support SWT.MULTI, cannot make multiple selections"); //$NON-NLS-1$
				logger.debug("Setting Tree selection");
				swtTree.setSelection(selection
						.toArray(new org.eclipse.swt.widgets.TreeItem[] {}));
			}
		});
		notifySelect(swtTree);
		logger.info("Selected Tree Items:");
		for (TreeItem treeItem : treeItems) {
			logger.info("  " + treeItem);
		}
	}

	/**
	 * @see org.jboss.reddeer.swt.api.Tree#setFocus()
	 * @param swtTree
	 */
	public void setFocus(final org.eclipse.swt.widgets.Tree swtTree) {
		Display.syncExec(new Runnable() {
			public void run() {
				if (!swtTree.isFocusControl()) {
					logger.debug("Setting focus to Tree");
					swtTree.forceFocus();
				}
			}
		});
	}

	/**
	 * @see Tree#getColumnCount()
	 * @param swtTree
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
	 * @see org.jboss.reddeer.swt.api.Tree#unselectAllItems()
	 * @param swtTree
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
	 * See {@link TreeItem}
	 * @param swtTreeItem
	 */
	public void select(final org.eclipse.swt.widgets.TreeItem swtTreeItem) {
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
		notifyTree(swtTreeItem,
				createEventForTree(swtTreeItem, SWT.Selection));
		logger.info("Selected: " + this);
	}
	/**
	 * See {@link TreeItem}
	 * @param swtTreeItem
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
	 * See {@link TreeItem}
	 * @param swtTreeItem
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
	 * See {@link TreeItem}
	 * @param swtTreeItem
	 */
	public void expand(final org.eclipse.swt.widgets.TreeItem swtTreeItem){
		expand(swtTreeItem,TimePeriod.SHORT);
	}
	/**
	 * See {@link TreeItem}
	 * @param swtTreeItem
	 * @param timePeriod
	 */
	public void expand(final org.eclipse.swt.widgets.TreeItem swtTreeItem,
			TimePeriod timePeriod) {
		logger.debug("Expanding Tree Item "
				+ WidgetHandler.getInstance().getText(swtTreeItem));

		final TreeExpandListener tel = new TreeExpandListener();
		
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				swtTreeItem.getParent().addListener(SWT.Expand, tel);
			}
		});
		
		new WaitUntil(new TreeHeardExpandNotification(swtTreeItem, tel));
		logger.info("Expanded: " + this);
		
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				swtTreeItem.setExpanded(true);
			}
		});
	}
	/**
	 * See {@link TreeItem}
	 * @param swtTreeItem
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
	 * See {@link TreeItem}
	 * @param swtTreeItem
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
	 * See {@link TreeItem}
	 * @param swtTreeItem
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
	 * See {@link TreeItem}
	 * @param swtTreeItem
	 */
	public void collapse(final org.eclipse.swt.widgets.TreeItem swtTreeItem) {
		logger.debug("Collapsing Tree Item " + WidgetHandler.getInstance().getText(swtTreeItem));
		if (isExpanded(swtTreeItem)) {
			Display.syncExec(new Runnable() {
				@Override
				public void run() {
					logger.debug("Setting tree item " + swtTreeItem.getText() + " collapsed");
					swtTreeItem.setExpanded(false);
				}
			});
			logger.debug("Notify tree about collapse event");
			notifyTree(swtTreeItem,createEventForTree(swtTreeItem,SWT.Collapse));
		} else {
			logger.debug("Tree Item " + WidgetHandler.getInstance().getText(swtTreeItem)
					+ " is already collapsed. No action performed");
		}
		logger.info("Collapsed: " + this);
	}
	/**
	 * Return direct descendant specified with parameters
	 * 
	 * @param swtTreeItem
	 * @param text
	 *            text of tree item which should be returned
	 * @return direct descendant specified with parameters
	 */
	public TreeItem getItem(final org.eclipse.swt.widgets.TreeItem swtTreeItem,final String text) {
		logger.debug("Getting child tree item " + text + " of tree item " + WidgetHandler.getInstance().getText(swtTreeItem));
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
			SWTLayerException exception = new SWTLayerException("Tree Item " + this 
				+ " has no Tree Item with text " + text);
			exception.addMessageDetail("Tree Item " + this + " has these direct children:");
			for (TreeItem treeItem : getItems(getParent(swtTreeItem).getSWTWidget())){
				exception.addMessageDetail("  " + treeItem.getText());
			}
			throw exception;
		}
	}
	/**
	 * Notifies listeners about selection event
	 * @param swtTree
	 * 
	 */
	private void notifySelect(final org.eclipse.swt.widgets.Tree swtTree) {
		Display.syncExec(new Runnable() {
			public void run() {
				logger.debug("Notify Tree about selection event");
				swtTree.notifyListeners(SWT.Selection,
						createSelectionEvent(swtTree));
			}
		});
	}

	/**
	 * Creates selection event
	 * @param swtTree
	 * @return
	 */
	private Event createSelectionEvent(final org.eclipse.swt.widgets.Tree swtTree) {
		Event event = new Event();
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.widget = swtTree;
		event.type = SWT.Selection;
		return event;
	}

	/**
	 * Creates event for tree with specified type and empty detail
	 * 
	 * @param swtTreeItem
	 * @param type
	 * @return
	 */
	public  Event createEventForTree(
			final org.eclipse.swt.widgets.TreeItem swtTreeItem, 
			int type) {
		return createEventForTree(swtTreeItem, type, SWT.NONE);
	}

	/**
	 * Creates event for tree with specified type and detail
	 * 
	 * @param swtTreeItem
	 * @param type
	 * @param detail
	 * @return
	 */
	public Event createEventForTree(
			final org.eclipse.swt.widgets.TreeItem swtTreeItem,
			int type, int detail) {
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
	 * Notifies tree listeners about event event.type field has too be properly
	 * set
	 * 
	 * @param swtTreeItem
	 * @param event
	 */
	public void notifyTree(final org.eclipse.swt.widgets.TreeItem swtTreeItem, final Event event) {
		Display.syncExec(new Runnable() {
			public void run() {
				swtTreeItem.getParent().notifyListeners(event.type, event);
			}
		});
	}
	/**
	 * See {@link TreeItem}
	 * @param swtTreeItem
	 */
	public void setChecked(final org.eclipse.swt.widgets.TreeItem swtTreeItem,
			final boolean check) {
		logger.debug((check ? "Check" : "Uncheck") + "Tree Item " +  WidgetHandler.getInstance().getText(swtTreeItem)
				+ ":");
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				swtTreeItem.setChecked(check);
			}
		});
		logger.debug("Notify tree about check event");
		notifyTree(swtTreeItem,createEventForTree(swtTreeItem,SWT.Selection, SWT.CHECK));
		logger.info((check ? "Checked: " : "Unchecked: ") + this);
	}
	/**
	 * Returns children tree items
	 * 
	 * @param swtTreeItem
	 * @return
	 */
	public List<TreeItem> getChildrenItems(final org.eclipse.swt.widgets.TreeItem swtTreeItem) {
		expand(swtTreeItem,TimePeriod.SHORT);
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
	
	private class TreeExpandListener implements Listener{
		
		private boolean heard = false;

		@Override
		public void handleEvent(Event arg0) {
			heard = true;
		}
		
		public boolean isHeard(){
			return heard;
		}
		
	}
	
	private class TreeHeardExpandNotification implements WaitCondition{
		
		private org.eclipse.swt.widgets.TreeItem treeItem;
		private TreeExpandListener listener;
		
		public TreeHeardExpandNotification(org.eclipse.swt.widgets.TreeItem treeItem, TreeExpandListener listener){
			this.treeItem = treeItem;
			this.listener = listener;
		}

		@Override
		public boolean test() {
			if (!isExpanded(treeItem)) {
				notifyTree(treeItem,createEventForTree(treeItem, SWT.Expand));
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
