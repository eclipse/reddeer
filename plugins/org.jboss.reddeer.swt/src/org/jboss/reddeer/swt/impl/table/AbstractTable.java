package org.jboss.reddeer.swt.impl.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.logging.LoggingUtils;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.condition.TableHasRows;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.core.handler.TableHandler;
import org.jboss.reddeer.swt.impl.table.internal.BasicTableItem;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Basic abstract class implementation for a table
 * @author Jiri Peterka
 * @author Rastislav Wagner
 *
 */
public abstract class AbstractTable extends AbstractWidget<org.eclipse.swt.widgets.Table> implements Table {
	
	private static final Logger log = Logger.getLogger(AbstractTable.class);
	
	protected AbstractTable(org.eclipse.swt.widgets.Table swtWidget) {
		super(swtWidget);
	}
	
	protected AbstractTable(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.Table.class, refComposite, index, matchers);
	}
	
	@Override
	public boolean containsItem(String item){
		for(TableItem it: getItems()){
			if(it.getText().equals(item)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean containsItem(String item, int cellIndex){
		for(TableItem it: getItems()){
			if(it.getText(cellIndex).equals(item)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<TableItem> getItems(){
		waitUntilTableHasRows();
		org.eclipse.swt.widgets.TableItem[] items = TableHandler.getInstance().getSWTItems(swtWidget);
		List<TableItem> tableItems = new ArrayList<TableItem>();
		for(org.eclipse.swt.widgets.TableItem i: items){
			tableItems.add(new BasicTableItem(i));
		}
		return tableItems;
	}
	
	@Override
	public List<TableItem> getItems(Matcher<TableItem> matcher) {
		List<TableItem> matchedItems = new ArrayList<TableItem>();
		
		for (TableItem item : getItems()){
			if (matcher.matches(item)){
				matchedItems.add(item);
			}
		}
		return matchedItems;
	}
	
	@Override
	public TableItem getItem(final int index) {
		waitUntilTableHasRows();
		org.eclipse.swt.widgets.TableItem tItem = TableHandler.getInstance().getSWTItem(swtWidget, index);
		return new BasicTableItem(tItem);
	}
	
	@Override
	public TableItem getItem(final String itemText) {
		waitUntilTableHasRows();
		int row = TableHandler.getInstance().indexOf(swtWidget, itemText, 0);
		org.eclipse.swt.widgets.TableItem tItem = TableHandler.getInstance().getSWTItem(swtWidget, row);
		return new BasicTableItem(tItem);
	}
	
	@Override
	public TableItem getItem(final String itemText, int column) {
		waitUntilTableHasRows();
		int row = TableHandler.getInstance().indexOf(swtWidget, itemText, column);
		org.eclipse.swt.widgets.TableItem tItem = TableHandler.getInstance().getSWTItem(swtWidget, row);
		return new BasicTableItem(tItem);
	}

	@Override
	public int rowCount() {
		return TableHandler.getInstance().rowCount(swtWidget);
	}

	@Override
	public void select(final int... indexes) {
		log.info("Select table rows with indexes (" + LoggingUtils.format(indexes) + ")");
		waitUntilTableHasRows();
		if(indexes.length == 1){
			TableHandler.getInstance().select(swtWidget, indexes[0]);
		} else {
			TableHandler.getInstance().select(swtWidget, indexes);
		}
		
	}
	
	@Override
	public void select(String... items) {
		log.info("Select table rows (" + LoggingUtils.format(items) + ")");
		waitUntilTableHasRows();
		int[] indicies = new int[items.length];
		for(int i =0;i<items.length;i++){
			indicies[i] = TableHandler.getInstance().indexOf(swtWidget, items[i], 0);
		}
		select(indicies);
	}
	
	@Override
	public void selectAll(){
		log.info("Select all table rows");
		waitUntilTableHasRows();
		TableHandler.getInstance().selectAll(swtWidget);
	}

	@Override
	public void deselectAll() {
		log.info("Deselect all table rows");
		waitUntilTableHasRows();
		TableHandler.getInstance().deselectAll(swtWidget);
	}

	private void waitUntilTableHasRows() {
		new WaitUntil(new TableHasRows(this), TimePeriod.NORMAL, false);
	}
	
	@Override
	public int indexOf(TableItem tableItem) {
		return TableHandler.getInstance().indexOf(swtWidget, tableItem.getSWTWidget());
	}
	
	@Override
	public Control getControl() {
		return swtWidget;
	}
	
	@Override
	public List<String> getHeaders() {
		return TableHandler.getInstance().getHeaders(swtWidget);
	}
	
	@Override
	public int getHeaderIndex(String header) {
		List<String> headers = getHeaders();
		for (int i=0; i < headers.size(); i++) {
			if (headers.get(i).equals(header)) {
				return i;
			}
		}
		log.debug("Available headers: " + getHeaders());
		throw new SWTLayerException("There is no header with label " + header +
				" in table.");
	}
	
	@Override
	public String getHeader(int index) {
		List<String> headers = getHeaders();
		if (index < 0) {
			log.debug("Available headers: " + headers);
			throw new SWTLayerException("Cannot get header with negative index.");
		}
		if (headers.size() <= index) {
			log.debug("Available headers: " + headers);
			throw new SWTLayerException("Cannot get header with index " + index + " because table contains only " 
					+ headers.size() + " items(s).");
		}
		return headers.get(index);
	}
}
