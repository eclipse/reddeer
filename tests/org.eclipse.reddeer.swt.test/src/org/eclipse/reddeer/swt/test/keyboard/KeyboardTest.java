/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.test.keyboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.exception.TestFailureException;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.keyboard.Keyboard;
import org.eclipse.reddeer.swt.keyboard.KeyboardFactory;
import org.eclipse.reddeer.swt.test.utils.ShellTestUtils;
import org.junit.After;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class KeyboardTest {
	
	protected static final String SHELL_TITLE = "Keyboard testing shell";
	private Text text;
	
	@After
	public void cleanup() {
		try {
			Display.syncExec(new Runnable() {

				@Override
				public void run() {
					ShellTestUtils.closeShell(SHELL_TITLE);
				}
			});
		} catch (RedDeerException ex) {
			// keyCombinationTest does not open shell with title SHELL_TITLE
		}
	}
	
	@Test
	public void typingWithShiftTest(){
		openTestingShell();
		KeyboardFactory.getKeyboard().type("{@Test}");
		assertEquals("{@Test}", getText());
	}
	
	@Test
	public void typingTest() {
		openTestingShell();
		KeyboardFactory.getKeyboard().type("test123");
		assertEquals("test123", getText());
	}
	
	@Test
	public void keyCombinationTest(){
		new DefaultShell();
		KeyboardFactory.getKeyboard().invokeKeyCombination(SWT.CONTROL, 'h');
		try {
			new DefaultShell("Search").close();
		} catch (CoreLayerException ex) {
			fail("Expected shell 'Search' did not appear, got exception instead: " + ex.getMessage());
		}
	}
	
	@Test(expected=TestFailureException.class)
	public void selectionTest(){
		openTestingShell();
		KeyboardFactory.getKeyboard().type("test");
		KeyboardFactory.getKeyboard().select(2, true);
		KeyboardFactory.getKeyboard().type(SWT.DEL);
		try {
			assertEquals("te", getText());
		} catch (ComparisonFailure ex) {
			throw new TestFailureException(ex.getMessage());
		}
	}
	
	@Test(expected=TestFailureException.class)
	public void copyPasteTest(){
		openTestingShell();
		Keyboard keyboard = KeyboardFactory.getKeyboard();
		keyboard.type("test");
		keyboard.select(2, true);
		keyboard.writeToClipboard(false);
		keyboard.moveCursor(5, true);
		keyboard.pasteFromClipboard();
		try {
			assertEquals("sttest", getText());
		} catch (ComparisonFailure ex) {
			throw new TestFailureException(ex.getMessage());
		}
	}
	
	@Test(expected=TestFailureException.class)
	public void cutPasteTest(){
		openTestingShell();
		Keyboard keyboard = KeyboardFactory.getKeyboard();
		keyboard.type("test");
		keyboard.select(2, true);
		keyboard.writeToClipboard(true);
		keyboard.moveCursor(5, true);
		keyboard.pasteFromClipboard();
		try {
			assertEquals("stte", getText());
		} catch (ComparisonFailure ex) {
			throw new TestFailureException(ex.getMessage());
		}
	}

	private void openTestingShell(){
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = ShellTestUtils.createShell(SHELL_TITLE);
				text = new Text(shell, SWT.NONE);
				shell.layout();
			}
		});
		new DefaultShell(SHELL_TITLE);
		new DefaultText();
	}

	private String getText() {
		
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return text.getText();
			}
		});
	}

}
