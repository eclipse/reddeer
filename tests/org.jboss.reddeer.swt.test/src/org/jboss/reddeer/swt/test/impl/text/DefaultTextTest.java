package org.jboss.reddeer.swt.test.impl.text;


import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class DefaultTextTest {

	@BeforeClass
	public static void openExplorer() {
		new WorkbenchView("RedDeer SWT Controls").open();
	}

	@Test
	public void defaultTextTest() {
		assertTrue(new DefaultText(0).getText().equals("Original text"));
		Text text = new DefaultText("Original text");
		assertTrue(text.getText().equals("Original text"));
		text.setText("New text");
		assertTrue(new DefaultText(0).getText().equals("New text"));
		assertTrue(text.getText().equals("New text"));
		assertTrue(new DefaultText("New text").getText().equals("New text"));
		text.setText("Original text");
	}
	
	@Test
	public void testInDialog() {
		new ShellMenu("File","New","Other...").select();
		new DefaultShell("New");
		Text t = new DefaultText(0);
		t.setText("myvalue");
		assertTrue(t.getText().equals("myvalue"));
		new PushButton("Cancel").click();
	}
}
