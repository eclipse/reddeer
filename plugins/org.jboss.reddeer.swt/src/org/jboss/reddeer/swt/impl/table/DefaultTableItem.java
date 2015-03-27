package org.jboss.reddeer.swt.impl.table;

import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.condition.TableHasRows;
import org.jboss.reddeer.core.exception.SWTLayerException;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.common.wait.WaitUntil;

public class DefaultTableItem extends AbstractTableItem{
	
	/**
	 * Default constructor
	 */
	public DefaultTableItem() {
		this((ReferencedComposite) null);
	}
	
	/**
	 * TableItem inside given composite
	 * @param referencedComposite
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Table item with specified path will be constructed that matches given matchers
	 * 
	 * @param tableItem
	 * @param matchers
	 */
	public DefaultTableItem(int itemIndex, Matcher<?>... matchers) {
		this(null, itemIndex);
	}
	
	/**
	 * Table item with specified path inside given composite will be constructed that matches given matchers
	 * @param referencedComposite
	 * @param tableItem
	 * @param matchers
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite, int itemIndex, Matcher<?>... matchers) {
		super(referencedComposite, itemIndex, matchers);
	}
	
	/**
	 * Table item with specified path will be constructed 
	 * 
	 * @param tableItem
	 */
	public DefaultTableItem(String tableItem) {
		this(null, tableItem);
	}
	
	/**
	 * Table item with specified path inside given compoiste will be constructed 
	 * @param referencedComposite
	 * @param tableItem
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite, String tableItem) {
		this(referencedComposite, 0, new WithTextMatcher(tableItem));
	}
	
	/**
	 * TableItem that matches given matchers
	 * @param matchers
	 */
	public DefaultTableItem(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * TableItem that matches given matchers
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Table item with specified path will be constructed 
	 * @deprecated This is not a standard widget constructor. It will be removed in 1.0.0
	 * @param tableItem
	 */
	public DefaultTableItem(int tableIndex, int itemIndex) {
		super(findTableItem(null, tableIndex, itemIndex));
	}
	
	/**
	 * Table item with specified path inside given composite will be constructed 
	 * @deprecated This is not a standard widget constructor. It will be removed in 1.0.0
	 * @param referencedComposite
	 * @param tableIndex
	 * @param itemIndex
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite, int tableIndex, int itemIndex) {
		super(findTableItem(referencedComposite, tableIndex, itemIndex));
	}
	
	/**
	 * Tree item with specified tree index and path will be constructed
	 * @deprecated This is not a standard widget constructor. It will be removed in 1.0.0
	 * @param treeIndex
	 * @param treeItemPath
	 */
	public DefaultTableItem(int tableIndex, String tableItem) {
		super(findTableItem(null, tableIndex, tableItem));
	}
	
	/**
	 * Tree item with specified tree index and path inside given compoistewill be constructed
	 * @deprecated This is not a standard widget constructor. It will be removed in 1.0.0
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
	    if(itemIndex < items.size()){
	    	return (org.eclipse.swt.widgets.TableItem)items.get(itemIndex).getSWTWidget();
	    }
	    throw new SWTLayerException("Table with index " +tableIndex+ " does not contain table item with index "+itemIndex);
	}
	
	private static org.eclipse.swt.widgets.TableItem findTableItem(ReferencedComposite referencedComposite, int tableIndex, String tableItem){
	    Table table = new DefaultTable(referencedComposite, tableIndex);
	    new WaitUntil(new TableHasRows(table));
	    List<TableItem> items = table.getItems();
	    for(TableItem i: items){
	    	if(i.getText().equals(tableItem)){
	    		return (org.eclipse.swt.widgets.TableItem)i.getSWTWidget();
	    	}
	    }
	    throw new SWTLayerException("Table with index " +tableIndex+ " does not contain table item "+tableItem);
	}
	
	

}
