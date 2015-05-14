package org.jboss.reddeer.snippet.test;

import static org.junit.Assert.*;

import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class MarkersValidationTest {

	@Test
	public void test() {
		// lets say that MyClass.java contains basic priltln hello world code
		TextEditor textEditor = new TextEditor("MyClass.java");
		// Change System to Systemx - we will artificially create some validation
		// marker since Systemx cannot be resolved
		textEditor.setText(textEditor.getText().replace("System", "Systemx"));
		textEditor.save();
		// wait for validation
		AbstractWait.sleep(TimePeriod.SHORT);
		assertEquals("Systemx cannot be resolved",
				textEditor.getMarkers().get(0).getText());
	}
}