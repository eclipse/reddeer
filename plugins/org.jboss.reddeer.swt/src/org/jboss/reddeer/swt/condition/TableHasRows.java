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
package org.jboss.reddeer.swt.condition;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;

/**
 * Condition is met when table has rows.
 * 
 * @author Rastislav Wagner, Josef Kopriva
 */
public class TableHasRows extends AbstractWaitCondition {
	private final Table table;
	private final Matcher<?> matcher;

	/**
	 * Constructs TableHasRows wait condition. Condition is met when table has
	 * at least one row.
	 * 
	 * @param table
	 *            table which should contain rows to the condition be met
	 */
	public TableHasRows(Table table) {
		this(table, null);
	}

	/**
	 * Constructs TableHasRow wait condition. Condition is met when table has
	 * at least one TableItem matched with matcher.
	 * 
	 * @param table
	 *            table which should contain row to the condition be met
	 * @param matcher
	 *            matcher to match table - e.g. Regexmatcher, custom matcher...
	 */
	public TableHasRows(Table table, Matcher<?> matcher) {
		this.table = table;
		this.matcher = matcher != null ? matcher : null;
	}

	/**
	 * Search TableItems in table which are matched with matcher.
	 * 
	 * @return matchedItems list with matching TableItems
	 */
	private List<TableItem> search() {
		List<TableItem> matchedItems = new ArrayList<TableItem>();

		for (TableItem item : table.getItems()) {
			if (this.matcher.matches(item)) {
				matchedItems.add(item);
			}
		}
		return matchedItems;
	}

	@Override
	public boolean test() {
		if (this.table != null && this.matcher == null) {
			return table.rowCount() > 0;
		} else {
			return !search().isEmpty();
		}
	}

	@Override
	public String description() {
		return "table contains TableItems";
	}
}