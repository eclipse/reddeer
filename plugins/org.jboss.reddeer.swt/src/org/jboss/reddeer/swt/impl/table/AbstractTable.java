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
package org.jboss.reddeer.swt.impl.table;

import java.util.ArrayList;
import java.util.LinkedList;
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
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.swt.widgets.AbstractControl;

/**
 * Basic abstract class implementation for a table
 * @author Jiri Peterka
 * @author Rastislav Wagner
 *
 */
public abstract class AbstractTable extends AbstractControl<org.eclipse.swt.widgets.Table> implements Table {
	
	private static final Logger log = Logger.getLogger(AbstractTable.class);
	
	protected AbstractTable(org.eclipse.swt.widgets.Table swtWidget) {
		super(swtWidget);
	}
	
	protected AbstractTable(ReferencedComposite refComposite, int index, Matcher<?>... matchers){
		super(org.eclipse.swt.widgets.Table.class, refComposite, index, matchers);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#containsItem(java.lang.String)
	 */
	@Override
	public boolean containsItem(String item){
		for(TableItem it: getItems()){
			if(it.getText().equals(item)){
				return true;
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#containsItem(java.lang.String, int)
	 */
	@Override
	public boolean containsItem(String item, int cellIndex){
		for(TableItem it: getItems()){
			if(it.getText(cellIndex).equals(item)){
				return true;
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#getItems()
	 */
	@Override
	public List<TableItem> getItems(){
		waitUntilTableHasRows();
		org.eclipse.swt.widgets.TableItem[] items = TableHandler.getInstance().getSWTItems(swtWidget);
		List<TableItem> tableItems = new ArrayList<TableItem>();
		for(org.eclipse.swt.widgets.TableItem i: items){
			tableItems.add(new DefaultTableItem(i));
		}
		return tableItems;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#getItems(org.hamcrest.Matcher)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TableItem> getItems(Matcher<TableItem>... matchers) {
		List<TableItem> matchedItems = new ArrayList<TableItem>();
		
		for (TableItem item : getItems()){
			int index = 0;
			while (index < matchers.length && matchers[index].matches(item)){
				index++;
			}
			if (index == matchers.length){
				matchedItems.add(item);
			}
		}
		return matchedItems;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#getItem(int)
	 */
	@Override
	public TableItem getItem(final int index) {
		waitUntilTableHasRows();
		org.eclipse.swt.widgets.TableItem tItem = TableHandler.getInstance().getSWTItem(swtWidget, index);
		return new DefaultTableItem(tItem);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#getItem(java.lang.String)
	 */
	@Override
	public TableItem getItem(final String itemText) {
		waitUntilTableHasRows();
		int row = TableHandler.getInstance().indexOf(swtWidget, itemText, 0);
		org.eclipse.swt.widgets.TableItem tItem = TableHandler.getInstance().getSWTItem(swtWidget, row);
		return new DefaultTableItem(tItem);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#getItem(java.lang.String, int)
	 */
	@Override
	public TableItem getItem(final String itemText, int column) {
		waitUntilTableHasRows();
		int row = TableHandler.getInstance().indexOf(swtWidget, itemText, column);
		org.eclipse.swt.widgets.TableItem tItem = TableHandler.getInstance().getSWTItem(swtWidget, row);
		return new DefaultTableItem(tItem);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#rowCount()
	 */
	@Override
	public int rowCount() {
		return TableHandler.getInstance().rowCount(swtWidget);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#select(int[])
	 */
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
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#select(java.lang.String[])
	 */
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
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#selectAll()
	 */
	@Override
	public void selectAll(){
		log.info("Select all table rows");
		waitUntilTableHasRows();
		TableHandler.getInstance().selectAll(swtWidget);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#deselectAll()
	 */
	@Override
	public void deselectAll() {
		log.info("Deselect all table rows");
		waitUntilTableHasRows();
		TableHandler.getInstance().deselectAll(swtWidget);
	}

	private void waitUntilTableHasRows() {
		new WaitUntil(new TableHasRows(this), TimePeriod.NORMAL, false);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#indexOf(org.jboss.reddeer.swt.api.TableItem)
	 */
	@Override
	public int indexOf(TableItem tableItem) {
		return TableHandler.getInstance().indexOf(swtWidget, tableItem.getSWTWidget());
	}
	
	/**
	 * Gets the control.
	 *
	 * @return the control
	 */
	@Override
	public Control getControl() {
		return swtWidget;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#getHeaders()
	 */
	@Override
	public List<String> getHeaders() {
		return TableHandler.getInstance().getHeaders(swtWidget);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#getHeaderIndex(java.lang.String)
	 */
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
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#getHeader(int)
	 */
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
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Table#getSelectetItems()
	 */
	@Override
	public List<TableItem> getSelectetItems(){
		LinkedList<TableItem> result = new LinkedList<TableItem>();
		for (TableItem tableItem : getItems()){
			if (tableItem.isSelected()){
				result.addLast(tableItem);
			}
		}
		return result;
	}
}
