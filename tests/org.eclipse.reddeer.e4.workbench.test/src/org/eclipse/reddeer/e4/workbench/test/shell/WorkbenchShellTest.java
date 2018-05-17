/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.e4.workbench.test.shell;

import static org.junit.Assert.*;

import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.reddeer.swt.impl.menu.ShellMenu;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.junit.Test;

public class WorkbenchShellTest {
	
	
	@Test
	public void findWorkbenchShell() {
		WorkbenchShell workbenchShell = new WorkbenchShell();
		assertEquals("Eclipse 4 RCP Application",workbenchShell.getText());
	}
	
	@Test
	public void findWorkbenchShellMenu() {
		WorkbenchShell workbenchShell = new WorkbenchShell();
		Menu menu = new ShellMenu(workbenchShell);
		menu.getItem("File");
		menu.getItem("Help");
		menu.getItem("File","Open");
		menu.getItem("Help","About");
		
		new ShellMenuItem(workbenchShell,"File","Open");
		
		new ShellMenuItem("File","Open");
	}

}
