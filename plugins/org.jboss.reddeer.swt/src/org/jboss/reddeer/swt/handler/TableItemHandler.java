package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods that handle UI operations on {@link TableItem} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class TableItemHandler {

	private static TableItemHandler instance;

	private TableItemHandler() {

	}

	/**
	 * Creates and returns instance of ButtonHandler class
	 * 
	 * @return
	 */
	public static TableItemHandler getInstance() {
		if (instance == null) {
			instance = new TableItemHandler();
		}
		return instance;
	}

	/**
	 * Checks if tableitem is selected

	 * @param tableItem	given widget
	 * @return	returns widget label text
	 */
	public boolean isSelected(final TableItem tableItem) {
		boolean selectionState = Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				for(TableItem i: tableItem.getParent().getSelection()){
					if(i.equals(tableItem)){
						return true;
					}
				}
				return false;
			}
		});
		return selectionState;
	}

	/**
	 * Gets text on given cell index 
	 * 
	 * @param tableItem given widget
	 * @Param cellIndex index of cell
	 * @return returns widget text
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
	 * Selects item for supported widget type
	 * 
	 * @param w given widget
	 * @param item to select
	 */
	public void select(final TableItem swtTableItem) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				swtTableItem.getParent().setFocus();
				swtTableItem.getParent().setSelection(swtTableItem);
				WidgetHandler.getInstance().notifyItem(SWT.Selection, SWT.NONE, swtTableItem.getParent(), swtTableItem);
			}
		});
	}

	/**
	 * Checks item for supported widget type
	 * 
	 * @param w given widget
	 * @param item to check
	 */
	public <T> void setChecked(final TableItem swtTableItem, final boolean check) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if((swtTableItem.getParent().getStyle() & SWT.CHECK) != SWT.CHECK){
					throw new SWTLayerException("Unable to check table item "+swtTableItem.getText()+" because table does not have SWT.CHECK style");
				}
				swtTableItem.getParent().setFocus();
				swtTableItem.setChecked(check);
				WidgetHandler.getInstance().notifyItem(SWT.Selection, SWT.CHECK, swtTableItem.getParent(), swtTableItem);
			}
		});
	}

	/**
	 * Checks if widget is checked
	 * 
	 * @param w given widget
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
	 * Returns parent for supported widget
	 * 
	 * @param table	given widget
	 * @return	parent widget
	 */
	public Table getParent(final TableItem table) {
		return Display.syncExec(new ResultRunnable<Table>() {
			@Override
			public Table run() {
				return table.getParent();
			}
		});
	}
}
