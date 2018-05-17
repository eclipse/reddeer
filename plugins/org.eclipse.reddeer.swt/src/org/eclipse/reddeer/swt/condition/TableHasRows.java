/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.condition;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.swt.api.Table;
import org.eclipse.reddeer.swt.api.TableItem;

/**
 * Condition is met when table has rows.
 * 
 * @author Rastislav Wagner, Josef Kopriva
 */
public class TableHasRows extends AbstractWaitCondition {
	private final Table table;
	private final Matcher<?> matcher;
	private List<TableItem> resultRows;

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
		this.resultRows = new ArrayList<>();
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
			this.resultRows = search();
			return !this.resultRows.isEmpty();
		}
	}

	@Override
	public String description() {
		return "table contains TableItems";
	}

	@SuppressWarnings("unchecked")
	@Override 
	public List<TableItem> getResult() {
		return this.resultRows.isEmpty() ? null : this.resultRows;
	}
	
}