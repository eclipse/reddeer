package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;

/**
 * Condition is fulfilled when table contains specified item
 * 
 * @author Rastislav Wagner
 */
public class TableContainsItem implements WaitCondition{
	
	private Table table;
	private String item;
	private int cellIndex;
	
	/**
	 * 
	 * @param table look for item in specified table
	 * @param item text of item
	 * @param cellIndex index of cell which should contain item text
	 */
	public TableContainsItem(Table table, String item, int cellIndex){
		this.table=table;
		this.cellIndex=cellIndex;
		this.item=item;
	}

	@Override
	public boolean test() {
		for(TableItem i: table.getItems()){
			if(i.getText(cellIndex).equals(item)){
				return true;
			}
		}
		return false;
	}

	@Override
	public String description() {
		return "table contains item with text "+item+" in cell "+cellIndex;
	}

}
