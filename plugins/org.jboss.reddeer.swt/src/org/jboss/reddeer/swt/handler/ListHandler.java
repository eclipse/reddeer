package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods that handle UI operations on {@link List} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ListHandler {

	private static ListHandler instance;

	private ListHandler() {

	}

	/**
	 * Creates and returns instance of ListHandler class
	 * 
	 * @return
	 */
	public static ListHandler getInstance() {
		if (instance == null) {
			instance = new ListHandler();
		}
		return instance;
	}

	/**
	 * Gets items
	 * 
	 * @param list
	 *            given widget
	 * @return array of items in widget
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
	 * Deselects all items
	 * 
	 * @param list given widget
	 */
	public void deselectAll(final List list) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				list.deselectAll();
			}
		});
	}

	public void selectAll(final List list) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if((list.getStyle() & SWT.MULTI) !=0){
					list.selectAll();
					WidgetLookup.getInstance().notify(SWT.Selection, list);
				} else {
					throw new SWTLayerException("List does not support multi selection - it does not have SWT MULTI style");
				}
			}
		});
	}

	/**
	 * Selects item for supported widget type
	 * 
	 * @param w given widget
	 * @param item to select
	 */
	public void select(final List list,final String item) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				int index = (list.indexOf(item))	;
				if(index == -1){
					throw new SWTLayerException("Unable to select item "+item+" because it does not exist");
				}
				list.select(list.indexOf(item));
				WidgetLookup.getInstance().sendClickNotifications(list);
			}
		});
	}

	/**
	 * Selects items for supported widget type if widget supports multi selection
	 * 
	 * @param w given widget
	 * @param items to select
	 */
	public void select(final List list,final String[] items) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if((list.getStyle() & SWT.MULTI) !=0){
					for(String item:items){
						int index = (list.indexOf(item))	;
						if(index == -1){
							throw new SWTLayerException("Unable to select item "+item+" because it does not exist");
						}
						list.select(list.indexOf(item));
						WidgetLookup.getInstance().notify(SWT.Selection, list);
					}
				} else {
					throw new SWTLayerException("List does not support multi selection - it does not have SWT MULTI style");
				}
			}
		});
	}

	/**
	 * Selects items for supported widget type if widget supports multi selection
	 * 
	 * @param w given widget
	 * @param indices of items to select
	 */
	public void select(final List list,final int[] indices) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if((list.getStyle() & SWT.MULTI) !=0){
					list.select(indices);
					WidgetLookup.getInstance().notify(SWT.Selection, list);
				} else {
					throw new SWTLayerException("List does not support multi selection - it does not have SWT MULTI style");
				}
			}
		});
	}

	/**
	 * Selects item for supported widget type
	 * 
	 * @param w given widget
	 * @param index of item to select
	 */
	public void select(final List list,final int index) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if(list.getItemCount()-1 < index){
					throw new SWTLayerException("Unable to select item with index "+index+" because it does not exist");
				}
				list.select(index);
				WidgetLookup.getInstance().notify(SWT.Selection, list);
			}
		});
	}
}
