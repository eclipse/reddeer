package org.jboss.reddeer.swt.test.impl.menu;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContextMenuTest {
	
	private static String projectName = "test";
	
	@BeforeClass
	public static void createProject() throws InterruptedException{
		new ShellMenu("Window","Open Perspective","Other...").select();
		new WaitUntil(new ShellWithTextIsActive("Open Perspective"),TimePeriod.NORMAL);
		new DefaultTable().select("Java");
		new PushButton("OK").click();
		new ShellMenu("File","New","Other...").select();
		new WaitUntil(new ShellWithTextIsActive("New"),TimePeriod.NORMAL);
		new DefaultTreeItem("General","Project").select();
		new PushButton("Next >").click();
		new WaitUntil(new ShellWithTextIsActive("New Project"),TimePeriod.NORMAL);
		new LabeledText("Project name:").setText(projectName);
		new PushButton("Finish").click();
		Thread.sleep(1000);
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
		new WaitUntil(new ShellWithTextIsActive("Create new POM"),TimePeriod.NORMAL);
		new PushButton("Cancel").click();
	}
}
