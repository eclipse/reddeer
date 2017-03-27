/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.swt.api.Table;
import org.eclipse.reddeer.swt.api.TableItem;

/**
 * Condition is met when table contains specified item.
 * 
 * @author Rastislav Wagner
 */
public class TableContainsItem extends AbstractWaitCondition {

	private Table table;
	private String item;
	private int cellIndex;
	private TableItem resultItem;

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
				this.resultItem = i;
				return true;
			}
		}
		return false;
	}

	@Override
	public String description() {
		return "table contains item with text '" + item + "' in cell "
				+ cellIndex;
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public TableItem getResult() {
		return this.resultItem;
	}

}
