package org.jboss.reddeer.swt.test;


import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
import org.junit.BeforeClass;
import org.junit.Test;

public class TextTest extends RedDeerTest {

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
		new WaitUntil(new ShellWithTextIsActive("New"));
		Text t = new DefaultText("type filter text");
		t.setText("myvalue");
		assertTrue(t.getText().equals("myvalue"));
		new PushButton("Cancel").click();
	}

	@Test
	public void labeledTextTest() {
		assertTrue(new LabeledText("Name:").getText().equals("Original text"));
		Text text = new LabeledText("Name:");
		assertTrue(text.getText().equals("Original text"));
		text.setText("New text");
		assertTrue(text.getText().equals("New text"));
		text.setText("Original text");
	}
}
