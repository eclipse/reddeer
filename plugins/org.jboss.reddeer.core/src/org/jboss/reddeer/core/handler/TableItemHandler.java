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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.exception.CoreLayerException;

/**
 * Contains methods for handling UI operations on {@link TableItem} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class TableItemHandler {

	private static TableItemHandler instance;

	private TableItemHandler() {

	}

	/**
	 * Gets instance of TableItemHandler.
	 * 
	 * @return instance of TableItemHandler
	 */
	public static TableItemHandler getInstance() {
		if (instance == null) {
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
				WidgetHandler.getInstance().notifyItem(SWT.Selection, SWT.NONE,
						swtTableItem.getParent(), swtTableItem);
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
			WidgetHandler.getInstance().notifyItem(SWT.Selection,
					SWT.CHECK, getParent(tableItem), tableItem);
			return listener.isHeard();
		}

		@Override
		public String description() {
			return "table heard check notification";
		}

	}
}
