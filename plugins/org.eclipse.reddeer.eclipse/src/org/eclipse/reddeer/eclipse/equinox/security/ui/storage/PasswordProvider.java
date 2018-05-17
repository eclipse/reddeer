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
package org.eclipse.reddeer.eclipse.equinox.security.ui.storage;

import org.eclipse.reddeer.swt.api.TableItem;

/**
 * PasswordProvider representation of {@link TableItem}.
 * PasswordProvider objects are up to date when {@link TableItem} is modified.
 *
 * @author jnovak
 */
public class PasswordProvider {

	private TableItem tableItem;

	public PasswordProvider(TableItem tableItem) {
		this.tableItem = tableItem;
	}

	public String getDescription() {
		return tableItem.getText(0);
	}

	public int getPriority() {
		return Integer.parseInt(tableItem.getText(1));
	}
	
	public void disable() {
		tableItem.setChecked(false);
	}
	
	public void enable() {
		tableItem.setChecked(true);
	}
	
	public void setEnabled(boolean enabled) {
		tableItem.setChecked(enabled);
	}

}
