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
package org.eclipse.reddeer.gef.test;

import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.gef.GEFLayerException;
import org.eclipse.reddeer.gef.editor.GEFEditor;
import org.eclipse.reddeer.gef.impl.connection.DefaultConnection;
import org.eclipse.reddeer.gef.impl.editpart.DefaultEditPart;
import org.eclipse.reddeer.gef.impl.editpart.LabeledEditPart;
import org.eclipse.reddeer.gef.test.wizard.ExampleWizard;
import org.eclipse.reddeer.gef.test.wizard.GeneralProjectWizard;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
@RunWith(RedDeerSuite.class)
@CleanWorkspace
public class EditPartTest {

	public static final String PROJECT_NAME = "test";

	@BeforeClass
	public static void maximizeWorkbenchShell() {
		new WorkbenchShell().maximize();
	}

	@Before
	public void createProject() {
		new GeneralProjectWizard().create(PROJECT_NAME);
	}

	@After
	public void deleteAllProjects() {
		new CleanWorkspaceRequirement().fulfill();
	}

	@Test
	public void selectEditPartTest() {
		new ProjectExplorer().open();
		new ProjectExplorer().getProject(PROJECT_NAME).select();

		ExampleWizard.createLogicDiagram("test");

		GEFEditor gefEditor = new GEFEditor("test.logic");
		gefEditor.addToolFromPalette("Label", 0, 0);
		gefEditor.addToolFromPalette("Label", 0, 100);

		new LabeledEditPart("Label", 0).select();
		new LabeledEditPart("Label", 1).select();
		new LabeledEditPart("Label").setLabel("Foo");
		new LabeledEditPart("Label").setLabel("Hello");
		new LabeledEditPart("Foo").select();
		new LabeledEditPart("Hello").select();
	}

	@Test
	public void clickEditPartTest() {
		new ProjectExplorer().open();
		new ProjectExplorer().getProject(PROJECT_NAME).select();

		ExampleWizard.createLogicDiagram("test");

		GEFEditor gefEditor = new GEFEditor("test.logic");
		gefEditor.addToolFromPalette("LED", 0, 100);
		gefEditor.addToolFromPalette("LED", 100, 100);

		gefEditor.getPalette().activateTool("Connection");
		new DefaultEditPart("LEDEditPart", 0).click();
		new DefaultEditPart("LEDEditPart", 1).click();

		gefEditor.getPalette().activateTool("Select");
		new DefaultConnection(0).select();
		new ContextMenuItem("Delete").select();

		try {
			new DefaultConnection(0).select();
		} catch (GEFLayerException e) {
			// ok, this is expected
		}
	}

	@Test(expected = GEFLayerException.class)
	public void nonExistingEditPartTest() {
		new ProjectExplorer().open();
		new ProjectExplorer().getProject(PROJECT_NAME).select();

		ExampleWizard.createLogicDiagram("test");

		GEFEditor gefEditor = new GEFEditor("test.logic");
		gefEditor.addToolFromPalette("Label", 0, 0);
		gefEditor.addToolFromPalette("Label", 0, 100);

		new LabeledEditPart("Label", 0).select();
		new LabeledEditPart("Label", 1).select();
		new LabeledEditPart("Label").setLabel("Foo");
		new LabeledEditPart("Label").setLabel("Hello");
		new LabeledEditPart("Foo").select();
		new LabeledEditPart("Hello").select();
		new ContextMenuItem("Delete").select();
		new LabeledEditPart("Hello").select();
	}

}
