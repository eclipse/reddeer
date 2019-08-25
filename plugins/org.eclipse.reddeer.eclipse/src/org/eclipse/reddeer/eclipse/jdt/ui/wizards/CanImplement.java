/*******************************************************************************
 * Copyright (c) 2019 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.jdt.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.condition.TableHasRows;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.text.DefaultText;

/**
 * Interface that brings default behavior for Class/Interface/Enum wizards that can 'implement'.
 * @author odockal
 *
 */
public interface CanImplement {

	/**
	 * Add extended interface.
	 * 
	 * @param interfaceName String with name of interface to add
	 */
	public default void addExtendedInterface(String interfaceName) {
		new PushButton("Add...").click();
		new DefaultShell(new WithTextMatcher(new RegexMatcher("[Extended|Implemented].*Interfaces Selection")));
		new DefaultText(0).setText(interfaceName);
		new WaitUntil(new TableHasRows(new DefaultTable(0)), TimePeriod.DEFAULT, false);

		switch (new DefaultTable(0).getItems().size()) {
		case 0:
			throw new RedDeerException("No item was found for given interface name '" + interfaceName + "'.");
		case 1:
			new PushButton("OK").click();
			break;
		default:
			throw new RedDeerException("More than 1 item was found for given interface name '" + interfaceName + "'.");
		}
	}
	
	/**
	 * Remove extended interface.
	 * 
	 * @param interfaceName String with name of interface to remove
	 */
	public default void removeExtendedInterface(String interfaceName) {
		DefaultTable table = new DefaultTable(0);
		table.getItem(interfaceName).select();
		new PushButton("Remove").click();
	}

	/**
	 * Returns list of names of extended interfaces.
	 * 
	 * @return List of extended interfaces
	 */
	public default ArrayList<String> getExtendedInterfaces() {
		DefaultTable table = new DefaultTable(0);
		List<TableItem> tableItems = table.getItems();
		ArrayList<String> tableItemNames = new ArrayList<String>();

		for (TableItem item : tableItems) {
			tableItemNames.add(item.getText());
		}

		return tableItemNames;
	}
	
}
