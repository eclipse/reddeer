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
package org.eclipse.reddeer.swt.test.utils;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Shell;

public class ShellTestUtils {

	public static Shell createShell(String title) {
		Shell shell = new Shell(org.eclipse.swt.widgets.Display.getDefault());
		shell.setText(title);
		shell.setLayout(new RowLayout(SWT.VERTICAL));
		shell.setSize(500, 500);
		shell.open();
		shell.setFocus();
		return shell;
	}

	public static void closeShell(String title) {
		ShellIsAvailable shellIsAvailable = new ShellIsAvailable(title);
		new WaitUntil(shellIsAvailable, TimePeriod.DEFAULT);
		Shell shell = shellIsAvailable.getResult();
		if (shell != null) {
			new DefaultShell(shell).close();
		}
	}
}
