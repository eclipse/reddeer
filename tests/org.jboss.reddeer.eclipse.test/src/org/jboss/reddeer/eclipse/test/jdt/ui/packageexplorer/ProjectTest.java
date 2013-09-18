package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.Project;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.junit.Test;

public class ProjectTest extends RedDeerTest {

	private static final String PROJECT_NAME_0 = "TestProject0";
	private static final String PROJECT_NAME_1 = "TestProject1";
	private static final String PROJECT_NAME_2 = "TestProject2";
	private PackageExplorer packageExplorer;
	private Project project0;
		
	@Override
	protected void setUp(){
		super.setUp();
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = dialog.getFirstPage(); 
		page1.setProjectName(ProjectTest.PROJECT_NAME_0);
		dialog.finish();

		packageExplorer = new PackageExplorer();
		project0 = packageExplorer.getProject(ProjectTest.PROJECT_NAME_0);
	}

	@Test
	public void select() {
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = dialog.getFirstPage();
		page1.setProjectName(ProjectTest.PROJECT_NAME_1);
		dialog.finish();
		dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		page1 = dialog.getFirstPage();
		page1.setProjectName(ProjectTest.PROJECT_NAME_2);
		dialog.finish();
		final Project project1;
		project1 = packageExplorer.getProject(ProjectTest.PROJECT_NAME_1);
		final Project project2;
		project2 = packageExplorer.getProject(ProjectTest.PROJECT_NAME_2);
		project1.select();
		assertTrue("Project " + project1.getName() + " is not selected",
				project1.isSelected());
		assertTrue("Project " + project0.getName() + " is selected",
				!project0.isSelected());
		assertTrue("Project " + project2.getName() + " is selected",
				!project2.isSelected());
	}
	
	@Test
	public void delete(){
		project0.delete(true);
		assertFalse("Package Explorer contains project " + ProjectTest.PROJECT_NAME_0 +
				" but it should be deleted.",
			packageExplorer.containsProject(ProjectTest.PROJECT_NAME_0));
	}
	
	@Override
	protected void tearDown() {
		if (packageExplorer.containsProject(ProjectTest.PROJECT_NAME_0)) {
			packageExplorer.getProject(ProjectTest.PROJECT_NAME_0).delete(true);
		}
		if (packageExplorer.containsProject(ProjectTest.PROJECT_NAME_1)) {
			packageExplorer.getProject(ProjectTest.PROJECT_NAME_1).delete(true);
		}
		if (packageExplorer.containsProject(ProjectTest.PROJECT_NAME_2)) {
			packageExplorer.getProject(ProjectTest.PROJECT_NAME_2).delete(true);
		}
		super.tearDown();
	}
}
