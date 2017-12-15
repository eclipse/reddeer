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
package org.eclipse.reddeer.e4.workbench.test;

import org.eclipse.reddeer.e4.workbench.test.part.WorkbenchPartTest;
import org.eclipse.reddeer.e4.workbench.test.shell.WorkbenchShellTest;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(RedDeerSuite.class)
@Suite.SuiteClasses({
	WorkbenchPartTest.class,
	WorkbenchShellTest.class
})
public class AllRedDeerTests {

}
