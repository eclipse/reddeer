package org.jboss.reddeer.gef.test;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.gef.GEFLayerException;
import org.jboss.reddeer.gef.editor.GEFEditor;
import org.jboss.reddeer.gef.impl.connection.DefaultConnection;
import org.jboss.reddeer.gef.impl.editpart.DefaultEditPart;
import org.jboss.reddeer.gef.impl.editpart.LabeledEditPart;
import org.jboss.reddeer.gef.test.wizard.ExampleWizard;
import org.jboss.reddeer.gef.test.wizard.GeneralProjectWizard;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
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
		new GEFEditor().close();
		ProjectExplorer projectExplorer = new ProjectExplorer();
		projectExplorer.open();
		projectExplorer.getProject(PROJECT_NAME).delete(true);
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
		new ContextMenu("Delete").select();

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
		new ContextMenu("Delete").select();
		new LabeledEditPart("Hello").select();
	}

}
