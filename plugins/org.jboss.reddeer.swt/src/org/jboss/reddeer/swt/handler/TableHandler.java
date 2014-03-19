package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * 
 * Contains methods that handle UI operations on {@link TableHandler} widgets. 
 */
public class TableHandler {

	private static TableHandler instance;

	private TableHandler() {

	}

	/**
	 * Creates and returns instance of TableHandler class
	 * 
	 * @return
	 */
	public static TableHandler getInstance() {
		if (instance == null) {
			instance = new TableHandler();
		}
		return instance;
	}

	public int rowCount(final Table table) {
		return Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				return table.getItemCount();
			}
		});
	}

	public boolean isGrayed(final TableItem tableItem) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return tableItem.getGrayed();
			}
		});
	}

	public Image getItemImage(final TableItem tableItem, final int imageIndex) {
		return Display.syncExec(new ResultRunnable<Image>() {

			@Override
			public Image run() {
				return tableItem.getImage(imageIndex);
			}
		});
	}

	public int indexOf(final Table table, final String item, final int columnIndex){
		final TableItem[] tableItems = getSWTItems(table);


		for(int i=0;i<tableItems.length;i++){
			final int y = i;
			String itemText = Display.syncExec(new ResultRunnable<String>() {

				@Override
				public String run() {
					return  tableItems[y].getText(columnIndex);
				}
			});

			if(itemText.equals(item)){
				return y;
			}
		}
		throw new SWTLayerException("Item "+item+" does not exist in table");
	}

	public void doubleClick(final TableItem tableItem, final int column){
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				TableItemHandler.getInstance().select(tableItem);
				Rectangle rectangle = tableItem.getBounds(column);
				int x = rectangle.x + (rectangle.width/2);
				int y = rectangle.y + (rectangle.height/2);
				WidgetHandler.getInstance().notifyItemMouse(SWT.MouseDoubleClick,SWT.NONE , tableItem.getParent(), tableItem, x, y, 1);

			}
		});
	}

	/**
	 * Gets swt items
	 * 
	 * @param table
	 *            given widget
	 * @return array of items in widget
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
	 * Gets swt items wit specified index
	 * 
	 * @param table
	 *            given widget
	 * @return array of items in widget
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
	 * Deselects all items 
	 * 
	 * @param w given widget
	 */
	public void deselectAll(final Table w) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				w.deselectAll();
			}
		});
	}

	public void selectAll(final Table table) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if((table.getStyle() & SWT.MULTI) !=0){
					table.selectAll();
					WidgetHandler.getInstance().notify(SWT.Selection, table);
				} else {
					throw new SWTLayerException("Table does not support multi selection - it does not have SWT MULTI style");
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
	public void select(final Table table,final int[] indices) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if((table.getStyle() & SWT.MULTI) !=0){
					table.select(indices);
					WidgetHandler.getInstance().notify(SWT.Selection, table);
				} else {
					throw new SWTLayerException("Table does not support multi selection - it does not have SWT MULTI style");
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
	public void select(final Table table,final int index) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if(table.getItemCount()-1 < index){
					throw new SWTLayerException("Unable to select item with index "+index+" because it does not exist");
				}
				table.select(index);
				WidgetHandler.getInstance().notify(SWT.Selection, table);
			}
		});
	}
}
