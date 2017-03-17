/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.test.ui.editor.EditorState;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;
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


		new ShellMenu("File","New","File").select();
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
