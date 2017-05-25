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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.exception.CoreLayerException;

/**
 * Contains methods for handling UI operations on {@link TableItem} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class TableItemHandler extends ItemHandler{
	
	private static TableItemHandler instance;
	
	/**
	 * Gets instance of TableItemHandler.
	 * 
	 * @return instance of TableItemHandler
	 */
	public static TableItemHandler getInstance(){
		if(instance == null){
			instance = new TableItemHandler();
		}
		return instance;
	}
	
	/**
	 * Checks whether specified {@link TableItem} is selected or not.
	 * 
	 * @param tableItem table item to handle
	 * @return true if specified table item is selected, false otherwise
	 */
	public boolean isSelected(final TableItem tableItem) {
		boolean selectionState = Display
				.syncExec(new ResultRunnable<Boolean>() {

					@Override
					public Boolean run() {
						for (TableItem i : tableItem.getParent().getSelection()) {
							if (i.equals(tableItem)) {
								return true;
							}
						}
						return false;
					}
				});
		return selectionState;
	}

	/**
	 * Gets text of specified {@link TableItem} laying on specified cell index.
	 * 
	 * @param tableItem table item to handle
	 * @param cellIndex index of cell
	 * @return text of specified table item
	 */
	public String getText(final TableItem tableItem, final int cellIndex) {
		String text = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return tableItem.getText(cellIndex);
			}
		});
		return text;
	}

	/**
	 * Selects specified {@link TableItem}.
	 * 
	 * @param swtTableItem table item to select
	 * 
	 */
	public void select(final TableItem swtTableItem) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				swtTableItem.getParent().setFocus();
				swtTableItem.getParent().setSelection(swtTableItem);
				notifyItem(SWT.Selection, SWT.NONE,	swtTableItem.getParent(), swtTableItem);
			}
		});
	}

	/**
	 * Sets check to specified {@link TableItem}. 
	 * 
	 * @param swtTableItem table item to handle
	 * @param check whether to check or not specified table item
	 */
	public void setChecked(final TableItem swtTableItem, final boolean check) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if ((swtTableItem.getParent().getStyle() & SWT.CHECK) != SWT.CHECK) {
					throw new CoreLayerException("Unable to check table item "
							+ swtTableItem.getText()
							+ " because table does not have SWT.CHECK style");
				}
			}
		});

		final TableCheckListener listener = new TableCheckListener();
		
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				swtTableItem.getParent().addListener(SWT.Selection, listener);
			}
		});
		
		try{
			Display.syncExec(new Runnable() {
				@Override
				public void run() {
					swtTableItem.setChecked(check);
					swtTableItem.getParent().update();
				}
			});
			
			new WaitUntil(new TableHeardCheckNotification(swtTableItem, listener), TimePeriod.SHORT);
			
		} catch (RedDeerException ex){
			throw ex;
		} finally {
			Display.syncExec(new Runnable() {
				@Override
				public void run() {
					swtTableItem.getParent().removeListener(SWT.Selection, listener);
				}
			});
		}

		

	}

	/**
	 * Finds out whether specified {@link TableItem} is checked or not.
	 * 
	 * @param swtTableItem table item to handle
	 * @return true if table item is checked, false otherwise
	 */
	public boolean isChecked(final TableItem swtTableItem) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return swtTableItem.getChecked();
			}
		});
	}
	
	/**
	 * Gets image of specified {@link TableItem} on the position specified by index.
	 *  
	 * @param tableItem table item to handle
	 * @param imageIndex index of image to get
	 * @return image on position specified by index.
	 */
	public Image getImage(final TableItem tableItem, final int imageIndex) {
		return Display.syncExec(new ResultRunnable<Image>() {

			@Override
			public Image run() {
				return tableItem.getImage(imageIndex);
			}
		});
	}

	/**
	 * Gets parent {@link Table} of specified {@link TableItem}.
	 * 
	 * @param table table item to handle
	 * @return parent table of specified table item
	 */
	public Table getParent(final TableItem table) {
		return Display.syncExec(new ResultRunnable<Table>() {
			@Override
			public Table run() {
				return table.getParent();
			}
		});
	}
	
	/**
	 * Focuses specified {@link TableItem}.
	 *
	 * @param tableItem the new focus
	 */
	public void setFocus(final TableItem tableItem) {
		ControlHandler.getInstance().setFocus(getParent(tableItem));
	}
	
	/**
	 * Finds out whether specified {@link TableItem} is grayed or not.
	 * 
	 * @param tableItem table item to handle
	 * @return true if specified table item is grayed, false otherwise
	 */
	public boolean isGrayed(final TableItem tableItem) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return tableItem.getGrayed();
			}
		});
	}
	
	/**
	 * Set default selection on specified table item.
	 * 
	 * @param tableItem table item to handle
	 */
	public void setDefaultSelection(final TableItem tableItem){
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				select(tableItem);
				notifyItem(SWT.DefaultSelection, SWT.NONE, tableItem.getParent(), tableItem);

			}
		});
	}
	
	/**
	 * Click on specified column in specified table item.
	 * 
	 * @param tableItem table item to handle
	 * @param column column to click on
	 */
	public void click(final TableItem tableItem, final int column) {
		select(tableItem);
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Rectangle rectangle = tableItem.getBounds(column);
				int x = rectangle.x + (rectangle.width / 2);
				int y = rectangle.y + (rectangle.height / 2);
				notifyItemMouse(SWT.MouseDown, SWT.NONE, tableItem.getParent(),	tableItem, x, y, 1);
				notifyItemMouse(SWT.MouseUp, SWT.NONE, tableItem.getParent(), tableItem, x, y, 1);

			}
		});
	}
	
	/**
	 * Clicks twice on specified column in specified table item.
	 * 
	 * @param tableItem table item to handle
	 * @param column column to click on
	 */
	public void doubleClick(final TableItem tableItem, final int column) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				select(tableItem);
				Rectangle rectangle = tableItem.getBounds(column);
				int x = rectangle.x + (rectangle.width / 2);
				int y = rectangle.y + (rectangle.height / 2);
				notifyItemMouse(SWT.MouseDoubleClick, SWT.NONE, tableItem.getParent(),
						tableItem, x, y, 1);

			}
		});
	}
	
	
	private class TableCheckListener implements Listener {

		private boolean heard = false;

		@Override
		public void handleEvent(Event arg0) {
			heard = true;
		}

		public boolean isHeard() {
			return heard;
		}

	}
	
	
	private class TableHeardCheckNotification extends AbstractWaitCondition {

		private org.eclipse.swt.widgets.TableItem tableItem;
		private TableCheckListener listener;

		public TableHeardCheckNotification( org.eclipse.swt.widgets.TableItem tableItem,
				TableCheckListener listener) {
			this.tableItem = tableItem;
			this.listener = listener;
		}

		@Override
		public boolean test() {
			notifyItem(SWT.Selection, SWT.CHECK, getParent(tableItem), tableItem);
			return listener.isHeard();
		}

		@Override
		public String description() {
			return "table heard check notification";
		}

	}
}
