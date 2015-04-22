package org.jboss.reddeer.swt.test.impl.menu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.test.utils.ShellTestUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class ContextMenuTest {
	
	private static String projectName = "ContextMenuTest-test";
	
	@BeforeClass
	public static void createProject() throws InterruptedException{
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
	
	@Test(expected=CoreLayerException.class)
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
	
	@Test
	public void notifyMenuListenerTest(){
		createTestShell();
		
		DefaultText text = new DefaultText();
		new ContextMenu("menuItem1").select();
		assertEquals("Notified", text.getText());
		
		closeTestShell();
	}

	private void closeTestShell() {
		new DefaultShell("myShell").close();
	}

	private void createTestShell() {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = ShellTestUtils.createShell("myShell");
				createControls(shell);
				shell.layout();
			}
		});
	}
	
	private void createControls(Shell shell){
		final Text text = new Text(shell, 0);
		text.setText("Test");
		text.setSize(100, 100);
		final Menu menu = new Menu(shell, SWT.POP_UP);
		MenuItem menuItem1 = new MenuItem(menu, SWT.PUSH);
		menuItem1.setText("menuItem1");
		menuItem1.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new DefaultText().setText("Notified");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// nothing
			}
		});
		text.addMenuDetectListener(new MenuDetectListener() {
			
			@Override
			public void menuDetected(MenuDetectEvent arg0) {
				text.setMenu(menu);
			}
		});
	}
	
}
