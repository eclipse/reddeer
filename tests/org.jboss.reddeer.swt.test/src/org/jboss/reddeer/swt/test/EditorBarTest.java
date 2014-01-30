package org.jboss.reddeer.swt.test;

import static org.junit.Assert.*;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.impl.toolbar.ViewToolItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.test.ui.editor.EditorState;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.editor.DefaultEditor;
import org.junit.Before;
import org.junit.Test;

public class EditorBarTest extends RedDeerTest {

		@Before
		public void prepare() {
			// new DefaultShell("Spring Tool Tips").close();
			new WorkbenchShell();
			new ShellMenu("File","New","Other...").select();
			new WaitUntil(new ShellWithTextIsActive("New"));
			new DefaultTreeItem("General","Project").select();
			new PushButton("Next >").click();
			new WaitUntil(new ShellWithTextIsActive("New Project"));
			new LabeledText("Project name:").setText("test");
			new PushButton("Finish").click();
			
			ProjectExplorer pe = new ProjectExplorer();
			pe.open();
			pe.selectProjects("test");


			new ShellMenu("File","New","File").select();
			new WaitUntil(new ShellWithTextIsActive("New File"));
			new LabeledText("File name:").setText("test.tlb");
			new PushButton("Finish").click();
			new WaitWhile(new ShellWithTextIsActive("New File"));
			new DefaultEditor("editor-with-toolbar");
		}
		
		
		@Test 
		public void workbenchToolBarTest() {
			DefaultToolItem ti = new DefaultToolItem("Execute task");
			ti.click();	
			assertTrue("ToolItem was not clicked",EditorState.isExecuted());
		}
		
		
	}
