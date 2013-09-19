package org.jboss.reddeer.swt.impl.table;

import java.util.List;

import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.condition.TableHasRows;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.wait.WaitUntil;

public class DefaultTableItem extends AbstractTableItem{
	
	/**
	 * Default constructor
	 */
	public DefaultTableItem() {
		this(0);
	}
	
	/**
	 * TableItem inside given composite
	 * @param referencedComposite
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
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
	 * Table item with specified path inside given composite will be constructed 
	 * @param referencedComposite
	 * @param tableItem
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite, int itemIndex) {
		this(referencedComposite, 0, itemIndex);
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
	 * Table item with specified path inside given compoiste will be constructed 
	 * @param referencedComposite
	 * @param tableItem
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite, String tableItem) {
		this(referencedComposite, 0, tableItem);
	}
	
	/**
	 * Table item with specified path will be constructed 
	 * 
	 * @param tableItem
	 */
	public DefaultTableItem(int tableIndex, int itemIndex) {
		super(findTableItem(null, tableIndex, itemIndex));
	}
	
	/**
	 * Table item with specified path inside given composite will be constructed 
	 * @param referencedComposite
	 * @param tableIndex
	 * @param itemIndex
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite, int tableIndex, int itemIndex) {
		super(findTableItem(referencedComposite, tableIndex, itemIndex));
	}
	
	/**
	 * Tree item with specified tree index and path will be constructed
	 * 
	 * @param treeIndex
	 * @param treeItemPath
	 */
	public DefaultTableItem(int tableIndex, String tableItem) {
		super(findTableItem(null, tableIndex, tableItem));
	}
	
	/**
	 * Tree item with specified tree index and path inside given compoistewill be constructed
	 * @param referencedComposite
	 * @param treeIndex
	 * @param treeItemPath
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite, int tableIndex, String tableItem) {
		super(findTableItem(referencedComposite, tableIndex, tableItem));
	}
	
	private static org.eclipse.swt.widgets.TableItem findTableItem(ReferencedComposite referencedComposite, int tableIndex, int itemIndex){
		Table table = new DefaultTable(referencedComposite, tableIndex);
	    new WaitUntil(new TableHasRows(table));
	    List<TableItem> items = table.getItems();
	    if(items.size() < itemIndex){
	    	return items.get(itemIndex).getSWTWidget();
	    }
	    throw new SWTLayerException("Table with index " +tableIndex+ " does not contain table item with index "+itemIndex);
	}
	
	private static org.eclipse.swt.widgets.TableItem findTableItem(ReferencedComposite referencedComposite, int tableIndex, String tableItem){
	    Table table = new DefaultTable(referencedComposite, tableIndex);
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
