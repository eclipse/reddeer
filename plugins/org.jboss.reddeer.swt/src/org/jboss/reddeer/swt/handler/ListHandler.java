package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.jboss.reddeer.core.exception.SWTLayerException;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link List} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class ListHandler {

	private static ListHandler instance;

	private ListHandler() {

	}

	/**
	 * Gets instance of ListHandler.
	 * 
	 * @return instance of ListHandler
	 */
	public static ListHandler getInstance() {
		if (instance == null) {
			instance = new ListHandler();
		}
		return instance;
	}

	/**
	 * Gets items from specified {@link List}.
	 * 
	 * @param list list to handle
	 * @return items from specified list
	 */
	public String[] getItems(final List list) {
		return Display.syncExec(new ResultRunnable<String[]>() {

			@Override
			public String[] run() {
				return list.getItems();
			}
		});
	}

	/**
	 * Deselects all items of specified {@link List}.
	 * 
	 * @param list list to handle
	 */
	public void deselectAll(final List list) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				list.deselectAll();
			}
		});
	}

	/**
	 * Selects all items of specified {@link List}.
	 * 
	 * @param list list to handle
	 */
	public void selectAll(final List list) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if ((list.getStyle() & SWT.MULTI) != 0) {
					list.selectAll();
					WidgetHandler.getInstance().notify(SWT.Selection, list);
				} else {
					throw new SWTLayerException(
							"List does not support multi selection - it does not have SWT MULTI style");
				}
			}
		});
	}

	/**
	 * Gets item specified by text from specified {@link List}.
	 * 
	 * @param list list to handle
	 * @param item item to select
	 */
	public void select(final List list, final String item) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				int index = (list.indexOf(item));
				if (index == -1) {
					throw new SWTLayerException("Unable to select item " + item
							+ " because it does not exist");
				}
				list.select(list.indexOf(item));
				WidgetHandler.getInstance().sendClickNotifications(list);
			}
		});
	}

	/**
	 * Selects items specified by their text from specified {@link List}.
	 * 
	 * @param list list to handle
	 * @param items items to select
	 */
	public void select(final List list, final String[] items) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if ((list.getStyle() & SWT.MULTI) != 0) {
					for (String item : items) {
						int index = (list.indexOf(item));
						if (index == -1) {
							throw new SWTLayerException(
									"Unable to select item " + item
											+ " because it does not exist");
						}
						list.select(list.indexOf(item));
						WidgetHandler.getInstance().notify(SWT.Selection, list);
					}
				} else {
					throw new SWTLayerException(
							"List does not support multi selection - it does not have SWT MULTI style");
				}
			}
		});
	}

	/**
	 * Selects items on specified indices in specified {@link List}.
	 * 
	 * @param list list to handle
	 * @param indices indices of items to select
	 */
	public void select(final List list, final int[] indices) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if ((list.getStyle() & SWT.MULTI) != 0) {
					list.select(indices);
					WidgetHandler.getInstance().notify(SWT.Selection, list);
				} else {
					throw new SWTLayerException(
							"List does not support multi selection - it does not have SWT MULTI style");
				}
			}
		});
	}

	/**
	 * Selects item on position specified by index in specified {@link List}.
	 * 
	 * @param list list to handle
	 * @param index index of item to select
	 */
	public void select(final List list, final int index) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (list.getItemCount() - 1 < index) {
					throw new SWTLayerException(
							"Unable to select item with index " + index
									+ " because it does not exist");
				}
				list.select(index);
				WidgetHandler.getInstance().notify(SWT.Selection, list);
			}
		});
	}
}
