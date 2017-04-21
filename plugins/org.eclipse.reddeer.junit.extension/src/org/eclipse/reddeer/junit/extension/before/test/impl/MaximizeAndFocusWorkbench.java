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
package org.eclipse.reddeer.junit.extension.before.test.impl;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.reddeer.common.properties.RedDeerProperties;
import org.eclipse.reddeer.core.handler.ShellHandler;
import org.eclipse.reddeer.junit.extension.ExtensionPriority;
import org.eclipse.reddeer.junit.extensionpoint.IBeforeTest;
import org.eclipse.reddeer.workbench.core.lookup.WorkbenchShellLookup;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

/**
 * Ensures that workbench shell is maximized and focused before test execution. Use this system property to
 * enable/disable it:
 * - rd.maximizeWorkbench=[true|false]
 * (default=true)
 * 
 * @author rawagner
 *
 */
public class MaximizeAndFocusWorkbench implements IBeforeTest {
	
	private static boolean hasToRun = RedDeerProperties.MAXIMIZE_WORKBENCH_ENABLED.getBooleanValue();
	
	@Override
	public void runBeforeTestClass(String config, TestClass testClass) {
		Shell workbenchShell = WorkbenchShellLookup.getInstance().getWorkbenchShell();
		ShellHandler.getInstance().maximize(workbenchShell);
		ShellHandler.getInstance().setFocus(workbenchShell);
		//run just once
		hasToRun=false;
		
	}

	@Override
	public void runBeforeTest(String config, Object target, FrameworkMethod method) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasToRun() {
		return hasToRun;
	}

	@Override
	public long getPriority() {
		return ExtensionPriority.MAXIMIZE_WORKBENCH;
	}

}
