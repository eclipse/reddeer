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
package org.jboss.reddeer.eclipse.test.ui.part;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.test.Activator;
import org.jboss.reddeer.eclipse.ui.part.MultiPageEditor;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(RedDeerSuite.class)
public class MultiPageEditorTest {

	public static final File ZIP_FILE = new File(Activator.getTestResourcesLocation(MultiPageEditorTest.class), "xml-project.zip");
	
	protected static final String PROJECT_NAME = "xml-project";
	
	protected static final String FILE_NAME = "file.xml";
	
	private static MultiPageEditor editor;
	
	@BeforeClass
	public static void importProject(){
		ExternalProjectImportWizardDialog wizard  = new ExternalProjectImportWizardDialog();
		wizard.open();

		WizardProjectsImportPage wizardPage = new WizardProjectsImportPage();
		wizardPage.setArchiveFile(ZIP_FILE.getAbsolutePath());
		wizardPage.selectProjects(PROJECT_NAME);

		wizard.finish();
		
		PackageExplorer explorer = new PackageExplorer();
		explorer.open();
		explorer.getProject(PROJECT_NAME).getChild(FILE_NAME).open();
		
		editor = new MultiPageEditor(FILE_NAME);
	}
	
	@AfterClass
	public static void cleanProject(){
		PackageExplorer explorer = new PackageExplorer();
		explorer.open();
		explorer.getProject(PROJECT_NAME).delete(true);
	}
	
	@Test
	public void selectPage(){
		editor.selectPage("Design");
		new DefaultTreeItem("a", "b1");
		
		// loose focus
		new PackageExplorer().open();
		
		editor.selectPage("Source");
		assertTrue(new DefaultStyledText().getText().contains("</a>"));
	}
}
