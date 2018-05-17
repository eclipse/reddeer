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
package org.eclipse.reddeer.eclipse.test.wst.xml.ui.tabletree;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.test.Activator;
import org.eclipse.reddeer.eclipse.test.ui.part.MultiPageEditorTest;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.eclipse.reddeer.eclipse.wst.xml.ui.tabletree.XMLEditorFile;
import org.eclipse.reddeer.eclipse.wst.xml.ui.tabletree.XMLMultiPageEditorPart;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class XMLMultiPageEditorTest {

public static final File ZIP_FILE = new File(Activator.getTestResourcesLocation(MultiPageEditorTest.class), "xml-project.zip");
	
	protected static final String PROJECT_NAME = "xml-project";
	
	protected static final String FILE_NAME = "file.xml";
	
	private static XMLMultiPageEditorPart editor;
	
	@BeforeClass
	public static void importProject(){
		ExternalProjectImportWizardDialog wizard  = new ExternalProjectImportWizardDialog();
		wizard.open();

		WizardProjectsImportPage wizardPage = new WizardProjectsImportPage(wizard);
		wizardPage.setArchiveFile(ZIP_FILE.getAbsolutePath());
		wizardPage.selectProjects(PROJECT_NAME);

		wizard.finish();
		
		PackageExplorerPart explorer = new PackageExplorerPart();
		explorer.open();
		explorer.getProject(PROJECT_NAME).getProjectItem(FILE_NAME).open();
		
		editor = new XMLMultiPageEditorPart(FILE_NAME);
	}
	
	@AfterClass
	public static void cleanProject(){
		PackageExplorerPart explorer = new PackageExplorerPart();
		explorer.open();
		explorer.getProject(PROJECT_NAME).delete(true);
	}
	
	@Test
	public void selectDesignPage(){
		editor.selectDesignPage();
		new DefaultTreeItem("a", "b1");
	}
	
	@Test
	public void selectSourcePage(){
		editor.selectSourcePage();
		assertTrue(new DefaultStyledText().getText().contains("</a>"));
	}
	
	@Test
	public void getDesignPage(){
		editor.selectSourcePage();
		
		assertThat(editor.getDesignPage().getNode("a", "b1").getName(), is("b1"));
	}
	
	@Test
	public void getSourcePage(){
		editor.selectDesignPage();
		
		assertTrue(editor.getSourcePage().getText().contains("</a>"));
	}

	@Test
	public void testGettingingAssociatedXMLEditorFile() {
		XMLEditorFile xmlEditorFile = editor.getAssociatedFile();
		assertEquals("/" + PROJECT_NAME + "/" + FILE_NAME, xmlEditorFile.getRelativePath());
	}

	@Test
	public void testEvaluatingXPathOnAssociatedXMLEditorFile() {
		XMLEditorFile xmlEditorFile = editor.getAssociatedFile();
		assertEquals("4", xmlEditorFile.xpath("/a/b1[2]/@y"));
	}

}
