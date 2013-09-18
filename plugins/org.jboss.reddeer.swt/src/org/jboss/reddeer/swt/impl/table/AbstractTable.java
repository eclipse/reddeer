package org.jboss.reddeer.swt.impl.table;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.condition.TableHasRows;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.TableHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.impl.table.internal.BasicTableItem;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Basic abstract class implementation for a table
 * @author Jiri Peterka
 * @author Rastislav Wagner
 *
 */
public abstract class AbstractTable implements Table {
	protected final Logger log = Logger.getLogger(this.getClass());
	protected org.eclipse.swt.widgets.Table table;
	
	protected AbstractTable (org.eclipse.swt.widgets.Table swtTable){
		  if (swtTable != null){
		    this.table = swtTable;  
		  }
		  else {
		     throw new SWTLayerException("SWT Table passed to constructor is null");
		  }	  
	}
	
	@Override
	public List<TableItem> getItems(){
		waitUntilTableHasRows();
		org.eclipse.swt.widgets.TableItem[] items = (org.eclipse.swt.widgets.TableItem[])WidgetHandler.getInstance().getSWTItems(table);
		List<TableItem> tableItems = new ArrayList<TableItem>();
		for(org.eclipse.swt.widgets.TableItem i: items){
			tableItems.add(new BasicTableItem(i));
		}
		return tableItems;
	}
	
	@Override
	public TableItem getItem(final int row) {
		waitUntilTableHasRows();
		org.eclipse.swt.widgets.TableItem tItem = (org.eclipse.swt.widgets.TableItem)WidgetHandler.getInstance().getSWTItem(table, row);
		return new BasicTableItem(tItem);
	}
	
	@Override
	public TableItem getItem(final String itemText) {
		waitUntilTableHasRows();
		int row = TableHandler.getInstance().indexOf(table, itemText, 0);
		org.eclipse.swt.widgets.TableItem tItem = (org.eclipse.swt.widgets.TableItem)WidgetHandler.getInstance().getSWTItem(table, row);
		return new BasicTableItem(tItem);
	}

	@Override
	public int rowCount() {
		return TableHandler.getInstance().rowCount(table);
	}

	@Override
	public void select(final int... indexes) {
		if (log.isDebugEnabled()){
		      StringBuffer sbIndexes = new StringBuffer();
		      for (int index : indexes){
		        if (sbIndexes.length() > 0) {
		          sbIndexes.append(",");
		        }
		        sbIndexes.append(index);
		      }
		  log.debug("Select table row(s): " + sbIndexes.toString());
		}
		waitUntilTableHasRows();
		if(indexes.length == 1){
			WidgetHandler.getInstance().select(table, indexes[0]);
		} else {
			WidgetHandler.getInstance().select(table, indexes);
		}
		
	}
	
	@Override
	public void select(String... items) {
		waitUntilTableHasRows();
		int[] indicies = new int[items.length];
		for(int i =0;i<items.length;i++){
			indicies[i] = TableHandler.getInstance().indexOf(table, items[i], 0);
		}
		select(indicies);
	}
	
	@Override
	public void selectAll(){
		waitUntilTableHasRows();
		WidgetHandler.getInstance().selectAll(table);
	}
	
	private void waitUntilTableHasRows() {
		new WaitUntil(new TableHasRows(this), TimePeriod.NORMAL, false);
	}
	
}
