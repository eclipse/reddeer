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

	private static final String PROJECT_NAME = "TestProject";
	private PackageExplorer packageExplorer;
	private Project project;
		
	@Override
	protected void setUp(){
		super.setUp();
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = dialog.getFirstPage(); 
		page1.setProjectName(ProjectTest.PROJECT_NAME);
		dialog.finish();
		packageExplorer = new PackageExplorer();
		project = packageExplorer.getProject(ProjectTest.PROJECT_NAME);
	}

	@Test
	public void select(){
		project.select();
		assertTrue("Project is not selected" , project.isSelected());
	}
	
	@Test
	public void delete(){
		project.delete(true);
		assertFalse("Package Explorer contains project " + ProjectTest.PROJECT_NAME +
				" but it should be deleted.",
			packageExplorer.containsProject(ProjectTest.PROJECT_NAME));
		/*super.tearDown();*/
	}
	
	@Override
	protected void tearDown(){
		if (packageExplorer.containsProject(ProjectTest.PROJECT_NAME)){
			packageExplorer.getProject(ProjectTest.PROJECT_NAME).delete(true);
		}
		super.tearDown();
	}
}
