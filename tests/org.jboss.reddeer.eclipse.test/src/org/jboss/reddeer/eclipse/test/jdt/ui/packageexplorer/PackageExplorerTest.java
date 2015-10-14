package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.core.handler.WorkbenchPartHandler;
import org.jboss.reddeer.eclipse.core.resources.Project;
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
public class PackageExplorerTest {

	private static final String PROJECT_NAME_0 = "PackageExplorerTestProject0";
	private static final String PROJECT_NAME_1 = "PackageExplorerTestProject1";
	private static final String PROJECT_NAME_2 = "PackageExplorerTestProject2";
	private static final String PROJECT_NAME_3 = "PackageExplorerTestProject3";
	private static PackageExplorer packageExplorer;
	private static Project project0;
	private static Project project1;
	private static Project project2;

	@BeforeClass
	public static void setUp() {
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = new NewJavaProjectWizardPage();
		page1.setProjectName(PackageExplorerTest.PROJECT_NAME_0);
		dialog.finish();
		
		dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		page1 = new NewJavaProjectWizardPage();
		page1.setProjectName(PackageExplorerTest.PROJECT_NAME_1);
		dialog.finish();
		
		dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		page1 = new NewJavaProjectWizardPage();
		page1.setProjectName(PackageExplorerTest.PROJECT_NAME_2);
		dialog.finish();

		packageExplorer = new PackageExplorer();
		packageExplorer.open();
		project0 = packageExplorer.getProject(PackageExplorerTest.PROJECT_NAME_0);
		project1 = packageExplorer.getProject(PackageExplorerTest.PROJECT_NAME_1);
		project2 = packageExplorer.getProject(PackageExplorerTest.PROJECT_NAME_2);
		project1.select();
	}

	@Test
	public void open() {
		packageExplorer = new PackageExplorer();
		packageExplorer.open();
		String currentViewTitle = WorkbenchPartHandler.getInstance().getActiveViewTitle();
		assertTrue("Active View has to be Package Explorer but is "
				+ currentViewTitle, currentViewTitle.equals("Package Explorer"));
	}

	@Test
	public void select() {
		project1.select();
		assertTrue("Project " + project1.getName() + " is not selected",
				project1.isSelected());
		assertTrue("Project " + project0.getName() + " is selected",
				!project0.isSelected());
		assertTrue("Project " + project2.getName() + " is selected",
				!project2.isSelected());
	}
	@Test
	public void multipleSelect() {
		packageExplorer.selectProjects(PackageExplorerTest.PROJECT_NAME_0,PackageExplorerTest.PROJECT_NAME_2);

		assertTrue("Project " + project0.getName() + " is not selected",
				project0.isSelected());
		assertTrue("Project " + project1.getName() + " is selected",
				!project1.isSelected());
		assertTrue("Project " + project2.getName() + " is not selected",
				project2.isSelected());
	}
	@Test
	public void delete() {
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		
		NewJavaProjectWizardPage page1 = new NewJavaProjectWizardPage();
		page1.setProjectName(PackageExplorerTest.PROJECT_NAME_3);
		dialog.finish();
		
		packageExplorer.getProject(PackageExplorerTest.PROJECT_NAME_3).delete(true);
		assertFalse("Package Explorer contains project "
				+ PackageExplorerTest.PROJECT_NAME_3
				+ " but it should be deleted.",
				packageExplorer
						.containsProject(PackageExplorerTest.PROJECT_NAME_3));
	}
	@Test
	public void getTitle() {
		packageExplorer = new PackageExplorer();
		packageExplorer.open();
		String pacakgeExplorerTitle = packageExplorer.getTitle();
		assertTrue("Package Explorer has wrong title: '"
				+ pacakgeExplorerTitle + "'", pacakgeExplorerTitle.equals("Package Explorer"));
	}
	
	@AfterClass
	public static void tearDown() {
		if (packageExplorer != null){
			for (Project p : packageExplorer.getProjects()){
				DeleteUtils.forceProjectDeletion(p, true);
			}
		}
	}
}
