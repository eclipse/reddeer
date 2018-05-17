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
package org.eclipse.reddeer.graphiti.test;

import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.gef.GEFLayerException;
import org.eclipse.reddeer.gef.editor.GEFEditor;
import org.eclipse.reddeer.graphiti.impl.graphitieditpart.LabeledGraphitiEditPart;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class LabeledGraphitiEditPartTest {

	public static final String PROJECT_NAME = "test";

	@BeforeClass
	public static void maximizeWorkbenchShell() {
		new WorkbenchShell().maximize();
	}

	@Before
	public void createProject() {
		new GeneralProjectWizard().create(PROJECT_NAME);
		new ProjectExplorer().open();
		new ProjectExplorer().getProject("test").select();
		new TutorialDiagramWizard().create("test");
	}

	@After
	public void deleteAllProjects() {
		new GEFEditor().close();
		ProjectExplorer projectExplorer = new ProjectExplorer();
		projectExplorer.open();
		DeleteUtils.forceProjectDeletion(projectExplorer.getProject(PROJECT_NAME),true);
	}

	@Test(expected = GEFLayerException.class)
	public void contextButtonTest() {
		GEFEditor gefEditor = new GEFEditor("test");
		gefEditor.addToolFromPalette("EClass", 50, 100).setLabel("ClassA");
		gefEditor.addToolFromPalette("EClass", 200, 100).setLabel("ClassB");

		new LabeledGraphitiEditPart("ClassA").getContextButton("Delete").click();
		new DefaultShell("Confirm Delete").setFocus();
		new PushButton("Yes").click();

		new LabeledGraphitiEditPart("ClassA").select();
	}

	@Test
	public void doubleClickTest() {
		GEFEditor gefEditor = new GEFEditor("test");
		gefEditor.addToolFromPalette("EClass", 50, 100).setLabel("ClassA");
		gefEditor.addToolFromPalette("EClass", 200, 100).setLabel("ClassB");

		new LabeledGraphitiEditPart("ClassA").doubleClick();
		new DefaultShell("Rename EClass").setFocus();
		new PushButton("Cancel").click();
	}

	public class GeneralProjectWizard extends BasicNewProjectResourceWizard {

		public void create(String name) {
			open();
			new LabeledText("Project name:").setText(name);
			finish();
		}
	}

	public class TutorialDiagramWizard extends NewMenuWizard {

		public TutorialDiagramWizard() {
			super("New Diagram", "Other", "Graphiti Example Diagram");
		}

		public void create(String name) {
			open();
			new LabeledCombo("Diagram Type").setSelection("tutorial");
			next();
			new LabeledText("Diagram Name").setText(name);
			finish();
		}
	}
}
