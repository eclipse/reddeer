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
package org.jboss.reddeer.eclipse.test.jdt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.NewEnumWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewEnumWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;
import org.junit.Test;
import org.junit.runner.RunWith;

@CleanWorkspace
@RunWith(RedDeerSuite.class)
public class EnumWizardTest {
	
	@Test
	public void createEnumClass(){
		NewJavaProjectWizardDialog jp = new NewJavaProjectWizardDialog();
		jp.open();
		new NewJavaProjectWizardPage().setProjectName("EnumProject");
		jp.finish();
		
		NewEnumWizardDialog ed = new NewEnumWizardDialog();
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
