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
package org.eclipse.reddeer.eclipse.test.wst.html.ui.wizard;

import static org.junit.Assert.*;

import org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet.WebProjectFirstPage;
import org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet.WebProjectWizard;
import org.eclipse.reddeer.eclipse.wst.html.ui.wizard.NewHTMLFileWizardPage;
import org.eclipse.reddeer.eclipse.wst.html.ui.wizard.NewHTMLTemplatesWizardPage;
import org.eclipse.reddeer.eclipse.wst.html.ui.wizard.NewHTMLWizard;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
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
		WebProjectFirstPage wp =  new WebProjectFirstPage(wd);
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
		NewHTMLFileWizardPage hp = new NewHTMLFileWizardPage(hw);
		hp.setFileName("testHTML");
		assertEquals("testHTML", hp.getFileName());
		assertEquals("WebContent", hp.getSelectedParentFolder().getText());
		hp.selectParentFolder("HTMLProjectTest","src");
		assertEquals("src", hp.getSelectedParentFolder().getText());
		hw.next();
		NewHTMLTemplatesWizardPage tp = new NewHTMLTemplatesWizardPage(hw);
		tp.toggleUseHTMLTemplate(false);
		assertFalse(tp.isUseHTMLTeplate());
		tp.toggleUseHTMLTemplate(true);
		assertTrue(tp.isUseHTMLTeplate());
		assertNotNull(tp.getHTMLTemplate());
		hw.finish();
	}

}
