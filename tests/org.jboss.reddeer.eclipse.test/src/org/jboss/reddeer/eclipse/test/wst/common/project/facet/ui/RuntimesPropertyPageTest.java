package org.jboss.reddeer.eclipse.test.wst.common.project.facet.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.test.wst.server.ui.TestServerRuntime;
import org.jboss.reddeer.eclipse.test.wst.server.ui.view.ServersViewTestCase;
import org.jboss.reddeer.eclipse.ui.dialogs.ExplorerItemPropertyDialog;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.eclipse.wst.common.project.facet.ui.RuntimesPropertyPage;
import org.jboss.reddeer.eclipse.wst.server.ui.RuntimePreferencePage;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardPage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class RuntimesPropertyPageTest {

	private static final String PROJECT = "server-project";
	
	private ExplorerItemPropertyDialog dialog;
	
	private Project project;
	
	@Before
	public void createProject(){
		ExternalProjectImportWizardDialog wizard  = new ExternalProjectImportWizardDialog();
		wizard.open();

		WizardProjectsImportPage wizardPage = new WizardProjectsImportPage();
		wizardPage.setArchiveFile(ServersViewTestCase.ZIP_FILE.getAbsolutePath());
		wizardPage.selectProjects(PROJECT);

		wizard.finish();
	}
	
	@Before
	public void createRuntime(){
		WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
		RuntimePreferencePage runtimePreference = new RuntimePreferencePage();

		preferencesDialog.open();
		preferencesDialog.select(runtimePreference);
		
		NewRuntimeWizardDialog dialog = runtimePreference.addRuntime();
		NewRuntimeWizardPage page = new NewRuntimeWizardPage();
		page.selectType(TestServerRuntime.CATEGORY, TestServerRuntime.NAME);
		dialog.finish();
		
		preferencesDialog.ok();
	}
	
	@After
	public void cleanup(){
		Shell shell = null;
		try {
			new WaitUntil(new ShellWithTextIsAvailable(dialog.getTitle()), TimePeriod.NONE);
			shell = new DefaultShell(dialog.getTitle());
			shell.close();
		} catch (WaitTimeoutExpiredException e){
			e.printStackTrace();
			// not found, no action needed
		}
		
		DeleteUtils.forceProjectDeletion(getProject(),true);
		
		WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
		RuntimePreferencePage runtimePreference = new RuntimePreferencePage();

		preferencesDialog.open();
		preferencesDialog.select(runtimePreference);
		
		runtimePreference.removeAllRuntimes();
		preferencesDialog.cancel();
	}
	
	@Test
	public void selectRuntime() {
		dialog = new ExplorerItemPropertyDialog(getProject());
		RuntimesPropertyPage propertyPage = new RuntimesPropertyPage();
		
		dialog.open();
		dialog.select(propertyPage);
		propertyPage.selectRuntime(TestServerRuntime.NAME);
		assertTrue(propertyPage.getSelectedRuntimes().contains(TestServerRuntime.NAME));
		dialog.ok();
		
		dialog.open();
		dialog.select(propertyPage);
 		assertThat(propertyPage.getSelectedRuntimes().get(0), is(TestServerRuntime.NAME));
 	}
	
	public Project getProject() {
		if (project == null){
			PackageExplorer packageExplorer = new PackageExplorer();
			packageExplorer.open();
			project = packageExplorer.getProject(PROJECT);
		}
		return project;
	}
}
