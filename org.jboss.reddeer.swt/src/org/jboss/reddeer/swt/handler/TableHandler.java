package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

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
		final TableItem[] tableItems = (TableItem[]) WidgetHandler.getInstance().getSWTItems(table);
		
		
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

}
