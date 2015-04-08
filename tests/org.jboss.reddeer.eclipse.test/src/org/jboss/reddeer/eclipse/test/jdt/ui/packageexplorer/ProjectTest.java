package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class ProjectTest {

	private static final String PROJECT_NAME_0 = "ProjectTestProject0";
	private static final String PROJECT_NAME_1 = "ProjectTestProject1";
	private static final String PROJECT_NAME_2 = "ProjectTestProject2";
	private PackageExplorer packageExplorer;
	private Project project0;
		
	@Before
	public void setUp(){
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = new NewJavaProjectWizardPage(); 
		page1.setProjectName(ProjectTest.PROJECT_NAME_0);
		dialog.finish();

		packageExplorer = new PackageExplorer();
		packageExplorer.open();
		project0 = packageExplorer.getProject(ProjectTest.PROJECT_NAME_0);
	}

	@Test
	public void select() {
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = new NewJavaProjectWizardPage();
		page1.setProjectName(ProjectTest.PROJECT_NAME_1);
		dialog.finish();
		dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		page1 = new NewJavaProjectWizardPage();
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
	
	@After
	public void tearDown() {
		packageExplorer.close();
		packageExplorer.open();
		if (packageExplorer.containsProject(ProjectTest.PROJECT_NAME_0)) {
			DeleteUtils.forceProjectDeletion(packageExplorer.getProject(ProjectTest.PROJECT_NAME_0),
				true);
		}
		if (packageExplorer.containsProject(ProjectTest.PROJECT_NAME_1)) {
			DeleteUtils.forceProjectDeletion(packageExplorer.getProject(ProjectTest.PROJECT_NAME_1),
				true);
		}
		if (packageExplorer.containsProject(ProjectTest.PROJECT_NAME_2)) {
			DeleteUtils.forceProjectDeletion(packageExplorer.getProject(ProjectTest.PROJECT_NAME_2),
				true);
		}
	}
}
