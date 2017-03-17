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
package org.jboss.reddeer.eclipse.test.wst.xml.ui.tabletree;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import org.jboss.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.jboss.reddeer.eclipse.test.Activator;
import org.jboss.reddeer.eclipse.test.ui.part.MultiPageEditorTest;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.jboss.reddeer.eclipse.wst.xml.ui.tabletree.XMLEditorFile;
import org.jboss.reddeer.eclipse.wst.xml.ui.tabletree.XMLMultiPageEditorPart;
import org.jboss.reddeer.eclipse.wst.xml.ui.tabletree.XMLSourcePage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class XMLSourcePageTest {

	public static final File ZIP_FILE = new File(Activator.getTestResourcesLocation(MultiPageEditorTest.class),
			"xml-project.zip");

	protected static final String PROJECT_NAME = "xml-project";

	protected static final String FILE_NAME = "file.xml";

	protected static final String NAMESPACES_FILE_NAME = "TomcatServerRequirement.xml";

	private static PackageExplorerPart explorer;

	@BeforeClass
	public static void importProject() {
		ExternalProjectImportWizardDialog wizard = new ExternalProjectImportWizardDialog();
		wizard.open();

		WizardProjectsImportPage wizardPage = new WizardProjectsImportPage();
		wizardPage.setArchiveFile(ZIP_FILE.getAbsolutePath());
		wizardPage.selectProjects(PROJECT_NAME);

		wizard.finish();

		explorer = new PackageExplorerPart();
		explorer.open();
	}

	@AfterClass
	public static void cleanProject() {
		explorer.open();
		explorer.getProject(PROJECT_NAME).delete(true);
	}

	@Test
	public void evaluateXPath() {
		explorer.getProject(PROJECT_NAME).getProjectItem(FILE_NAME).open();

		XMLMultiPageEditorPart editor = new XMLMultiPageEditorPart(FILE_NAME);
		XMLSourcePage page = editor.getSourcePage();
		XMLEditorFile xmlEditorFile = page.getAssociatedFile();

		String result = xmlEditorFile.xpath("/a/b1/c/text()");
		assertThat(result, is("content"));
	}

	@Test
	public void evaluateXPathWithNamespace() {
		explorer.getProject(PROJECT_NAME).getProjectItem(NAMESPACES_FILE_NAME).openWith("XML Editor");

		XMLMultiPageEditorPart editor = new XMLMultiPageEditorPart(NAMESPACES_FILE_NAME);
		XMLSourcePage page = editor.getSourcePage();
		XMLEditorFile xmlEditorFile = page.getAssociatedFile();

		String result = xmlEditorFile.xpath("/:testrun/:requirements/server:server-requirement/@name");
		assertThat(result, is("Tomcat7"));
	}

	@Test
	public void evaluateXPathWithIgnoringNamespace() {
		explorer.getProject(PROJECT_NAME).getProjectItem(NAMESPACES_FILE_NAME).openWith("XML Editor");

		XMLMultiPageEditorPart editor = new XMLMultiPageEditorPart(NAMESPACES_FILE_NAME);
		XMLSourcePage page = editor.getSourcePage();
		XMLEditorFile xmlEditorFile = page.getAssociatedFile();

		String result = xmlEditorFile.xpath("/testrun/requirements/server-requirement/@name", false);
		assertThat(result, is("Tomcat7"));
	}

}
