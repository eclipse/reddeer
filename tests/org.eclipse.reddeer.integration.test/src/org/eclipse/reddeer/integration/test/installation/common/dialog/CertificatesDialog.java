/*******************************************************************************
 * Copyright (c) 2020 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/

package org.eclipse.reddeer.integration.test.installation.common.dialog;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.swt.api.Table;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;

public class CertificatesDialog extends DefaultShell {

	public static final String HEADER = "Certificates";
	
	public CertificatesDialog () {
		super(HEADER);
	}
	
	public void ok() {
		new OkButton(this).click();
		new WaitWhile(new ShellIsAvailable(this));
	}
	
	public void acceptAll() {
		Table table = new DefaultTable(this);
		for (TableItem item : table.getItems()) {
			item.setChecked(true);
		}

		try {
			new OkButton(this).click();
		} catch (CoreLayerException e) {
			new PushButton(this, new WithTextMatcher(new RegexMatcher(".*Accept.*selected.*")))
					.click();
		}

		new WaitWhile(new ShellIsAvailable(this));
	}
	
	public static boolean isAvailable() {
		return (new ShellIsAvailable(HEADER)).test();
	}	
}
