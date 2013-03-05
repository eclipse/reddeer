package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.impl.table.DefaultTable;


/**
 * Condition is fulfilled when table has rows
 * @author Rastislav Wagner
 *
 */
public class TableHasRows implements WaitCondition {
	private final DefaultTable table;

	public TableHasRows(DefaultTable table) {
		this.table = table;
	}

	public boolean test() {
		return table.rowCount() > 1;
	}

	@Override
	public String description() {
		return "Table does not contain rows";
	}
}