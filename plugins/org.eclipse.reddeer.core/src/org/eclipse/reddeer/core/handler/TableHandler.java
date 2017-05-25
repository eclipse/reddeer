/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.core.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.exception.CoreLayerException;

/**
 * Contains methods that handle UI operations on {@link TableHandler} widgets.
 * 
 * @author mlabuda
 */
public class TableHandler extends ControlHandler{
	
	private static TableHandler instance;
	
	/**
	 * Gets instance of TableHandler.
	 * 
	 * @return instance of TableHandler
	 */
	public static TableHandler getInstance(){
		if(instance == null){
			instance = new TableHandler();
		}
		return instance;
	}

	/**
	 * Gets count of rows in specified {@link Table}.
	 * 
	 * @param table table to handle
	 * @return count of rows in specified table
	 */
	public int rowCount(final Table table) {
		return Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				return table.getItemCount();
			}
		});
	}

	/**
	 * Gets the headers.
	 *
	 * @param table the table
	 * @return the headers
	 */
	public List<String> getHeaders(final Table table) {
		return Display.syncExec(new ResultRunnable<List<String>>() {
			
			@Override
			public List<String> run() {
				List<String> result = new ArrayList<String>();
				for (TableColumn column: table.getColumns()) {
					result.add(column.getText());
				}
				return result;
			}
		});
	}

	/**
	 * Finds out index of the table item specified by name and 
	 * column in specified {@link Table}.
	 * 
	 * @param table table to handle
	 * @param item item to select
	 * @param columnIndex index of column where to look up
	 * @return index of specified table item in specified table
	 */
	public int indexOf(final Table table, final String item, final int columnIndex) {
		final TableItem[] tableItems = getSWTItems(table);

		for (int i = 0; i < tableItems.length; i++) {
			final int y = i;
			String itemText = Display.syncExec(new ResultRunnable<String>() {

				@Override
				public String run() {
					return tableItems[y].getText(columnIndex);
				}
			});

			if (itemText.equals(item)) {
				return y;
			}
		}
		throw new CoreLayerException("Item " + item + " does not exist in table");
	}

	/**
	 * Gets index of specified {@link TableItem} in specified {@link Table}.
	 * 
	 * @param table table to handle
	 * @param tableItem table item to get index
	 * @return index index of specified table item in specified table
	 */

	public int indexOf(final Table table, final TableItem tableItem) {
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return table.indexOf(tableItem);
			}
		});
	}

	/**
	 * Gets {@link TableItem}s from specified {@link Table}.
	 * 
	 * @param table table to handle
	 * @return table items from specified table
	 */
	public TableItem[] getSWTItems(final Table table) {
		return Display.syncExec(new ResultRunnable<TableItem[]>() {

			@Override
			public TableItem[] run() {
				return table.getItems();
			}
		});
	}

	/**
	 * Gets {@link TableItem} from the position specified by index from
	 * specified {@link Table}.
	 * 
	 * @param table table to handle
	 * @param index index of item to get
	 * @return item with specified index in the specified table
	 */
	public TableItem getSWTItem(final Table table, final int index) {
		return Display.syncExec(new ResultRunnable<TableItem>() {

			@Override
			public TableItem run() {
				return table.getItem(index);
			}
		});
	}

	/**
	 * Deselects all items from specified {@link Table}. 
	 * 
	 * @param table table to handle
	 */
	public void deselectAll(final Table table) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				table.deselectAll();
			}
		});
	}

	/**
	 * Selects all items from specified {@link Table}.
	 * 
	 * @param table table to handle
	 */
	public void selectAll(final Table table) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if ((table.getStyle() & SWT.MULTI) != 0) {
					table.selectAll();
					notifyWidget(SWT.Selection, table);
				} else {
					throw new CoreLayerException(
							"Table does not support multi selection - it does not have "
							+ "SWT MULTI style");
				}
			}
		});
	}

	/**
	 * Selects table items on the position specified by indices in specified {@link Table}.
	 * 
	 * @param table table to handle
	 * @param indices indices of items to select
	 */
	public void select(final Table table, final int[] indices) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if ((table.getStyle() & SWT.MULTI) != 0) {
					table.select(indices);
					notifyWidget(SWT.Selection, table);
				} else {
					throw new CoreLayerException(
							"Table does not support multi selection - it does not have "
							+ "SWT MULTI style");
				}
			}
		});
	}

	/**
	 * Selects table item on the position specified by the index in specified {@link Table}.
	 * 
	 * @param table table to handle
	 * @param index of item to select
	 */
	public void select(final Table table, final int index) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (table.getItemCount() - 1 < index) {
					throw new CoreLayerException(
							"Unable to select item with index " + index
									+ " because it does not exist");
				}
				table.select(index);
				notifyWidget(SWT.Selection, table);
			}
		});
	}
}
