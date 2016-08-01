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
package org.jboss.reddeer.eclipse.test.jst.j2ee.ui.project.facet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet.EarProjectFirstPage;
import org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet.EarProjectInstallPage;
import org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet.EarProjectWizard;
import org.jboss.reddeer.eclipse.jst.j2ee.wizard.NewJ2EEComponentSelectionPage;
import org.jboss.reddeer.eclipse.jst.servlet.ui.WebProjectFirstPage;
import org.jboss.reddeer.eclipse.jst.servlet.ui.WebProjectWizard;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaEEPerspective;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@OpenPerspective(JavaEEPerspective.class)
@RunWith(RedDeerSuite.class)
public class EARProjectWizardTest {
	
	private String projectName = "EARProject";
	
	@After
	public void deleteProject() {
		ProjectExplorer explorer = new ProjectExplorer();
		explorer.open();
		explorer.deleteAllProjects();
	}
	
	@Test
	public void createProject(){
		EarProjectWizard ear = new EarProjectWizard();
		ear.open();
		EarProjectFirstPage fp = new EarProjectFirstPage();
		fp.setProjectName(projectName);
		ear.next();
		EarProjectInstallPage ip = new EarProjectInstallPage();
		assertFalse(ip.isGenerateApplicationXML());
		ear.finish();
		
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		assertTrue(pe.containsProject(projectName));
	}
	
	@Test
	public void createProjectWithDefaultModules(){
		EarProjectWizard ear = new EarProjectWizard();
		ear.open();
		EarProjectFirstPage fp = new EarProjectFirstPage();
		fp.setProjectName(projectName);
		ear.next();
		EarProjectInstallPage ip = new EarProjectInstallPage();
		assertFalse(ip.isGenerateApplicationXML());
		NewJ2EEComponentSelectionPage jee=ip.newModule();
		String ejb = jee.getEJBModuleName();
		String conn = jee.getConnectorModuleName();
		String web = jee.getWebModuleName();
		String appClient = jee.getApplicationClientModuleName();
		jee.finish();
		ear.finish();
		
		ProjectExplorer explorer = new ProjectExplorer();
		explorer.open();
		assertTrue(explorer.containsProject(projectName));
		assertTrue(explorer.containsProject(ejb));
		assertTrue(explorer.containsProject(conn));
		assertTrue(explorer.containsProject(web));
		assertTrue(explorer.containsProject(appClient));
	}
	
	@Test
	public void createProjectWithWebModule(){
		EarProjectWizard ear = new EarProjectWizard();
		ear.open();
		EarProjectFirstPage fp = new EarProjectFirstPage();
		fp.setProjectName(projectName);
		ear.next();
		EarProjectInstallPage ip = new EarProjectInstallPage();
		assertFalse(ip.isGenerateApplicationXML());
		NewJ2EEComponentSelectionPage jee=ip.newModule();
		jee.toggleCreateDefaultModules(false);
		WebProjectWizard ww = jee.addWeb();
		WebProjectFirstPage wp = new WebProjectFirstPage();
		wp.setProjectName("CreatedWebProjectModule");
		ww.finish();
		ear.finish();
		
		ProjectExplorer explorer = new ProjectExplorer();
		explorer.open();
		
		assertTrue(explorer.containsProject(projectName));
		assertTrue(explorer.containsProject("CreatedWebProjectModule"));
	}

}
