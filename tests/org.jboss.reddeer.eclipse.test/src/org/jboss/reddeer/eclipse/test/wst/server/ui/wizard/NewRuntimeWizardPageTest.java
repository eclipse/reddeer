package org.jboss.reddeer.eclipse.test.wst.server.ui.wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.jboss.reddeer.eclipse.test.wst.server.ui.TestServerRuntime;
import org.jboss.reddeer.eclipse.wst.server.ui.Runtime;
import org.jboss.reddeer.eclipse.wst.server.ui.RuntimePreferencePage;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardPage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class NewRuntimeWizardPageTest {

	private RuntimePreferencePage preference;
	
	private NewRuntimeWizardDialog wizard;
	
	private NewRuntimeWizardPage wizardPage;
	
	private WorkbenchPreferenceDialog workbenchPreferencesDialog;

	@Before
	public void setUp(){
		workbenchPreferencesDialog = new WorkbenchPreferenceDialog();
		workbenchPreferencesDialog.open();
		preference = new RuntimePreferencePage();
		workbenchPreferencesDialog.select(preference);
		preference.removeAllRuntimes();
		wizard = preference.addRuntime();
		wizardPage = new NewRuntimeWizardPage();
	}
	
	@Test
	public void selectType(){
		wizardPage.selectType("Basic", TestServerRuntime.NAME);
		wizard.finish();
		List<Runtime> runtimes = preference.getServerRuntimes();
		assertThat(runtimes.size(), is(1));
		assertThat(runtimes.get(0).getType(), is(TestServerRuntime.TYPE));
	}
	
	@After
	public void tearDown(){
		if (workbenchPreferencesDialog != null){
			preference.removeAllRuntimes();
			workbenchPreferencesDialog.cancel();			
		}
	}
}
