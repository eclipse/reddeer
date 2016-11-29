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
import static org.junit.Assert.fail;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class DefaultShellTest {
	

	private Shell shell1,shell2;

	@Test
	public void defaultShellTest() {
		Shell shell = new DefaultShell();
		assertFalse(shell.getText().equals(""));
	}

	@Test
	public void closeShellTest() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				org.eclipse.swt.widgets.Display display = Display.getDisplay();
				org.eclipse.swt.widgets.Shell shell = new org.eclipse.swt.widgets.Shell(display);
				shell.setText("Dummy shell");
				shell.open();
				shell.setFocus();
			}
		});

		Shell dummyShell = new DefaultShell("Dummy shell");
		dummyShell.close();
	}

	@Test
	public void activateShellTest() {
		createSimpleShell("Shell 1");
		createSimpleShell("Shell 2");

		shell2 = new DefaultShell("Shell 2");
		new DefaultLabel("Shell 2");

		shell1 = new DefaultShell("Shell 1");
		new DefaultLabel("Shell 1");

		new DefaultShell("Shell 2");
		new DefaultLabel("Shell 2");

		try {
			new DefaultLabel("Shell 1");
			fail("Label 'Shell 1' should be in inactive shell!");
		} catch (CoreLayerException cle) {
			// ok, we expect an exception
		}

	}
	
	@Test
	public void testEmptyTitleShell() {
		createSimpleShell("");
		shell1 = new DefaultShell("");
		assertTrue(shell1.getText().equals(""));
	}

	@Test
	public void testEmptyTitleShellWithCondition() {
		createSimpleShell("");
		shell1 = new DefaultShell("");
		assertTrue(shell1.getText().equals(""));
	}
	
	private static void createSimpleShell(final String title) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				org.eclipse.swt.widgets.Display display = Display.getDisplay();
				org.eclipse.swt.widgets.Shell shell = new org.eclipse.swt.widgets.Shell(display);
				shell.setText(title);
				Label swtLabel = new Label(shell, SWT.BORDER);
				swtLabel.setText(title);
				swtLabel.setSize(100, 30);
				swtLabel.setLocation(100, 50);
				shell.open();
			}
		});
	}
	
	@After
	public void tearDown(){
		if (shell1 != null){
			shell1.close();
		}
		if (shell2 != null){
			shell2.close();
		}
	}
	
	/**
	 * Checks whether the shell with specified name is open.
	 * Closes shell and return false, if is open. 
	 * 
	 * @param name Title of a shell
	 * @return <b>true</b> - the shell is closed
	 *         <b>false</b> - the shell was opened
	 */
	private boolean checkShell(String name) {
		
		try {
			new WaitUntil(new ShellWithTextIsAvailable(name), TimePeriod.NONE);
		} catch (WaitTimeoutExpiredException ex) {
			return true;
		}
		
		new DefaultShell().close();
		return false;
	}
}
