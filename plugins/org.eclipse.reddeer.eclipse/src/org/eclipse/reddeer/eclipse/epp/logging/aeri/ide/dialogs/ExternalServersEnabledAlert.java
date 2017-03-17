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
package org.eclipse.reddeer.eclipse.epp.logging.aeri.ide.dialogs;

import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.CancelButton;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;

public class ExternalServersEnabledAlert extends DefaultShell {

	public ExternalServersEnabledAlert() {
		super("Enabling Error Reporting for Non‐Eclipse Projects?");
	}

	public void ok() {
		new OkButton().click();
		new WaitWhile(new ShellIsAvailable(this));
	}

	public void cancel() {
		new CancelButton().click();
		new WaitWhile(new ShellIsAvailable(this));
	}

}
