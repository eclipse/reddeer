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
package org.eclipse.reddeer.core.test.condition.shells;

import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.core.util.InstanceValidator;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.test.impl.table.AbstractTableTest;

import java.util.List;

/**
*
* @author Jan Novak <jnovak@redhat.com>
*/
public class TableShell extends AbstractTableTest {


	private static DefaultTable table;

	private static final int INDEX_COLUMN = 6;

	public TableShell() {
		super();
		init();
	}

	public DefaultTable getTable() {
		return table;
	}

	private void init() {
		this.setUp();
		table = new DefaultTable(2);
		InstanceValidator.checkNotNull(table, "table");
	}

	public void close() {
		if(table != null) {
			this.cleanup();
			table = null;
		}
	}

	public List<TableItem> getFirstTwentyItems() {
		int limit = 20;
		return table.getItems().size() > limit ?
				table.getItems().subList(0, limit) :
				table.getItems();
	}

	public static String getIndexCell(org.eclipse.swt.widgets.TableItem tableItem){
		return Display.syncExec(() -> tableItem.getText(INDEX_COLUMN));
	}

}