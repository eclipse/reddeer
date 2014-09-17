package org.jboss.reddeer.eclipse.test.wst.server.ui.wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jboss.reddeer.eclipse.jdt.ui.WorkbenchPreferenceDialog;
import org.jboss.reddeer.eclipse.test.wst.server.ui.TestServerRuntime;
import org.jboss.reddeer.eclipse.wst.server.ui.Runtime;
import org.jboss.reddeer.eclipse.wst.server.ui.RuntimePreferencePage;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NewRuntimeWizardPageTest {

	private RuntimePreferencePage preference;
	
	private NewRuntimeWizardDialog wizard;
	
	private NewRuntimeWizardPage wizardPage;

	@Before
	public void setUp(){
		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		
		preference = new RuntimePreferencePage();
		dialog.open();
		
		preference.removeAllRuntimes();
		wizard = preference.addRuntime();
		wizardPage = wizard.getFirstPage();
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
		preference.removeAllRuntimes();
		preference.cancel();
	}
}
