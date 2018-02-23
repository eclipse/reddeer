/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.test.widgets;

import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.swt.test.utils.ShellTestUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;

public class DefaultWidgetAndControlBase {

	protected static final String SHELL_TITLE = "Testing shell";

	protected Menu widget;
	protected Button control;

	@Before
	public void setUp() {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = ShellTestUtils.createShell(SHELL_TITLE);
				createButton(shell);
				createShellMenuBar(shell);
			}
		});
	}

	protected void createButton(Shell shell) {
		Button button = new org.eclipse.swt.widgets.Button(shell, SWT.PUSH);
		button.setText("Push Button");
		this.control = button;
	}

	public void createShellMenuBar(Shell shell) {
		Menu menu = new Menu(shell, SWT.BAR);
		MenuItem item = new MenuItem(menu, SWT.CASCADE);
		item.setText("ShellMenuBarItem");
		shell.setMenuBar(menu);
		this.widget = menu;
	}

	@After
	public void cleanup() {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				ShellTestUtils.closeShell(SHELL_TITLE);
			}
		});
	}

}
