package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.common.condition.WaitCondition;

/**
 * Condition is met when table contains specified item.
 * 
 * @author Rastislav Wagner
 */
public class TableContainsItem implements WaitCondition {

	private Table table;
	private String item;
	private int cellIndex;

	/**
	 * Constructs TableContainsItem wait condition. Condition is met when the
	 * specified table contains the table item with specified text in specified
	 * cell.
	 * 
	 * @param table table where to look for an item
	 * @param item item to find in the specified table
	 * @param cellIndex index of cell which should contain item with specified text
	 */
	public TableContainsItem(Table table, String item, int cellIndex) {
		this.table = table;
		this.cellIndex = cellIndex;
		this.item = item;
	}

	@Override
	public boolean test() {
		for (TableItem i : table.getItems()) {
			if (i.getText(cellIndex).equals(item)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String description() {
		return "table contains item with text " + item + " in cell "
				+ cellIndex;
	}

}
