package org.jboss.reddeer.snippet.test;

import org.jboss.reddeer.jface.text.contentassist.ContentAssistant;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class QuickFixTest {

	@Test
	public void test() {
		TextEditor textEditor = new TextEditor();
		textEditor.selectText("JavaClass");
		ContentAssistant ca = textEditor.openQuickFixContentAssistant();
		ca.chooseProposal("Rename in file");
	}
}
