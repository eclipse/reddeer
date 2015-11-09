package org.jboss.reddeer.generator.test;

import static org.jboss.reddeer.generator.test.utils.CodeGeneratorTestUtils.assertConstructorCode;
import static org.jboss.reddeer.generator.test.utils.CodeGeneratorTestUtils.closeTestingShell;
import static org.jboss.reddeer.generator.test.utils.CodeGeneratorTestUtils.createTestedControlWithShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.generator.CodeGenerator;
import org.jboss.reddeer.generator.ComboCodeGenerator;
import org.jboss.reddeer.generator.test.utils.CodeGeneratorTestUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
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

	@After
	public void closeShell() {
		closeTestingShell();
	}

	@Test
	public void getConstructorTextWithLabelTest() {
		Combo combo = createTestedControlWithShell(new CodeGeneratorTestUtils.TestingShell<Combo>() {

			@Override
			public Combo createControls(Shell shell) {
				Label label = new Label(shell, SWT.LEFT);
				label.setText("Test");
				label.setSize(100, 30);
				Combo combo = new Combo(shell, SWT.LEFT);
				combo.setSize(200, 30);
				return combo;
			}
		});

		assertConstructorCode("new LabeledCombo(\"Test\")", codeGenerator, combo);
	}

	@Test
	public void getConstructorTextWithoutLabelTest() {
		Combo combo = createTestedControlWithShell(new CodeGeneratorTestUtils.TestingShell<Combo>() {

			@Override
			public Combo createControls(Shell shell) {
				Combo combo = new Combo(shell, SWT.LEFT);
				combo.setSize(200, 30);
				return combo;
			}
		});

		assertConstructorCode("new DefaultCombo(0)", codeGenerator, combo);
	}

}
