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
package org.jboss.reddeer.generator.test.viewer;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.swt.keyboard.KeyboardFactory;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class CodeGeneratorViewTest {

	@Test
	public void openCodeGeneratorViewTest() {
		CodeGeneratorView codeGeneratorView = new CodeGeneratorView();
		codeGeneratorView.open();
		codeGeneratorView.close();
		codeGeneratorView.open();

		Assert.assertEquals("Press ALT + g to generate code", codeGeneratorView.getCode());
	}

	@Test
	public void runCodeGeneratorViewTest() {
		CodeGeneratorView codeGeneratorView = new CodeGeneratorView();
		codeGeneratorView.open();

		NewJavaProjectWizardDialog wizard = new NewJavaProjectWizardDialog();
		wizard.open();
		KeyboardFactory.getKeyboard().invokeKeyCombination(SWT.ALT, 'g');
		wizard.cancel();

		String code = codeGeneratorView.getCode();
		Assert.assertTrue(code.contains("public void activate() {\n\tnew DefaultShell(\"New Java Project\");\n}"));
	}

	private class CodeGeneratorView extends WorkbenchView {

		public CodeGeneratorView() {
			super("RedDeer Code Generator");
		}

		public String getCode() {
			open();
			return new DefaultStyledText().getText();
		}

	}

}
