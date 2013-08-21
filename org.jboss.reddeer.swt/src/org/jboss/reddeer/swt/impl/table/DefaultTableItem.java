package org.jboss.reddeer.swt.impl.table;

import java.util.List;

import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.condition.TableHasRows;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.wait.WaitUntil;

public class DefaultTableItem extends AbstractTableItem{

	public DefaultTableItem() {
		this(0);
	}
	
	/**
	 * Table item with specified path will be constructed 
	 * 
	 * @param tableItem
	 */
	public DefaultTableItem(int itemIndex) {
		this(0, itemIndex);
	}
	
	/**
	 * Table item with specified path will be constructed 
	 * 
	 * @param tableItem
	 */
	public DefaultTableItem(String tableItem) {
		this(0, tableItem);
	}
	
	/**
	 * Table item with specified path will be constructed 
	 * 
	 * @param tableItem
	 */
	public DefaultTableItem(int tableIndex, int itemIndex) {
		super(findTableItem(tableIndex, itemIndex));
	}
	
	/**
	 * Tree item with specified tree index and path will be constructed
	 * 
	 * @param treeIndex
	 * @param treeItemPath
	 */
	public DefaultTableItem(int tableIndex, String tableItem) {
		super(findTableItem(tableIndex, tableItem));
	}
	
	private static org.eclipse.swt.widgets.TableItem findTableItem(int tableIndex, int itemIndex){
		Table table = new DefaultTable(tableIndex);
	    new WaitUntil(new TableHasRows(table));
	    List<TableItem> items = table.getItems();
	    if(items.size() < itemIndex){
	    	return items.get(itemIndex).getSWTWidget();
	    }
	    throw new SWTLayerException("Table with index " +tableIndex+ " does not contain table item with index "+itemIndex);
	}
	
	private static org.eclipse.swt.widgets.TableItem findTableItem(int tableIndex, String tableItem){
	    Table table = new DefaultTable(tableIndex);
	    new WaitUntil(new TableHasRows(table));
	    List<TableItem> items = table.getItems();
	    for(TableItem i: items){
	    	if(i.getText().equals(tableItem)){
	    		return i.getSWTWidget();
	    	}
	    }
	    throw new SWTLayerException("Table with index " +tableIndex+ " does not contain table item "+tableItem);
	}
	
	

}
