package org.jboss.reddeer.graphiti.test;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.gef.GEFLayerException;
import org.jboss.reddeer.gef.editor.GEFEditor;
import org.jboss.reddeer.graphiti.impl.graphitieditpart.LabeledGraphitiEditPart;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
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
		projectExplorer.getProject(PROJECT_NAME).delete(true);
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

	public class GeneralProjectWizard extends NewWizardDialog {

		public GeneralProjectWizard() {
			super("General", "Project");
		}

		public void create(String name) {
			open();
			new LabeledText("Project name:").setText(name);
			finish();
		}
	}

	public class TutorialDiagramWizard extends NewWizardDialog {

		public TutorialDiagramWizard() {
			super("Other", "Graphiti Example Diagram");
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
