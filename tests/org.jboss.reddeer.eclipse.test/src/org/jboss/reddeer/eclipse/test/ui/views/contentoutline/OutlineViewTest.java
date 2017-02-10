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
package org.jboss.reddeer.eclipse.test.ui.views.contentoutline;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.core.Is;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.jboss.reddeer.eclipse.jdt.ui.wizards.NewClassCreationWizard;
import org.jboss.reddeer.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.jboss.reddeer.eclipse.ui.views.contentoutline.OutlineView;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.workbench.handler.EditorHandler;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class OutlineViewTest{

	private OutlineView outlineView;
	private static PackageExplorer packageExplorer;
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
		
		outlineView = new OutlineView();
		outlineView.open();
		assertThat(outlineView.outlineElements().size(), Is.is(0));
	}
	
	@Test(expected=CoreLayerException.class)
	public void testCollapseInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.collapseAll();
	}
	
	@Test(expected=CoreLayerException.class)
	public void testSortInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=CoreLayerException.class)
	public void testHideFieldsInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=CoreLayerException.class)
	public void testHideStaticFieldsAndMethodsInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=CoreLayerException.class)
	public void testHideNonPublicMembersInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=CoreLayerException.class)
	public void testHideLocalTypesInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=CoreLayerException.class)
	public void testLinkWithEditorInEmptyOutlineView() {
		EditorHandler.getInstance().closeAll(true);
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.linkWithEditor();
	}
	
	@Test
	public void testElementsInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new OutlineView();
		outlineView.open();
		assertThat(outlineView.outlineElements().size(), Is.is(2));
	}
	
	@Test
	public void testCollapseInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.collapseAll();
	}
	
	@Test
	public void testSortInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test
	public void testHideFieldsInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test
	public void testHideStaticFieldsAndMethodsInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test
	public void testHideNonPublicMembersInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test
	public void testHideLocalTypesInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=CoreLayerException.class)
	public void testLinkWithEditorInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.linkWithEditor();
	}
	

	private static void createJavaProject() {
		JavaProjectWizard javaProject = new JavaProjectWizard();
		javaProject.open();
		
		NewJavaProjectWizardPageOne javaWizardPage = new NewJavaProjectWizardPageOne();
		javaWizardPage.setProjectName(TEST_PROJECT_NAME);
		
		javaProject.finish();
	}
	
	private static void createJavaClass() {
		packageExplorer = new PackageExplorer();
		packageExplorer.open();
		packageExplorer.getProject(TEST_PROJECT_NAME).select();
		NewClassCreationWizard javaClassDialog = new NewClassCreationWizard();
		javaClassDialog.open();
		
		NewClassWizardPage wizardPage = new NewClassWizardPage();
		wizardPage.setName("TestClass");
		wizardPage.setPackage("test");
		wizardPage.setStaticMainMethod(true);
		javaClassDialog.finish();
	}
	
	private void openTestClass() {
		packageExplorer = new PackageExplorer();
		packageExplorer.open();
		packageExplorer.getProject(TEST_PROJECT_NAME).getProjectItem(
				"src","test","TestClass.java").open();
	}
	
}
