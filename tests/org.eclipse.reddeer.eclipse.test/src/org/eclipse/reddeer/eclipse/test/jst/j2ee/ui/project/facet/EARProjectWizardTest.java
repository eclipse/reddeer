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
package org.eclipse.reddeer.eclipse.test.jst.j2ee.ui.project.facet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.jst.j2ee.ui.project.facet.EarProjectFirstPage;
import org.eclipse.reddeer.eclipse.jst.j2ee.ui.project.facet.EarProjectInstallPage;
import org.eclipse.reddeer.eclipse.jst.j2ee.ui.project.facet.EarProjectWizard;
import org.eclipse.reddeer.eclipse.jst.j2ee.wizard.DefaultJ2EEComponentCreationWizard;
import org.eclipse.reddeer.eclipse.jst.j2ee.wizard.NewJ2EEComponentSelectionPage;
import org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet.WebProjectFirstPage;
import org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet.WebProjectWizard;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaEEPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
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
		DefaultJ2EEComponentCreationWizard componentWizard = ip.newModule();
		NewJ2EEComponentSelectionPage jee= new NewJ2EEComponentSelectionPage();
		String ejb = jee.getEJBModuleName();
		String conn = jee.getConnectorModuleName();
		String web = jee.getWebModuleName();
		String appClient = jee.getApplicationClientModuleName();
		componentWizard.finish();
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
		ip.newModule();
		NewJ2EEComponentSelectionPage jee= new NewJ2EEComponentSelectionPage();
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
