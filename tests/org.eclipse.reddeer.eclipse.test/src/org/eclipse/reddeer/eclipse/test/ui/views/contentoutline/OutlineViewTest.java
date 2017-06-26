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
package org.eclipse.reddeer.eclipse.test.ui.views.contentoutline;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.core.Is;
import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassCreationWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.workbench.handler.EditorHandler;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class OutlineViewTest{

	private ContentOutline outlineView;
	private static PackageExplorerPart packageExplorer;
	private static final String TEST_PROJECT_NAME = "OutlineViewTestProject";
	
	@BeforeClass
	public static void prepareWS() {
		createJavaProject();
		createJavaClass();
	}
	
	@AfterClass
	public static void cleanup() {
		DeleteUtils.forceProjectDeletion(packageExplorer.getProject(TEST_PROJECT_NAME),true);
	}
	
	@Test
	public void testElementsInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new ContentOutline();
		outlineView.open();
		assertThat(outlineView.outlineElements().size(), Is.is(0));
	}
	
	@Test(expected=CoreLayerException.class)
	public void testCollapseInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new ContentOutline();
		outlineView.open();
		outlineView.collapseAll();
	}
	
	@Test(expected=CoreLayerException.class)
	public void testSortInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new ContentOutline();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=CoreLayerException.class)
	public void testHideFieldsInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new ContentOutline();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=CoreLayerException.class)
	public void testHideStaticFieldsAndMethodsInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new ContentOutline();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=CoreLayerException.class)
	public void testHideNonPublicMembersInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new ContentOutline();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=CoreLayerException.class)
	public void testHideLocalTypesInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new ContentOutline();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=CoreLayerException.class)
	public void testLinkWithEditorInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new ContentOutline();
		outlineView.open();
		outlineView.linkWithEditor();
	}
	
	@Test
	public void testElementsInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new ContentOutline();
		outlineView.open();
		assertThat(outlineView.outlineElements().size(), Is.is(2));
	}
	
	@Test
	public void testCollapseInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new ContentOutline();
		outlineView.open();
		outlineView.collapseAll();
	}
	
	@Test
	public void testSortInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new ContentOutline();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test
	public void testHideFieldsInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new ContentOutline();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test
	public void testHideStaticFieldsAndMethodsInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new ContentOutline();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test
	public void testHideNonPublicMembersInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new ContentOutline();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test
	public void testHideLocalTypesInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new ContentOutline();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=CoreLayerException.class)
	public void testLinkWithEditorInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new ContentOutline();
		outlineView.open();
		outlineView.linkWithEditor();
	}
	

	private static void createJavaProject() {
		JavaProjectWizard javaProject = new JavaProjectWizard();
		javaProject.open();
		
		NewJavaProjectWizardPageOne javaWizardPage = new NewJavaProjectWizardPageOne(javaProject);
		javaWizardPage.setProjectName(TEST_PROJECT_NAME);
		
		javaProject.finish();
	}
	
	private static void createJavaClass() {
		packageExplorer = new PackageExplorerPart();
		packageExplorer.open();
		packageExplorer.getProject(TEST_PROJECT_NAME).select();
		NewClassCreationWizard javaClassDialog = new NewClassCreationWizard();
		javaClassDialog.open();
		
		NewClassWizardPage wizardPage = new NewClassWizardPage(javaClassDialog);
		wizardPage.setName("TestClass");
		wizardPage.setPackage("test");
		wizardPage.setStaticMainMethod(true);
		javaClassDialog.finish();
	}
	
	private void openTestClass() {
		packageExplorer = new PackageExplorerPart();
		packageExplorer.open();
		packageExplorer.getProject(TEST_PROJECT_NAME).getProjectItem(
				"src","test","TestClass.java").open();
	}
	
}
