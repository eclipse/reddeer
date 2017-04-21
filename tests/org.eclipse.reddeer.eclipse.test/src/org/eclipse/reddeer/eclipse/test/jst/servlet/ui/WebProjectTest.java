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
package org.eclipse.reddeer.eclipse.test.jst.servlet.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet.WebProjectFirstPage;
import org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet.WebProjectSecondPage;
import org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet.WebProjectThirdPage;
import org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet.WebProjectWizard;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaEEPerspective;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@OpenPerspective(JavaEEPerspective.class)
@RunWith(RedDeerSuite.class)
public class WebProjectTest {
	
	private PackageExplorerPart packageExplorer;
	private String projectName = "web project";
	
	@Before
	public void openPackageExplorer() {
		packageExplorer = new PackageExplorerPart();
		packageExplorer.open();
	}
	
	@After
	public void deleteProject() {
		DeleteUtils.forceProjectDeletion(packageExplorer.getProject(projectName), true);
	}
	
	@Test
	public void createWebProject(){
		WebProjectWizard ww = new WebProjectWizard();
		ww.open();
		WebProjectFirstPage fp = new WebProjectFirstPage();
		fp.setProjectName(projectName);
		ww.finish();
		assertTrue(packageExplorer.containsProject(projectName));
	}
	
	@Test
	public void createWebProject1(){
		WebProjectWizard ww = new WebProjectWizard();
		ww.open();
		WebProjectFirstPage fp = new WebProjectFirstPage();
		fp.setProjectName(projectName);
		assertEquals(projectName, fp.getProjectName());
		ww.next();
		WebProjectSecondPage sp = new WebProjectSecondPage();
		sp.addSourceFoldersOnBuildPath("source");
		sp.removeSourceFoldersOnBuildPath("src");
		assertEquals(1,sp.getSourceFolders().size());
		assertEquals("source", sp.getSourceFolders().get(0));
		ww.next();
		WebProjectThirdPage tp = new WebProjectThirdPage();
		tp.setGenerateWebXmlDeploymentDescriptor(true);
		ww.finish();
		assertTrue(packageExplorer.containsProject(projectName));
		assertTrue(packageExplorer.getProject(projectName).containsResource("source"));
		assertFalse(packageExplorer.getProject(projectName).containsResource("src"));
		assertTrue(packageExplorer.getProject(projectName).containsResource("WebContent","WEB-INF","web.xml"));
	}

}
