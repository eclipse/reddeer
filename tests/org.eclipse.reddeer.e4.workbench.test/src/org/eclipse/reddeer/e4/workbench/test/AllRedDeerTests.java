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
