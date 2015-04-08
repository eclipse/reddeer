package org.jboss.reddeer.generator.test;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.generator.CodeGenerator;
import org.jboss.reddeer.generator.ShellCodeGenerator;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.core.util.Display;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author apodhrad
 *
 */
@RunWith(RedDeerSuite.class)
public class ShellCodeGeneratorTest {

	private static CodeGenerator codeGenerator = new ShellCodeGenerator();

	@Test
	public void getConstructorShellTest() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = new Shell();
				shell.setText("Test");
				Assert.assertEquals("new DefaultShell(\"Test\")", codeGenerator.getConstructor(shell));
			}
		});
	}

}
