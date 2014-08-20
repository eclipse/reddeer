package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.test.ui.editor.EditorState;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class EditorBarTest {

	private static String projectName = "EditorBarTest-test";
	
	@Before
	public void prepare() {
		// new DefaultShell("Spring Tool Tips").close();
		new WorkbenchShell();
		new ShellMenu("File","New","Other...").select();
		new WaitUntil(new ShellWithTextIsActive("New"));
		new DefaultTreeItem("General","Project").select();
		new PushButton("Next >").click();
		new WaitUntil(new ShellWithTextIsActive("New Project"));
		new LabeledText("Project name:").setText(projectName);
		new PushButton("Finish").click();
		
		new WaitWhile(new ShellWithTextIsActive("New Project"), TimePeriod.LONG);
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		pe.selectProjects(projectName);


		new ShellMenu("File","New","File").select();
		new DefaultShell("New File").setFocus();
		new LabeledText("File name:").setText("test.tlb");
		new PushButton("Finish").click();
		new WaitWhile(new ShellWithTextIsActive("New File"));
		new DefaultEditor("editor-with-toolbar");
	}

	@After
	public void deleteProject(){
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		pe.getProject(projectName).delete(true);;
	}

	@Test 
	public void workbenchToolBarTest() {
		DefaultToolItem ti = new DefaultToolItem("Execute task");
		ti.click();	
		assertTrue("ToolItem was not clicked",EditorState.isExecuted());
	}


}
