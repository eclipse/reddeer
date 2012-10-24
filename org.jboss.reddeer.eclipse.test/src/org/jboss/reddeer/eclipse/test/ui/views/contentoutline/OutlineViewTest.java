package org.jboss.reddeer.eclipse.test.ui.views.contentoutline;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.core.Is;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.views.contentoutline.OutlineView;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Bot;
import org.junit.BeforeClass;
import org.junit.Test;

public class OutlineViewTest {

	private OutlineView outlineView;
	private PackageExplorer packageExplorer;
	private static final String TEST_PROJECT_NAME = "prj";
	
	@BeforeClass
	public static void prepareWS() {
		createJavaProject();
		createJavaClass();
	}
	
	@Test
	public void testElementsInEmptyOutlineView() {
		Bot.get().closeAllEditors();
		
		outlineView = new OutlineView();
		outlineView.open();
		assertThat(outlineView.outlineElements().size(), Is.is(0));
	}
	
	@Test(expected=SWTLayerException.class)
	public void testCollapseInEmptyOutlineView() {
		Bot.get().closeAllEditors();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.collapseAll();
	}
	
	@Test(expected=SWTLayerException.class)
	public void testSortInEmptyOutlineView() {
		Bot.get().closeAllEditors();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=SWTLayerException.class)
	public void testHideFieldsInEmptyOutlineView() {
		Bot.get().closeAllEditors();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=SWTLayerException.class)
	public void testHideStaticFieldsAndMethodsInEmptyOutlineView() {
		Bot.get().closeAllEditors();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=SWTLayerException.class)
	public void testHideNonPublicMembersInEmptyOutlineView() {
		Bot.get().closeAllEditors();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=SWTLayerException.class)
	public void testHideLocalTypesInEmptyOutlineView() {
		Bot.get().closeAllEditors();
		
		outlineView = new OutlineView();
		outlineView.open();
		outlineView.sort();
	}
	
	@Test(expected=SWTLayerException.class)
	public void testLinkWithEditorInEmptyOutlineView() {
		Bot.get().closeAllEditors();
		
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
	
	private static void createBuildProperties() {
		/** TODO Implement this method */
	}
	
	private void openTestClass() {
		packageExplorer = new PackageExplorer();
		packageExplorer.open();
		packageExplorer.getProject(TEST_PROJECT_NAME).getProjectItem(
				"src","test","TestClass.java").open();
	}
	
}
