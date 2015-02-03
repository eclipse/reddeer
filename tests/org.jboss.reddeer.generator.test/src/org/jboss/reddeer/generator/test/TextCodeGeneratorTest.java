package org.jboss.reddeer.generator.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.generator.CodeGenerator;
import org.jboss.reddeer.generator.TextCodeGenerator;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.util.Display;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author apodhrad
 *
 */
@RunWith(RedDeerSuite.class)
public class TextCodeGeneratorTest {

	private static CodeGenerator codeGenerator = new TextCodeGenerator();

	@Test
	public void getConstructorTextWithLabelTest() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = new Shell();
				Label label = new Label(shell, SWT.LEFT);
				label.setText("Test");
				label.setSize(100, 30);
				Text text = new Text(shell, SWT.LEFT);
				text.setSize(200, 30);
				Assert.assertEquals("new LabeledText(\"Test\")", codeGenerator.getConstructor(text));
			}
		});

	}

	@Test
	public void getConstructorTextWithoutLabelTest() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = new Shell();
				Text text = new Text(shell, SWT.LEFT);
				text.setSize(200, 30);
				Assert.assertEquals("new DefaultText(0)", codeGenerator.getConstructor(text));
			}
		});

	}

}
