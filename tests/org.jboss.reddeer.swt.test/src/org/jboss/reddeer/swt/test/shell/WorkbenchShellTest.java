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
package org.jboss.reddeer.swt.test.shell;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.workbench.impl.shell.WorkbenchShell;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class WorkbenchShellTest {
	
	@Test
	public void workbenchShellTest() {
		Shell shell = new WorkbenchShell();
		assertFalse(shell.getText().equals(""));
	}
	
	@Test
	public void maximizeWorkbenshShellTest() {
		WorkbenchShell workbenchShell = new WorkbenchShell();
		workbenchShell.restore();
		assertFalse(workbenchShell.isMaximized());
		workbenchShell.maximize();
		assertTrue(workbenchShell.isMaximized());
		workbenchShell.restore();
		assertFalse(workbenchShell.isMaximized());
	}
}
