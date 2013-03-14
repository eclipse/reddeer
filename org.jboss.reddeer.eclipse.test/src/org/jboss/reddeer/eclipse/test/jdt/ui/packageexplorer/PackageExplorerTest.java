package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.Project;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Bot;
import org.junit.Test;

public class PackageExplorerTest extends RedDeerTest{

  private static final String PROJECT_NAME_0 = "TestProject0";
  private static final String PROJECT_NAME_1 = "TestProject1";
  private static final String PROJECT_NAME_2 = "TestProject2";
  
  private PackageExplorer packageExplorer;
	
  @Override
  protected void setUp(){
    super.setUp();

    packageExplorer = new PackageExplorer();
  }
  @Test
	public void selectProject() {
	  createProjects();
    packageExplorer.selectProject(PackageExplorerTest.PROJECT_NAME_0);
    final Project project0 = packageExplorer.getProject(PackageExplorerTest.PROJECT_NAME_0);
    final Project project1 = packageExplorer.getProject(PackageExplorerTest.PROJECT_NAME_1);
    final Project project2 = packageExplorer.getProject(PackageExplorerTest.PROJECT_NAME_2);
    project0.select();
    assertTrue("Project " + project0.getName() + "is not selected" , project0.isSelected());
    assertTrue("Project " + project1.getName() + "is selected" , !project1.isSelected());
    assertTrue("Project " + project2.getName() + "is selected" , !project2.isSelected());		
	}
	@Test
  public void selectProjects() {
    createProjects();
    final Project project0 = packageExplorer.getProject(PackageExplorerTest.PROJECT_NAME_0);
    final Project project1 = packageExplorer.getProject(PackageExplorerTest.PROJECT_NAME_1);
    final Project project2 = packageExplorer.getProject(PackageExplorerTest.PROJECT_NAME_2);
    packageExplorer.selectProjects(PackageExplorerTest.PROJECT_NAME_0,PackageExplorerTest.PROJECT_NAME_1);
    assertTrue("Project " + project0.getName() + "is not selected" , project0.isSelected());
    assertTrue("Project " + project1.getName() + "is not selected" , project1.isSelected());
    assertTrue("Project " + project2.getName() + "is selected" , !project2.isSelected());   
  }
	
	@Test
  public void open() {
    packageExplorer.open();
    assertTrue("Active View has to be Package Explorer but is " + Bot.get().activeView().getTitle(),
      Bot.get().activeView().getTitle().equals("Package Explorer"));
    
  }
  
  @Override
  protected void tearDown() {
    if (packageExplorer.containsProject(PackageExplorerTest.PROJECT_NAME_0)) {
      packageExplorer.getProject(PackageExplorerTest.PROJECT_NAME_0).delete(
          true);
    }
    if (packageExplorer.containsProject(PackageExplorerTest.PROJECT_NAME_1)) {
      packageExplorer.getProject(PackageExplorerTest.PROJECT_NAME_1).delete(
          true);
    }
    if (packageExplorer.containsProject(PackageExplorerTest.PROJECT_NAME_2)) {
      packageExplorer.getProject(PackageExplorerTest.PROJECT_NAME_2).delete(
          true);
    }
    super.tearDown();
  }
	 
  private void createProjects(){
    NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
    dialog.open();
    NewJavaProjectWizardPage page1 = dialog.getFirstPage(); 
    page1.setProjectName(PackageExplorerTest.PROJECT_NAME_0);
    dialog.finish();
    dialog = new NewJavaProjectWizardDialog();
    dialog.open();
    page1 = dialog.getFirstPage();
    page1.setProjectName(PackageExplorerTest.PROJECT_NAME_1);
    dialog.finish();
    dialog = new NewJavaProjectWizardDialog();
    dialog.open();
    page1 = dialog.getFirstPage();
    page1.setProjectName(PackageExplorerTest.PROJECT_NAME_2);
    dialog.finish();
  }
}
