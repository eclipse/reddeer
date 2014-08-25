package org.jboss.reddeer.eclipse.test.ui.views.contentoutline;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.core.Is;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.views.contentoutline.OutlineView;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.WorkbenchHandler;
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
		WorkbenchHandler.getInstance().closeAllEditors();
		
		outlineView = new OutlineView();
		outlineView.open();
		assertThat(outlineView.outlineElements().size(), Is.is(0));
	}
	
	@Test(expected=SWTLayerException.class)
	public void testCollapseInEmptyOutlineView() {
		WorkbenchHandler.getInstance().closeAllEditors();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.collapseAll();
	}
	
	@Test(expected=SWTLayerException.class)
	public void testSortInEmptyOutlineView() {
		WorkbenchHandler.getInstance().closeAllEditors();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=SWTLayerException.class)
	public void testHideFieldsInEmptyOutlineView() {
		WorkbenchHandler.getInstance().closeAllEditors();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=SWTLayerException.class)
	public void testHideStaticFieldsAndMethodsInEmptyOutlineView() {
		WorkbenchHandler.getInstance().closeAllEditors();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=SWTLayerException.class)
	public void testHideNonPublicMembersInEmptyOutlineView() {
		WorkbenchHandler.getInstance().closeAllEditors();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=SWTLayerException.class)
	public void testHideLocalTypesInEmptyOutlineView() {
		WorkbenchHandler.getInstance().closeAllEditors();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=SWTLayerException.class)
	public void testLinkWithEditorInEmptyOutlineView() {
		WorkbenchHandler.getInstance().closeAllEditors();
		
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
	
	@Test(expected=SWTLayerException.class)
	public void testLinkWithEditorInNonEmptyOutlineView() {
		openTestClass();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.linkWithEditor();
	}
	

	private static void createJavaProject() {
		NewJavaProjectWizardDialog javaProject = new NewJavaProjectWizardDialog();
		javaProject.open();
		
		NewJavaProjectWizardPage javaWizardPage = javaProject.getFirstPage();
		javaWizardPage.setProjectName(TEST_PROJECT_NAME);
		
		javaProject.finish(false);
	}
	
	private static void createJavaClass() {
		NewJavaClassWizardDialog javaClassDialog = new NewJavaClassWizardDialog();
		javaClassDialog.open();
		
		NewJavaClassWizardPage wizardPage = javaClassDialog.getFirstPage();
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
