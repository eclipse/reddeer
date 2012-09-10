package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.ProjectItem;
import org.jboss.reddeer.eclipse.ui.ide.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.ui.ide.NewJavaClassWizardPage;
import org.jboss.reddeer.swt.condition.AllRunningJobsAreNotActive;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProjectItemTest {

	private static final String PROJECT_NAME = "TestProject";
	private static final String PROJECT_ITEM_TEXT = "src";
	private PackageExplorer packageExplorer;
	private ProjectItem projectItem;
		
	@Before
	public void setup(){
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
		projectItem.delete(true);
		assertFalse("Project " + ProjectItemTest.PROJECT_NAME + " contains project item " + ProjectItemTest.PROJECT_ITEM_TEXT +
				" but it should be deleted.",
			packageExplorer.getProject(ProjectItemTest.PROJECT_NAME).containsItem(ProjectItemTest.PROJECT_NAME));
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
		new WaitUntil(new AllRunningJobsAreNotActive(), TimePeriod.LONG);
		Bot.get().closeAllEditors();
		final String javaClassFileName = javaClassName + ".java";
		packageExplorer.getProject(ProjectItemTest.PROJECT_NAME)
          .getProjectItem(ProjectItemTest.PROJECT_ITEM_TEXT,"(default package)",javaClassFileName)
          .open();
		assertTrue("Active Editor has to have title " + javaClassFileName,
			Bot.get().activeEditor().getTitle().equals(javaClassFileName));
	}
	@After
	public void teardown(){
		packageExplorer.getProject(ProjectItemTest.PROJECT_NAME).delete(true);
	}
}
