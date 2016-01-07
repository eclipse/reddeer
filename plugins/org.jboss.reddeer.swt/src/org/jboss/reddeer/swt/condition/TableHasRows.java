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

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.swt.api.Table;

/**
 * Condition is met when table has rows.
 * 
 * @author Rastislav Wagner
 */
public class TableHasRows extends AbstractWaitCondition {
	private final Table table;

	/**
	 * Constructs TableHasRows wait condition.
	 * Condition is met when table has any rows.
	 * 
	 * @param table table which should contain rows to the condition be met
	 */
	public TableHasRows(Table table) {
		this.table = table;
	}

	@Override
	public boolean test() {
		return table.rowCount() > 0;
	}

	@Override
	public String description() {
		return "table contains rows";
	}
}