package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.ProjectItem;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.handler.WorkbenchHandler;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.editor.DefaultEditor;
import org.junit.Test;

public class ProjectItemTest extends RedDeerTest{

	private static final String PROJECT_NAME = "TestProject";
	private static final String PROJECT_ITEM_TEXT = "src";
	private static final String DEFAULT_PACKAGE_TEXT = "(default package)";
	private PackageExplorer packageExplorer;
	private ProjectItem projectItem;
		
	@Override
	protected void setUp(){
	  super.setUp();
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = dialog.getFirstPage(); 
		page1.setProjectName(ProjectItemTest.PROJECT_NAME);
		dialog.finish();
		packageExplorer = new PackageExplorer();
		projectItem = packageExplorer.getProject(ProjectItemTest.PROJECT_NAME)
			              .getProjectItem(ProjectItemTest.PROJECT_ITEM_TEXT);
	}

	@Test
	public void select(){
		projectItem.select();
		assertTrue("Project item " + ProjectItemTest.PROJECT_ITEM_TEXT + " is not selected" , projectItem.isSelected());
	}
	
	@Test
	public void delete(){
		projectItem.delete();
		assertFalse("Project " + ProjectItemTest.PROJECT_NAME + " contains project item " + ProjectItemTest.PROJECT_ITEM_TEXT +
				" but it should be deleted.",
			packageExplorer.getProject(ProjectItemTest.PROJECT_NAME).containsItem(ProjectItemTest.PROJECT_ITEM_TEXT));
	}
	
	@Test
	public void open(){
		packageExplorer.getProject(ProjectItemTest.PROJECT_NAME)
            .getProjectItem(ProjectItemTest.PROJECT_ITEM_TEXT)
            .select();
		NewJavaClassWizardDialog newJavaClassDialog = new NewJavaClassWizardDialog();
		newJavaClassDialog.open();
		
		NewJavaClassWizardPage wizardPage = newJavaClassDialog.getFirstPage();
		final String javaClassName = "TestClass";
		wizardPage.setName(javaClassName);
		newJavaClassDialog.finish();
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		WorkbenchHandler.getInstance().closeAllEditors();
		final String javaClassFileName = javaClassName + ".java";
		packageExplorer.getProject(ProjectItemTest.PROJECT_NAME)
          .getProjectItem(ProjectItemTest.PROJECT_ITEM_TEXT,ProjectItemTest.DEFAULT_PACKAGE_TEXT,javaClassFileName)
          .open();
		assertTrue("Active Editor has to have title " + javaClassFileName,
			new DefaultEditor().getTitle().equals(javaClassFileName));
	}
	@Test
	public void getChild(){
		packageExplorer.getProject(ProjectItemTest.PROJECT_NAME)
            .getProjectItem(ProjectItemTest.PROJECT_ITEM_TEXT)
            .select();
		NewJavaClassWizardDialog newJavaClassDialog = new NewJavaClassWizardDialog();
		newJavaClassDialog.open();
		
		NewJavaClassWizardPage wizardPage = newJavaClassDialog.getFirstPage();
		final String javaClassName = "TestClass";
		wizardPage.setName(javaClassName);
		newJavaClassDialog.finish();
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		ProjectItem piDefaultPackage = packageExplorer.getProject(ProjectItemTest.PROJECT_NAME)
          .getProjectItem(ProjectItemTest.PROJECT_ITEM_TEXT)
          .getChild(ProjectItemTest.DEFAULT_PACKAGE_TEXT);
          
		assertTrue("Found Project Item has to have text " + ProjectItemTest.DEFAULT_PACKAGE_TEXT
				  + " but is " + piDefaultPackage.getText(),
				piDefaultPackage.getText().equals(ProjectItemTest.DEFAULT_PACKAGE_TEXT));
	}
	@Override
	protected void tearDown(){
		packageExplorer.getProject(ProjectItemTest.PROJECT_NAME).delete(true);
		super.tearDown();
	}
}
