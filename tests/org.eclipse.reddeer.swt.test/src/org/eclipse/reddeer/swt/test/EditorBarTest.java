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
package org.eclipse.reddeer.swt.test;

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.swt.test.ui.editor.EditorState;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.workbench.impl.editor.DefaultEditor;
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
		new ShellMenuItem("File","New","Other...").select();
		new DefaultShell("New");
		new DefaultTreeItem("General","Project").select();
		new PushButton("Next >").click();
		Shell projectShell = new DefaultShell("New Project");
		new LabeledText("Project name:").setText(projectName);
		new PushButton("Finish").click();
		
		new WaitWhile(new ShellIsAvailable(projectShell), TimePeriod.LONG);
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		pe.selectProjects(projectName);


		new ShellMenuItem("File","New","File").select();
		Shell fileShell = new DefaultShell("New File");
		new LabeledText("File name:").setText("test.tlb");
		new PushButton("Finish").click();
		new WaitWhile(new ShellIsAvailable(fileShell));
		new DefaultEditor("editor-with-toolbar");
	}

	@After
	public void deleteProject(){
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		DeleteUtils.forceProjectDeletion(pe.getProject(projectName),true);
	}

	@Test 
	public void workbenchToolBarTest() {
		DefaultToolItem ti = new DefaultToolItem("Execute task");
		ti.click();	
		assertTrue("ToolItem was not clicked",EditorState.isExecuted());
	}


}
