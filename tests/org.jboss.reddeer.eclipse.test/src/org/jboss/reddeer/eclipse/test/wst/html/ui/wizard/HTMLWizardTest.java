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
package org.jboss.reddeer.eclipse.test.wst.html.ui.wizard;

import static org.junit.Assert.*;

import org.jboss.reddeer.eclipse.jst.servlet.ui.project.facet.WebProjectFirstPage;
import org.jboss.reddeer.eclipse.jst.servlet.ui.project.facet.WebProjectWizard;
import org.jboss.reddeer.eclipse.wst.html.ui.wizard.NewHTMLFileWizardPage;
import org.jboss.reddeer.eclipse.wst.html.ui.wizard.NewHTMLTemplatesWizardPage;
import org.jboss.reddeer.eclipse.wst.html.ui.wizard.NewHTMLWizard;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class HTMLWizardTest {
	
	@BeforeClass
	public static void prepareWorkspace(){
		WebProjectWizard wd = new WebProjectWizard();
		wd.open();
		WebProjectFirstPage wp =  new WebProjectFirstPage();
		wp.setProjectName("HTMLProjectTest");
		wd.finish();
	}
	
	@AfterClass
	public static void deleteWorkspace(){
		new CleanWorkspaceRequirement().fulfill();
	}
	
	@Test
	public void createHTMLFile(){
		NewHTMLWizard hw = new NewHTMLWizard();
		hw.open();
		NewHTMLFileWizardPage hp = new NewHTMLFileWizardPage();
		hp.setFileName("testHTML");
		assertEquals("testHTML", hp.getFileName());
		assertEquals("WebContent", hp.getSelectedParentFolder().getText());
		hp.selectParentFolder("HTMLProjectTest","src");
		assertEquals("src", hp.getSelectedParentFolder().getText());
		hw.next();
		NewHTMLTemplatesWizardPage tp = new NewHTMLTemplatesWizardPage();
		tp.toggleUseHTMLTemplate(false);
		assertFalse(tp.isUseHTMLTeplate());
		tp.toggleUseHTMLTemplate(true);
		assertTrue(tp.isUseHTMLTeplate());
		assertNotNull(tp.getHTMLTemplate());
		hw.finish();
	}

}
