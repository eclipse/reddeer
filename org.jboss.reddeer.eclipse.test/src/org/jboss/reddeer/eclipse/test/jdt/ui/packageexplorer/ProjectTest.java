package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.Project;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProjectTest {

	private static final String PROJECT_NAME = "TestProject";
	private PackageExplorer packageExplorer;
	private Project project;
		
	@Before
	public void setup(){
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = new NewJavaProjectWizardPage(dialog); 
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
	}
	
	@After
	public void teardown(){
		if (packageExplorer.containsProject(ProjectTest.PROJECT_NAME)){
			packageExplorer.getProject(ProjectTest.PROJECT_NAME).delete(true);
		}
	}
}
