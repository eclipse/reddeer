package org.jboss.reddeer.swt.test.keyboard;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellIsActive;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.event.KeyEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.jboss.reddeer.eclipse.ui.ide.NewFileCreationWizardDialog;
import org.jboss.reddeer.eclipse.ui.ide.NewFileCreationWizardPage;
import org.jboss.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.keyboard.Action;
import org.jboss.reddeer.swt.keyboard.Key;
import org.jboss.reddeer.swt.keyboard.Keyboard;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.junit.After;
import org.junit.Test;

public class KeyboardTest extends RedDeerTest{

	private Text text;
	
	@Override
	protected void setUp() {
		super.setUp();
	}
	
	@After
	public void cleanup() {
		UIThreadRunnable.syncExec(new VoidResult() {

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
	public void keyCombinationTest(){
		Keyboard.invokeKeyEvent(Key.CTRL.getKeyCode(), KeyEvent.VK_N);
		new WaitUntil(new ShellWithTextIsActive("New"));
		DefaultShell shell = new DefaultShell("New");
		shell.close();
	}
	
	@Test
	public void typingTest(){
		UIThreadRunnable.syncExec(new VoidResult() {

			@Override
			public void run() {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				shell.setLayout(new GridLayout());
				shell.setText("Testing shell");
				createControls(shell);
				shell.open();
				shell.setFocus();
			}
		});
		Keyboard.invoke("Test");
		assertEquals("Test", getText());
		Keyboard.moveCursor(3, true);
		Keyboard.invoke("t");
		try{
			new WaitUntil(new TextIsTypedCondition("Ttest"));
		}catch(WaitTimeoutExpiredException ex){
			assertEquals("Ttest", getText());
		}
		Keyboard.select(2, false);
		Keyboard.invoke(Action.COPY);
		Keyboard.moveCursor(5, true);
		Keyboard.invoke(Action.PASTE);
		try{
			new WaitUntil(new TextIsTypedCondition("esTtest"));
		}catch(WaitTimeoutExpiredException ex){
			assertEquals("esTtest", getText());
		}
	}
	
	private void createControls(Shell shell) {
		text = new Text(shell, SWT.NONE);
	}
	
	private String getText(){
		return UIThreadRunnable.syncExec(new Result<String>() {
			@Override
			public String run() {
				return text.getText();
			}
		});
	}
	
	class TextIsTypedCondition implements WaitCondition{

		private String text;
		
		public TextIsTypedCondition(String text) {
			this.text = text;
		}
		
		@Override
		public boolean test() {
			if (getText().equals(text)){
				return true;
			}else{
				return false;
			}
		}

		@Override
		public String description() {
			return null;
		}
		
	}

}
