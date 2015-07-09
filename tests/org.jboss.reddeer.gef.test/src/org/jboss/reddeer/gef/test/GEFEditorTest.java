package org.jboss.reddeer.gef.test;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.gef.editor.GEFEditor;
import org.jboss.reddeer.gef.test.wizard.ExampleWizard;
import org.jboss.reddeer.gef.test.wizard.GeneralProjectWizard;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.workbench.impl.shell.WorkbenchShell;
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
public class GEFEditorTest {

	public static final String PROJECT_NAME = "test";

	@BeforeClass
	public static void maximizeWorkbenchShell() {
		new WorkbenchShell().maximize();
	}

	@After
	public void deleteAllProjects() {
		new GEFEditor().close();
		ProjectExplorer projectExplorer = new ProjectExplorer();
		projectExplorer.open();
		DeleteUtils.forceProjectDeletion(projectExplorer.getProject(PROJECT_NAME),true);
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
		
		gefEditor.addToolFromPalette("Flow Container", 100, 200);
		gefEditor.addToolFromPalette("LED", 105, 205);
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
	}

	private static void assertContains(String text, Collection<String> collection) {
		if (!collection.contains(text)) {
			fail("Collection " + collection + " doesn't contain '" + text + "'");
		}
	}

}
