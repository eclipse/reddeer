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
import org.jboss.reddeer.eclipse.wst.xml.ui.tabletree.XMLDesignPage;
import org.jboss.reddeer.eclipse.wst.xml.ui.tabletree.XMLDesignPage.XMLDesignPageNode;
import org.jboss.reddeer.eclipse.wst.xml.ui.tabletree.XMLMultiPageEditorPart;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class XMLDesignPageTest {

public static final File ZIP_FILE = new File(Activator.getTestResourcesLocation(MultiPageEditorTest.class), "xml-project.zip");
	
	protected static final String PROJECT_NAME = "xml-project";
	
	protected static final String FILE_NAME = "file.xml";
	
	private static XMLDesignPage page;
	
	@BeforeClass
	public static void importProject(){
		ExternalProjectImportWizardDialog wizard  = new ExternalProjectImportWizardDialog();
		wizard.open();

		WizardProjectsImportPage wizardPage = new WizardProjectsImportPage();
		wizardPage.setArchiveFile(ZIP_FILE.getAbsolutePath());
		wizardPage.selectProjects(PROJECT_NAME);

		wizard.finish();
		
		PackageExplorerPart explorer = new PackageExplorerPart();
		explorer.open();
		explorer.getProject(PROJECT_NAME).getProjectItem(FILE_NAME).open();
		
		XMLMultiPageEditorPart editor = new XMLMultiPageEditorPart(FILE_NAME);
		page = editor.getDesignPage();
	}
	
	@AfterClass
	public static void cleanProject(){
		PackageExplorerPart explorer = new PackageExplorerPart();
		explorer.open();
		explorer.getProject(PROJECT_NAME).delete(true);
	}
	
	@Test
	public void getNode(){
		assertThat(page.getNode("a", "b1").getName(), is("b1"));
	}
	
	@Test
	public void getNode_childNode(){
		XMLDesignPageNode node = page.getNode("a");
		
		assertThat(page.getNode(node, "b1", "c").getName(), is("c"));
	}
	
	@Test
	public void getAttribute(){
		XMLDesignPageNode node = page.getNode("a", "b1");
		
		assertThat(page.getAttributeValue(node, "y"), is("2"));
	}
	
	@Test
	public void getValue(){
		XMLDesignPageNode node = page.getNode("a", "b1", "c");
		
		assertThat(page.getContent(node), is("content"));
	}
}
