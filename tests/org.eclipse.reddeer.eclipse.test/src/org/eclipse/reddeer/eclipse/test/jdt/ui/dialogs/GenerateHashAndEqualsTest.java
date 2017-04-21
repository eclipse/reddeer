/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.jdt.ui.dialogs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.jdt.ui.dialogs.GenerateHashCodeEqualsDialog;
import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassCreationWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.eclipse.reddeer.workbench.impl.editor.TextEditor;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@CleanWorkspace
@RunWith(RedDeerSuite.class)
public class GenerateHashAndEqualsTest {
	
	@AfterClass
	public static void deleteProject(){
		PackageExplorerPart pe = new PackageExplorerPart();
		pe.open();
		try{
			DefaultProject testProject = pe.getProject("GenHashProject");
			DeleteUtils.forceProjectDeletion(testProject,true);
		} catch (EclipseLayerException ele){
			// do nothing
		}
	}
	
	@Test
	public void generateHashAndEquals(){
		new WorkbenchShell().maximize();
		JavaProjectWizard jp = new JavaProjectWizard();
		jp.open();
		new NewJavaProjectWizardPageOne().setProjectName("GenHashProject");
		jp.finish();
		
		NewClassCreationWizard jc = new NewClassCreationWizard();
		jc.open();
		NewClassWizardPage jpp = new NewClassWizardPage();
		jpp.setName("GenHash");
		jc.finish();
		
		TextEditor te = new TextEditor("GenHash.java");
		te.insertText(3, 0, "private String text;");
		te.save();
		GenerateHashCodeEqualsDialog gd = new GenerateHashCodeEqualsDialog();
		gd.open(false);
		assertEquals(1,gd.getFields().size());
		assertEquals("text",gd.getFields().get(0).getFieldName());
		gd.selectAll();
		gd.ok();
		String text = te.getText();
		te.close();
		assertTrue(text.contains("equals"));
		assertTrue(text.contains("hashCode"));
	}

}
