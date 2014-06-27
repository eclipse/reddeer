package org.jboss.reddeer.swt.handler;

import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.swt.api.ExpandBar;
import org.jboss.reddeer.swt.api.ExpandBarItem;
import org.jboss.reddeer.swt.impl.expandbar.internal.BasicExpandBarItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on
 * {@link org.eclipse.swt.widgets.ExpandBar} widgets.
 * 
 * @author Vlado Pakan
 *
 */
public class ExpandBarHandler {

	private static ExpandBarHandler instance;

	private ExpandBarHandler() {

	}

	/**
	 * Gets instance of ExpandBarHandler.
	 * 
	 * @return instance of ExpandBarHandler
	 */
	public static ExpandBarHandler getInstance() {
		if (instance == null) {
			instance = new ExpandBarHandler();
		}
		return instance;
	}

	/**
	 * See {@link ExpandBar#expandAll}.
	 * 
	 * @deprecated This is not UI handling operation. Moved to the implementation
	 * of the widget. Will be removed in version 0.6.
	 * @param expandBar expand bar to handle
	 */
	public static void expandAll(final ExpandBar expandBar) {
		for (ExpandBarItem expandBarItem : expandBar.getItems()) {
			expandBarItem.expand();
		}

	}

	/**
	 * See {@link ExpandBar#collapseAll}.
	 * 
	 * @deprecated This is not UI handling operation. Moved to the implementation
	 * of the widget. Will be removed in version 0.6.
	 * @param expandBar expand bar to handle
	 */
	public static void collapseAll(final ExpandBar expandBar) {
		for (ExpandBarItem expandBarItem : expandBar.getItems()) {
			expandBarItem.collapse();
		}
	}

	/**
	 * Gets items in specified {@link ExpandBar}.
	 * 
	 * @deprecated Use non-static method instead. Will be removed in 0.6.
	 * @param expandBar expand bar to handle
	 * @return list of nested expand bar items on specified expand bar
	 */
	public static List<ExpandBarItem> getItems(final ExpandBar expandBar) {
		return Display.syncExec(new ResultRunnable<List<ExpandBarItem>>() {
			@Override
			public List<ExpandBarItem> run() {
				LinkedList<ExpandBarItem> result = new LinkedList<ExpandBarItem>();
				for (org.eclipse.swt.widgets.ExpandItem swtExpandItem : expandBar
						.getSWTWidget().getItems()) {
					result.addFirst(new BasicExpandBarItem(swtExpandItem));
				}
				return result;
			}
		});
	}

	/**
	 * Gets items nested in specified {@link org.eclipse.swt.widgets.ExpandBar}.
	 * 
	 * @param expandBar expand bar to handle
	 * @return list of nested expand bar items on specified expand bar
	 */
	public List<ExpandBarItem> getItems(
			final org.eclipse.swt.widgets.ExpandBar expandBar) {
		return Display.syncExec(new ResultRunnable<List<ExpandBarItem>>() {
			@Override
			public List<ExpandBarItem> run() {
				LinkedList<ExpandBarItem> result = new LinkedList<ExpandBarItem>();
				for (org.eclipse.swt.widgets.ExpandItem swtExpandItem : expandBar
						.getItems()) {
					result.addFirst(new BasicExpandBarItem(swtExpandItem));
				}
				return result;
			}
		});
	}

	/**
	 * See {@link ExpandBar#getItemsCount}.
	 * 
	 * @deprecated This is not UI handling operation. Moved to the implementation
	 * of the widget. Will be removed in version 0.6.
	 * @param expandBar expand bar to handle
	 * @return count of nested items on specified expand bar
	 */
	public static int getItemsCount(final ExpandBar expandBar) {
		return ExpandBarHandler.getItems(expandBar).size();
	}
}
