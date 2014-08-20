package org.jboss.reddeer.eclipse.wst.common.project.facet.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.test.wst.server.ui.TestServerRuntime;
import org.jboss.reddeer.eclipse.test.wst.server.ui.view.ServersViewTestCase;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.jboss.reddeer.eclipse.wst.server.ui.RuntimePreferencePage;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardPage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class RuntimesPropertyPageTest {

	private static final String PROJECT = "RuntimesPropertyPageTestProject";
	
	private RuntimesPropertyPage propertyPage;
	
	@Before
	public void createProject(){
		ExternalProjectImportWizardDialog wizard  = new ExternalProjectImportWizardDialog();
		wizard.open();

		WizardProjectsImportPage wizardPage = wizard.getFirstPage();
		wizardPage.setArchiveFile(ServersViewTestCase.ZIP_FILE.getAbsolutePath());
		wizardPage.selectProjects(PROJECT);

		wizard.finish();
		
		propertyPage = new RuntimesPropertyPage(new PackageExplorer().getProject(PROJECT));
	}
	
	@Before
	public void createRuntime(){
		RuntimePreferencePage runtimePreference = new RuntimePreferencePage();
		runtimePreference.open();
		
		NewRuntimeWizardDialog dialog = runtimePreference.addRuntime();
		NewRuntimeWizardPage page = dialog.getFirstPage();
		page.selectType(TestServerRuntime.CATEGORY, TestServerRuntime.NAME);
		dialog.finish();
		
		runtimePreference.ok();
	}
	
	@After
	public void cleanup(){
		Shell shell = null;
		try {
			shell = new DefaultShell(propertyPage.getPageTitle());
			shell.close();
		} catch (SWTLayerException e){
			// not found, no action needed
		}
		
		new PackageExplorer().getProject(PROJECT).delete(true);
		
		RuntimePreferencePage runtimePreference = new RuntimePreferencePage();
		runtimePreference.open();
		runtimePreference.removeAllRuntimes();
		runtimePreference.cancel();
	}
	
	@Test
	public void selectRuntime() {
		propertyPage.open();
		propertyPage.selectRuntime(TestServerRuntime.NAME);
		propertyPage.ok();
		
		propertyPage.open();
		assertThat(propertyPage.getSelectedRuntimes().get(0), is(TestServerRuntime.NAME));
 	}
}
