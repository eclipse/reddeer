package org.jboss.reddeer.eclipse.test.ui.ide;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.ide.NewGeneralProjectReferencesWizardPage;
import org.jboss.reddeer.eclipse.ui.ide.NewGeneralProjectWizardDialog;
import org.jboss.reddeer.eclipse.ui.ide.NewGeneralProjectWizardPage;
import org.jboss.reddeer.swt.exception.WidgetNotEnabledException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NewGeneralProjectWizardDialogTest {
  
  private static final String DEFAULT_PROJECT_NAME = "defaultGeneralProject";
  private static final String CUSTOMIZED_PROJECT_NAME = "customizedGeneralProject";
  private PackageExplorer packageExplorer;
  private static final String CUSTOM_PROJECT_LOCATION = System.getProperty("java.io.tmpdir")
	  + File.separator + "rdcustomprojectlocation"
	  + System.currentTimeMillis();
  @Before
  public void setUp() {
    packageExplorer = new PackageExplorer();
  }
	@Test
	public void createGeneralProjects(){
	  // create defult project
		NewGeneralProjectWizardDialog wizardDialog = 
		    new NewGeneralProjectWizardDialog();
		wizardDialog.open();
		NewGeneralProjectWizardPage projectPage =
		    wizardDialog.getFirstPage();
		projectPage.setProjectName(NewGeneralProjectWizardDialogTest.DEFAULT_PROJECT_NAME);
		wizardDialog.finish();
		assertTrue("Package Explorer has to contain project " +
		    NewGeneralProjectWizardDialogTest.DEFAULT_PROJECT_NAME +
		    " but it doesn't",
		  packageExplorer.containsProject(NewGeneralProjectWizardDialogTest.DEFAULT_PROJECT_NAME));
		// create customized project
    wizardDialog.open();
    NewGeneralProjectReferencesWizardPage projectReferencesPage =
        new NewGeneralProjectReferencesWizardPage(wizardDialog);
    projectPage.setProjectName(NewGeneralProjectWizardDialogTest.CUSTOMIZED_PROJECT_NAME);
    File customProjectDir = new File(NewGeneralProjectWizardDialogTest.CUSTOM_PROJECT_LOCATION);
    if (customProjectDir.exists()){
    	customProjectDir.delete();
    }
    customProjectDir.mkdir();
    projectPage.setProjectLocation(NewGeneralProjectWizardDialogTest.CUSTOM_PROJECT_LOCATION);
    try{
      projectPage.addProjectToWorkingSet("dummyws");  
    }catch (WidgetNotEnabledException wnee){
      // do nothing this exception means there is no Working set
      // defined but all widgets were found
    }    
    projectReferencesPage.setProjectReferences(NewGeneralProjectWizardDialogTest.DEFAULT_PROJECT_NAME);
    wizardDialog.finish();
    assertTrue("Package Explorer has to contain project " +
        NewGeneralProjectWizardDialogTest.CUSTOMIZED_PROJECT_NAME +
        " but it doesn't",
      packageExplorer.containsProject(NewGeneralProjectWizardDialogTest.CUSTOMIZED_PROJECT_NAME));
  }
	@After
	public void deleteProjects(){
	  packageExplorer.getProject(NewGeneralProjectWizardDialogTest.CUSTOMIZED_PROJECT_NAME).delete(true);
	  packageExplorer.getProject(NewGeneralProjectWizardDialogTest.DEFAULT_PROJECT_NAME).delete(true);
	  File customProjectDir = new File(NewGeneralProjectWizardDialogTest.CUSTOM_PROJECT_LOCATION);
	  if (customProjectDir.exists()){
	  	customProjectDir.delete();
	  }
	}
}
