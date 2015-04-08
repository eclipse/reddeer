package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import org.jboss.reddeer.eclipse.core.resources.JavaProject;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class JavaProjectTest {

	private static final String PROJECT_NAME_0 = "JavaTestProject0";
	private static PackageExplorer packageExplorer;
		
	@BeforeClass
	public static void setUp(){
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = new NewJavaProjectWizardPage();
		page1.setProjectName(PROJECT_NAME_0);
		dialog.finish();

		packageExplorer = new PackageExplorer();
		packageExplorer.open();
	}
	
	@Test
	public void testGetJavaProject() {
			JavaProject javaProject = packageExplorer.getProject(PROJECT_NAME_0, JavaProject.class);
	}
	
	@Test(expected=EclipseLayerException.class)
	public void testGetNonExistingJavaProject() {
		JavaProject javaProject = packageExplorer.getProject("NONEXISTINGPROJECT", JavaProject.class);
	}
	
	@AfterClass
	public static void tearDown() {
		packageExplorer.close();
		packageExplorer.open();
		if (packageExplorer.containsProject(PROJECT_NAME_0)) {
			DeleteUtils.forceProjectDeletion(packageExplorer.getProject(PROJECT_NAME_0),
				true);
		}
	}
}
