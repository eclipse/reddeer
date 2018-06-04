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

package org.eclipse.reddeer.ui.test.wizard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;

import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;
import org.eclipse.reddeer.eclipse.ui.markers.matcher.MarkerResourceMatcher;
import org.eclipse.reddeer.eclipse.ui.problems.Problem;
import org.eclipse.reddeer.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.reddeer.eclipse.ui.views.markers.ProblemsView;
import org.eclipse.reddeer.eclipse.ui.views.markers.ProblemsView.ProblemType;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.button.FinishButton;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.ui.test.wizard.impl.RedDeerTestPluginWizard;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RedDeerTestPluginWizardTest extends RedDeerWizardTestCase {

	private static final String BUNDLE_REDDEER_GO = "org.eclipse.reddeer.go";
	private static final String BUNDLE_ORG_JUNIT = "org.junit";
	protected static NewMenuWizard wizard;

	@Override
	public String getWizardText() {
		return "New RedDeer Test Plugin";
	}
	
	@Before
	public void open() {
		wizard.open();
	}
	
	@After
	public void closeWizard() {
		closeOpenedWizard(wizard);
	}
	
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
		fillInWizard(wizard);
		wizard.finish();
		
		DefaultProject project = getProject(PLUGIN_ID);
		
		checkProjectResources(project);
		checkManifest(project);
		checkExampleTest(project);
		checkForCompilationErrors(project);
	}
	
	private void checkExampleTest(DefaultProject project) {
		project.getProjectItem(EXAMPLE_TEST_CLASS_PATH).open();
		ContentOutline view = new ContentOutline();
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

	private void checkManifest(DefaultProject project) {
		project.getProjectItem("META-INF", "MANIFEST.MF").open();
		assertTrue(new LabeledText("ID:").getText().equals(PLUGIN_ID));
		assertTrue(new LabeledText("Version:").getText().equals(VERSION));
		assertTrue(new LabeledText("Name:").getText().equals(PLUGIN_NAME));
		assertTrue(new LabeledText("Vendor:").getText().equals(PLUGIN_PROVIDER));
		ContentOutline outline = new ContentOutline();
		outline.open();
		for (TreeItem item : outline.outlineElements()) {
			if (item.getText().equals("Dependencies")) {
				item.expand();
				assertNotNull(item.getItem(BUNDLE_REDDEER_GO));
				assertNotNull(item.getItem(BUNDLE_ORG_JUNIT));
			}
		}
	}
	
	private void checkProjectResources(DefaultProject project) {
		assertTrue("Project does not contain build.properties file", project.containsResource("build.properties"));
		assertTrue("Project does not contain pluginCustomization.ini file",
				project.containsResource("pluginCustomization.ini"));
		assertTrue("Project does not contain MANIFEST.MF file",
				project.containsResource("META-INF", "MANIFEST.MF"));
		assertTrue("Project does not contain example test",
				project.containsResource("src", "org.reddeer.test", "RedDeerTest.java"));
		assertTrue("Project does not contain RedDeerTest.launch file", project.containsResource("RedDeerTest.launch"));
	}

	private void checkForCompilationErrors(DefaultProject project) {
		project.getProjectItem(EXAMPLE_TEST_CLASS_PATH).open();
		ProblemsView view = new ProblemsView();
		view.open();
		List<Problem> problems = view.getProblems(ProblemType.ERROR, new MarkerResourceMatcher(EXAMPLE_TEST_CLASS_NAME + ".java"));
		if (!problems.isEmpty()) {
			String errors = "";
			for (Problem problem : problems) {
				errors = errors.concat(problem.toString() + "\r\n");
			}
			fail("There are compilation errors in " + EXAMPLE_TEST_CLASS_JAVA_NAME + " class: " + errors);
		}
	}
	
}
