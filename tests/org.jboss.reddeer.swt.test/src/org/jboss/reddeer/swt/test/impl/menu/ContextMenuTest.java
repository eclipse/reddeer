package org.jboss.reddeer.swt.test.impl.menu;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ContextMenuTest {
	
	private static String projectName = "ContextMenuTest-test";
	
	@BeforeClass
	public static void createProject() throws InterruptedException{
		new ShellMenu("Window","Open Perspective","Other...").select();
		new DefaultShell("Open Perspective");
		new DefaultTable().select("Java");
		new PushButton("OK").click();
		new ShellMenu("File","New","Other...").select();
		new DefaultShell("New");
		new DefaultTreeItem("Java","Java Project").select();
		new PushButton("Next >").click();
		new DefaultShell("New Java Project");
		new LabeledText("Project name:").setText(projectName);
		new PushButton("Finish").click();
		new WaitWhile(new ShellWithTextIsActive("New Java Project"));
		new ShellMenu("File","New","Other...").select();
		new DefaultShell("New");
		new DefaultTreeItem("Java","Class").select(); 
		new PushButton("Next >").click();
		new DefaultShell("New Java Class");
		new LabeledText("Name:").setText("TestClass");
		new CheckBox("public static void main(String[] args)").toggle(true);
		new PushButton("Finish").click();
		new WaitWhile(new ShellWithTextIsActive("New Java Class"));
		
	}
	
	@AfterClass
	public static void deleteProject(){
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		DeleteUtils.forceProjectDeletion(pe.getProject(projectName),true);
	}
	
	@Test(expected=SWTLayerException.class)
	public void disabledAction() throws InterruptedException {
		PackageExplorer pex = new PackageExplorer();
		pex.open();
		pex.getProject(projectName).select();
		new ContextMenu("Compare With","Each Other").select();
	}
	
	@Test
	public void dynamicEnabledAction() throws InterruptedException{
		PackageExplorer pex = new PackageExplorer();
		pex.open();
		pex.getProject(projectName).select();
		new ContextMenu("Configure","Convert to Maven Project").select();
		new DefaultShell("Create new POM");
		new PushButton("Cancel").click();
	}
	
	@Test
	public void testOpenWithCheck(){
		PackageExplorer pex = new PackageExplorer();
		pex.open();
		pex.getProject(projectName).getProjectItem("src","(default package)","TestClass.java").select();
		new ContextMenu("Open With","Text Editor").select();
		assertTrue(new ContextMenu("Open With","Text Editor").isSelected());
	}
}
