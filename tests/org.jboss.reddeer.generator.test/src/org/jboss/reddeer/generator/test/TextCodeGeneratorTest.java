package org.jboss.reddeer.generator.test;

import static org.jboss.reddeer.generator.test.utils.CodeGeneratorTestUtils.assertConstructorCode;
import static org.jboss.reddeer.generator.test.utils.CodeGeneratorTestUtils.closeTestingShell;
import static org.jboss.reddeer.generator.test.utils.CodeGeneratorTestUtils.createTestedControlWithShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.generator.CodeGenerator;
import org.jboss.reddeer.generator.TextCodeGenerator;
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
public class TextCodeGeneratorTest {

	private static CodeGenerator codeGenerator = new TextCodeGenerator();

	@After
	public void closeShell() {
		closeTestingShell();
	}

	@Test
	public void getConstructorTextWithLabelTest() {
		Text text = createTestedControlWithShell(new CodeGeneratorTestUtils.TestingShell<Text>() {

			@Override
			public Text createControls(Shell shell) {
				Label label = new Label(shell, SWT.LEFT);
				label.setText("Test");
				label.setSize(100, 30);
				Text text = new Text(shell, SWT.LEFT);
				text.setSize(200, 30);
				return text;
			}
		});

		assertConstructorCode("new LabeledText(\"Test\")", codeGenerator, text);

	}

	@Test
	public void getConstructorTextWithoutLabelTest() {
		Text text = createTestedControlWithShell(new CodeGeneratorTestUtils.TestingShell<Text>() {

			@Override
			public Text createControls(Shell shell) {
				Text text = new Text(shell, SWT.LEFT);
				text.setSize(200, 30);
				return text;
			}
		});

		assertConstructorCode("new DefaultText(0)", codeGenerator, text);

	}

}
