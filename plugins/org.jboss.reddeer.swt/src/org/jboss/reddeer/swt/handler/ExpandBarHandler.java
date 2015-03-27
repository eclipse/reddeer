package org.jboss.reddeer.swt.handler;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.widgets.ExpandItem;
import org.jboss.reddeer.swt.api.ExpandBarItem;
import org.jboss.reddeer.swt.impl.expandbar.internal.BasicExpandBarItem;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

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
	 * Gets items nested in specified {@link org.eclipse.swt.widgets.ExpandBar}.
	 * 
	 * @param expandBar expand bar to handle
	 * @return list of nested expand bar items on specified expand bar
	 * @deprecated since 0.7. Use {link {@link ExpandBarHandler#getSWTItems(org.eclipse.swt.widgets.ExpandBar)} instead
	 */
	@Deprecated
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
	 * Gets SWT items nested in specified {@link org.eclipse.swt.widgets.ExpandBar}.
	 * 
	 * @param expandBar expand bar to handle
	 * @return list of nested expand items on specified expand bar
	 */
	public List<org.eclipse.swt.widgets.ExpandItem> getSWTItems(
			final org.eclipse.swt.widgets.ExpandBar expandBar) {
		return Display.syncExec(new ResultRunnable<List<org.eclipse.swt.widgets.ExpandItem>>() {
			@Override
			public List<org.eclipse.swt.widgets.ExpandItem> run() {
				LinkedList<ExpandItem> result = new LinkedList<ExpandItem>();
				for (ExpandItem item: expandBar.getItems()) {
					result.addFirst(item);
				}
				return result;
				
			}
		});
	}
}
