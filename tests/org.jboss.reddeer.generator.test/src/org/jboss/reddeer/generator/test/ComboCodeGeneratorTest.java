package org.jboss.reddeer.generator.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.generator.CodeGenerator;
import org.jboss.reddeer.generator.ComboCodeGenerator;
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
public class ComboCodeGeneratorTest {

	private static CodeGenerator codeGenerator = new ComboCodeGenerator();

	@Test
	public void getConstructorTextWithLabelTest() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = new Shell();
				Label label = new Label(shell, SWT.LEFT);
				label.setText("Test");
				label.setSize(100, 30);
				Combo combo = new Combo(shell, SWT.LEFT);
				combo.setSize(200, 30);
				Assert.assertEquals("new LabeledCombo(\"Test\")", codeGenerator.getConstructor(combo));
			}
		});

	}

	@Test
	public void getConstructorTextWithoutLabelTest() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = new Shell();
				Combo combo = new Combo(shell, SWT.LEFT);
				combo.setSize(200, 30);
				Assert.assertEquals("new DefaultCombo(0)", codeGenerator.getConstructor(combo));
			}
		});

	}

}
