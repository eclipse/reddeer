/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.generator.test;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.generator.CodeGenerator;
import org.jboss.reddeer.generator.ShellCodeGenerator;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
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
