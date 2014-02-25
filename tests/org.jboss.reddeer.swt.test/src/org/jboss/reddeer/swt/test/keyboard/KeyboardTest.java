package org.jboss.reddeer.swt.test.keyboard;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.keyboard.Keyboard;
import org.jboss.reddeer.swt.keyboard.KeyboardFactory;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.junit.After;
import org.junit.Test;

public class KeyboardTest extends RedDeerTest {

	
	private Text text;
	
	@After
	public void cleanup() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				for (Shell shell : org.jboss.reddeer.swt.
						util.Display.getDisplay().getShells()) {
					if (shell.getText().equals("Testing shell")) {
						shell.dispose();
						break;
					}
				}
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
				org.eclipse.swt.widgets.Display display = org.eclipse.swt.widgets.Display
						.getDefault();
				Shell shell = new Shell(display);
				shell.setLayout(new GridLayout());
				shell.setText("Testing shell");
				text = new Text(shell, SWT.NONE);
				shell.open();
				shell.setFocus();
			}
		});
		new DefaultShell("Testing shell");
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
