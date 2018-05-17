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
package org.eclipse.reddeer.gef.test;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;

import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.gef.editor.GEFEditor;
import org.eclipse.reddeer.gef.test.wizard.ExampleWizard;
import org.eclipse.reddeer.gef.test.wizard.GeneralProjectWizard;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.eclipse.reddeer.swt.api.Menu;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.junit.After;
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
public class GEFEditorTest {

	public static final String PROJECT_NAME = "test";

	@BeforeClass
	public static void maximizeWorkbenchShell() {
		new WorkbenchShell().maximize();
	}

	@After
	public void deleteAllProjects() {
		new CleanWorkspaceRequirement().fulfill();
	}

	@Test
	public void logicDiagramTest() {
		new GeneralProjectWizard().create(PROJECT_NAME);

		new ProjectExplorer().open();
		new ProjectExplorer().getProject(PROJECT_NAME).select();

		ExampleWizard.createLogicDiagram("test");

		GEFEditor gefEditor = new GEFEditor("test.logic");

		List<String> tools = gefEditor.getPalette().getTools();

		assertContains("Select", tools);
		assertContains("Marquee", tools);
		assertContains("Connection", tools);
		assertContains("Flow Container", tools);
		assertContains("Circuit", tools);
		assertContains("Label", tools);
		assertContains("LED", tools);
		assertContains("Or Gate", tools);
		assertContains("XOR Gate", tools);
		assertContains("And Gate", tools);
		assertContains("V+", tools);
		assertContains("Ground", tools);
		assertContains("HalfAdder", tools);
		assertContains("FullAdder", tools);

		gefEditor.addToolFromPalette("Label", 0, 0);
		gefEditor.addToolFromPalette("LED", 50, 0);
		gefEditor.addToolFromPalette("Or Gate", 100, 0);
		gefEditor.addToolFromPalette("XOR Gate", 150, 0);
		gefEditor.addToolFromPalette("And Gate", 200, 0);

		gefEditor.addToolFromPalette("Label", "Components", 0, 100);
		gefEditor.addToolFromPalette("LED", "Components", 50, 100);
		gefEditor.addToolFromPalette("Or Gate", "Components", 100, 100);
		gefEditor.addToolFromPalette("XOR Gate", "Components", 150, 100);
		gefEditor.addToolFromPalette("And Gate", "Components", 200, 100);

		gefEditor.addToolFromPalette("HalfAdder", "Canned Parts", 100, 200);
		gefEditor.addToolFromPalette("FullAdder", "Canned Parts", 200, 200);

		gefEditor.addToolFromPalette("Flow Container", 100, 300);
		gefEditor.addToolFromPalette("LED", 105, 305);
		gefEditor.save();

		Menu contextMenu = gefEditor.getContextMenu();
		contextMenu.getItem("Undo Create Object").select();
	}

	@Test
	public void shapesDiagramTest() {
		new GeneralProjectWizard().create(PROJECT_NAME);

		new ProjectExplorer().open();
		new ProjectExplorer().getProject(PROJECT_NAME).select();

		ExampleWizard.createShapesDiagram("test");

		GEFEditor gefEditor = new GEFEditor("test.shapes");

		List<String> tools = gefEditor.getPalette().getTools();

		assertContains("Select", tools);
		assertContains("Marquee", tools);
		assertContains("Solid connection", tools);
		assertContains("Dashed connection", tools);
		assertContains("Ellipse", tools);
		assertContains("Rectangle", tools);

		gefEditor.addToolFromPalette("Ellipse", 0, 0);
		gefEditor.addToolFromPalette("Rectangle", 100, 0);
		gefEditor.addToolFromPalette("Ellipse", "Shapes", 0, 200);
		gefEditor.addToolFromPalette("Rectangle", "Shapes", 100, 200);
		gefEditor.save();

		Menu contextMenu = gefEditor.getContextMenu();
		contextMenu.getItem("Undo shape creation").select();
	}

	private static void assertContains(String text, Collection<String> collection) {
		if (!collection.contains(text)) {
			fail("Collection " + collection + " doesn't contain '" + text + "'");
		}
	}

}
