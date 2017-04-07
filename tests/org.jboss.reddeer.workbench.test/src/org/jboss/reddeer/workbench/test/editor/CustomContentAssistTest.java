/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.workbench.test.editor;

import static org.junit.Assert.*;

import java.util.List;

import org.jboss.reddeer.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.jboss.reddeer.eclipse.ui.wizards.newresource.BasicNewFileResourceWizard;
import org.jboss.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.jboss.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizardFirstPage;
import org.jboss.reddeer.jface.text.contentassist.ContentAssistant;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@CleanWorkspace
public class CustomContentAssistTest {
	
	@After
	public void clean(){
		new CleanWorkspaceRequirement().fulfill();
	}
	
	//this editor does not have ContentAssist in shellmenu and can only be invoked by keyboard
	@Test
	public void testCustomContentAssist(){
		BasicNewProjectResourceWizard projectWizard = new BasicNewProjectResourceWizard();
		projectWizard.open();
		BasicNewProjectResourceWizardFirstPage pPage = new BasicNewProjectResourceWizardFirstPage();
		pPage.setProjectName("project");
		projectWizard.finish();
		
		BasicNewFileResourceWizard fileWizard = new BasicNewFileResourceWizard();
		fileWizard.open();
		WizardNewFileCreationPage page = new WizardNewFileCreationPage();
		page.setFolderPath("project");
		page.setFileName("abc.ca");
		fileWizard.finish();
		ContentAssistant ca = new DefaultEditor().openContentAssistant();
		List<String> proposals = ca.getProposals();
		assertNotNull(proposals);
		assertTrue(proposals.size() == 3);
		assertTrue(proposals.contains("a"));
		assertTrue(proposals.contains("b"));
		assertTrue(proposals.contains("c"));
		ca.close();
	}

}
