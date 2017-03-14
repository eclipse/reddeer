/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.reddeer.core.test.condition;

import static org.junit.Assert.*;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Jan Novak <jnovak@redhat.com>
 */
@RunWith(RedDeerSuite.class)
public class ShellWithTextIsActiveTest extends ShellTestBase {

	@Test
	public void testFocusedShellIsActive() {
		for (DefaultShell testedShell : getTestShells()) {
			ShellWithTextIsActive isActive = new ShellWithTextIsActive(testedShell.getText());

			for (DefaultShell shell : getTestShells()) {
				shell.setFocus();
				assertEquals(testedShell.getText().equals(shell.getText()), isActive.test());
			}
		}
	}

	@Test
	public void testClosedShell() {
		for (DefaultShell shell : getTestShells()) {
			ShellWithTextIsActive isActive = new ShellWithTextIsActive(shell.getText());
			shell.setFocus();
			
			assertTrue(isActive.test());
			shell.close();
			assertFalse(isActive.test());
		}
	}

}
