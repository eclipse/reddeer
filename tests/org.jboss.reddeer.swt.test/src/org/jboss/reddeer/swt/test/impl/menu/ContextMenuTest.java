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
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.test.utils.ShellTestUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class ContextMenuTest {

	private static int limit = 20;
	private static String projectName = "ContextMenuTest-test";
	
	@BeforeClass
	public static void createProject() {
		NewJavaProjectWizardDialog projectWizard = new NewJavaProjectWizardDialog();
		projectWizard.open();
		new NewJavaProjectWizardPage().setProjectName(projectName);
		projectWizard.finish();
		
		NewJavaClassWizardDialog classWizard = new NewJavaClassWizardDialog();
		classWizard.open();
		NewJavaClassWizardPage page = new NewJavaClassWizardPage();
		page.setName("TestClass");
		page.setStaticMainMethod(true);
		classWizard.finish();
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

	@Test
	public void contextMenuTest() {

		ProjectExplorer pe = new ProjectExplorer();
		pe.open();

		org.jboss.reddeer.swt.api.Menu menu = new ContextMenu("New", "Project...");
		menu.select();
		org.jboss.reddeer.swt.api.Shell s = new DefaultShell("New Project");
		s.close();
	}

	@Test
	public void hundertscontextMenuTest() {
		for (int i = 0; i < limit; i++) {
			contextMenuTest();
		}
	}

	@Test
	public void contextMenuItemTextTest() {
		// make sure shell is focused
		new DefaultShell();

		ProjectExplorer pe = new ProjectExplorer();
		pe.open();

		org.jboss.reddeer.swt.api.Menu menu = new ContextMenu("New", "Project...");
		assertTrue("Menuitem text not expected to be empty", !menu.getText().equals(""));
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
