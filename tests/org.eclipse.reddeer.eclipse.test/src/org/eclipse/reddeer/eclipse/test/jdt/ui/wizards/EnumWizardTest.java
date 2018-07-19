/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.jdt.ui.wizards;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewEnumCreationWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewEnumWizardPage;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.eclipse.reddeer.workbench.impl.editor.TextEditor;
import org.junit.Test;
import org.junit.runner.RunWith;

@CleanWorkspace
@RunWith(RedDeerSuite.class)
public class EnumWizardTest {
	
	@Test
	public void createEnumClass(){
		JavaProjectWizard jp = new JavaProjectWizard();
		jp.open();
		new NewJavaProjectWizardPageOne(jp).setProjectName("EnumProject");
		jp.finish();
		
		NewEnumCreationWizard ed = new NewEnumCreationWizard();
		ed.open();
		NewEnumWizardPage ep = ed.getFirstPage();
		ep.setName("MyEnum");
		ep.setPackage("enumPackage");
		assertTrue(ep.isPublicModifier());
		assertFalse(ep.isGenerateComments());
		ed.finish();
		TextEditor te = new TextEditor("MyEnum.java");
		assertTrue(te.getText().contains("public enum MyEnum"));
		te.close();
	}

}
