package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.StyledText;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author rhopp
 *
 */
@RunWith(RedDeerSuite.class)
public class StyledTextTest {
	
	private org.eclipse.swt.custom.StyledText styledText;
	
	@BeforeClass
	public static void openView(){
		new WorkbenchView("RedDeer SWT Controls").open();
	}
	
	@After
	public void setup(){
		//try to close testing shell
		closeTestingShell();
	}
	
	
	@Test
	public void defaultStyledTextTest(){
		assertTrue(new DefaultStyledText().getText().equals("Styled text"));
		StyledText styledText= new DefaultStyledText("Styled text");
		assertTrue(styledText.getText().equals("Styled text"));
		styledText.setText("Modified styled text");
		assertTrue(styledText.getText().equals("Modified styled text"));
	}
	
	@Test
	public void defaultStyledTextGetPositionTest(){
		openTestingShell();
		StyledText sText = new DefaultStyledText();
		assertEquals(sText.getPositionOfText("styledText"), 8);
	}
	
	@Test
	public void defaultStyledTextGetPositionNonExistingTest(){
		openTestingShell();
		StyledText sText = new DefaultStyledText();
		assertEquals(-1, sText.getPositionOfText("styledText1"));
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
				styledText = new org.eclipse.swt.custom.StyledText(shell, SWT.NONE);
				styledText.setText("Testing styledText widget.");
				shell.open();
				shell.setFocus();
			}
		});
		new DefaultShell("Testing shell");
	}
	
	private void closeTestingShell(){
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
		new WaitWhile(new ShellWithTextIsActive("Testing shell"));
	}

}
