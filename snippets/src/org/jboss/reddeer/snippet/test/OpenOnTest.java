package org.jboss.reddeer.snippet.test;

import org.jboss.reddeer.jface.text.contentassist.ContentAssistant;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class OpenOnTest {

	@Test
	public void test() {
		// lets say that MyClass.java contains basic priltln hello world code
		TextEditor textEditor = new TextEditor("MyClass.java");
		// select println in editor
		textEditor.selectText("println");
		// open OpenOn assistant
		ContentAssistant ca = textEditor.openOpenOnAssistant();
		// choose some proposal
		ca.chooseProposal("Open Declaration");
		// for selected println, Open Declaration choice opens PrintStream.class class
		new TextEditor("PrintStream.class");
	}
}