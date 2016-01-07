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
package org.jboss.reddeer.swt.test.keyboard;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.keyboard.Keyboard;
import org.jboss.reddeer.swt.keyboard.KeyboardFactory;
import org.jboss.reddeer.swt.test.utils.ShellTestUtils;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class KeyboardTest {
	
	protected static final String SHELL_TITLE = "Keyboard testing shell";
	private Text text;
	
	@After
	public void cleanup() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				ShellTestUtils.closeShell(SHELL_TITLE);
			}
		});
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
		new DefaultShell("Search").close();
	}
	
	@Test
	public void selectionTest(){
		openTestingShell();
		KeyboardFactory.getKeyboard().type("test");
		KeyboardFactory.getKeyboard().select(2, true);
		KeyboardFactory.getKeyboard().type(SWT.DEL);
		assertEquals("te", getText());
	}
	
	@Test
	public void copyPasteTest(){
		openTestingShell();
		Keyboard keyboard = KeyboardFactory.getKeyboard();
		keyboard.type("test");
		keyboard.select(2, true);
		keyboard.writeToClipboard(false);
		keyboard.moveCursor(5, true);
		keyboard.pasteFromClipboard();
		assertEquals("sttest", getText());
	}
	
	@Test
	public void cutPasteTest(){
		openTestingShell();
		Keyboard keyboard = KeyboardFactory.getKeyboard();
		keyboard.type("test");
		keyboard.select(2, true);
		keyboard.writeToClipboard(true);
		keyboard.moveCursor(5, true);
		keyboard.pasteFromClipboard();
		assertEquals("stte", getText());
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
