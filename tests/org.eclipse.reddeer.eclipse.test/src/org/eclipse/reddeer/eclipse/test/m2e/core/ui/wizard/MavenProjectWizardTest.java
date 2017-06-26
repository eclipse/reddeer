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
package org.eclipse.reddeer.eclipse.test.m2e.core.ui.wizard;

import static org.junit.Assert.*;

import org.eclipse.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizard;
import org.eclipse.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizardArchetypePage;
import org.eclipse.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizardArchetypeParametersPage;
import org.eclipse.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizardArtifactPage;
import org.eclipse.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizardPage;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class MavenProjectWizardTest {
	
	@AfterClass
	public static void clean(){
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		pe.deleteAllProjects();
	}
	
	@Test
	public void createMavenProject(){
		MavenProjectWizard mw = new MavenProjectWizard();
		mw.open();
		mw.next();
		MavenProjectWizardArchetypePage mp1 = new MavenProjectWizardArchetypePage(mw);
		assertEquals("All Catalogs",mp1.getArchetypeCatalog());
		mp1.selectArchetypeCatalog("Internal");
		assertEquals("Internal",mp1.getArchetypeCatalog());
		mp1.selectArchetype("org.apache.maven.archetypes","maven-archetype-quickstart",null);
		mw.next();
		MavenProjectWizardArchetypeParametersPage mp2 = new MavenProjectWizardArchetypeParametersPage(mw);
		mp2.setArtifactId("artifact");
		mp2.setGroupId("group");
		mp2.setVersion("1.0.0");
		assertEquals("artifact",mp2.getArtifactId());
		assertEquals("group",mp2.getGroupId());
		assertEquals("1.0.0",mp2.getVersion());
		assertEquals("group.artifact",mp2.getPackage());
		mw.finish();
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		assertTrue(pe.containsProject("artifact"));
	}
	
	public void createCleanMavenProject(){
		MavenProjectWizard mw = new MavenProjectWizard();
		mw.open();
		MavenProjectWizardPage mp = new MavenProjectWizardPage(mw);
		mp.createSimpleProject(true);
		mw.next();
		MavenProjectWizardArtifactPage ma = new MavenProjectWizardArtifactPage(mw);
		ma.setGroupId("g");
		ma.setArtifactId("a");
		mw.finish();
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		assertTrue(pe.containsProject("a"));
	}

}
