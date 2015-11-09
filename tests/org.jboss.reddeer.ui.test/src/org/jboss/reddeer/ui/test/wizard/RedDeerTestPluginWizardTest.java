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

package org.jboss.reddeer.ui.test.wizard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.ui.views.contentoutline.OutlineView;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.FinishButton;
import org.jboss.reddeer.swt.impl.button.RadioButton;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.ui.test.wizard.impl.RedDeerTestPluginWizard;
import org.jboss.reddeer.ui.test.wizard.impl.RedDeerTestPluginWizardPage;
import org.junit.BeforeClass;
import org.junit.Test;

public class RedDeerTestPluginWizardTest extends RedDeerWizardTestCase {

	private static final String APPLICATION = "org.eclipse.ui.ide.workbench";
	private static final String PRODUCT = "org.eclipse.platform.ide";
	private static final String PLUGIN_NAME = "reddeer.test.plugin";
	private static final String PLUGIN_ID = "test.plugin";
	private static final String PLUGIN_PROVIDER = "JBoss by Red Hat";
	private static final String VERSION = "8.2.9.qualifier";
	
	@BeforeClass
	public static void setup() {
		wizard = new RedDeerTestPluginWizard();
		projectName = PLUGIN_ID;
	}
	
	@Test
	public void testOpen() {
		assertEquals("New " + RedDeerTestPluginWizard.NAME.replace("-", ""), new DefaultShell().getText());
		
		assertTrue("Product id radio is not selected", new RadioButton("Product id:").isSelected());
		assertTrue("Product combo is not enabled", new DefaultCombo().isEnabled());
		
		assertFalse("Application id radio is selected", new RadioButton("Application id:").isSelected());
		assertFalse("Application combo not enabled", new DefaultCombo(1).isEnabled());
		
		assertFalse("Finish button is enabled", new FinishButton().isEnabled());
	}
	
	@Test
	public void testCreate() {
		fillInWizard();
		wizard.finish();

		ProjectExplorer explorer = new ProjectExplorer();
		Project project = null;
		try {
			project = explorer.getProject(PLUGIN_ID);
		} catch (RedDeerException ex) {
			fail("Cannot retrieve created plug-in project");
		}
		
		checkProjectResources(project);
		checkManifest(project);
		checkExampleTest(project);
	}
	
	private void checkExampleTest(Project project) {
		project.getProjectItem("src", "org.reddeer.test", "RedDeerTest.java").open();
		OutlineView view = new OutlineView();
		view.open();
		
		Collection<TreeItem> outline = view.outlineElements();
		TreeItem testMethod = null;
		
		for (TreeItem item : outline) {
			if (item.getText().equals("RedDeerTest")) {
				for(TreeItem member : item.getItems()) {
					if (member.getText().equals("redDeerTestExample() : void")) {
						testMethod = member;
					}
				}
			}
		}
		
		assertNotNull("Cannot locate example test method", testMethod);
	}

	private void checkManifest(Project project) {
		project.getProjectItem("META-INF", "MANIFEST.MF").open();
		assertTrue(new LabeledText("ID:").getText().equals(PLUGIN_ID));
		assertTrue(new LabeledText("Version:").getText().equals(VERSION));
		assertTrue(new LabeledText("Name:").getText().equals(PLUGIN_NAME));
		assertTrue(new LabeledText("Vendor:").getText().equals(PLUGIN_PROVIDER));
	}
	
	private void checkProjectResources(Project project) {
		assertTrue("Project does not contain build.properties file", project.containsItem("build.properties"));
		assertTrue("Project does not contain pluginCustomization.ini file",
				project.containsItem("pluginCustomization.ini"));
		assertTrue("Project does not contain MANIFEST.MF file",
				project.containsItem("META-INF", "MANIFEST.MF"));
		assertTrue("Project does not contain example test",
				project.containsItem("src", "org.reddeer.test", "RedDeerTest.java"));
		assertTrue("Project does not contain RedDeerTest.launch file", project.containsItem("RedDeerTest.launch"));
	}

	private void fillInWizard() {
		RedDeerTestPluginWizardPage page = new RedDeerTestPluginWizardPage();
		page.setPluginName(PLUGIN_NAME);
		page.setPluginId(PLUGIN_ID);
		page.setVersion(VERSION);
		page.setProvider(PLUGIN_PROVIDER);
		
		page.setApplication(true);
		assertTrue("Application combo was not enabled", new DefaultCombo(1).isEnabled());
		page.selectApplication(APPLICATION);
		
		page.toggleExampleTest(true);
		assertTrue("'Example test' checkbox was not checked", new CheckBox().isChecked());
		
		page.setProduct(true);
		assertFalse("Application combo was not disabled", new DefaultCombo(1).isEnabled());
		assertTrue("Product combo was not enabled", new DefaultCombo().isEnabled());
		page.selectProduct(PRODUCT);
		
		assertTrue("Finish button is not enabled", new FinishButton().isEnabled());
	}
	
}
