package org.jboss.reddeer.eclipse.test.wst.server.ui.wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jboss.reddeer.eclipse.test.wst.server.ui.TestServerRuntime;
import org.jboss.reddeer.eclipse.wst.server.ui.Runtime;
import org.jboss.reddeer.eclipse.wst.server.ui.RuntimePreferencePage;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardPage;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.junit.Test;

public class NewRuntimeWizardPageTest extends RedDeerTest{

	private RuntimePreferencePage preference;
	
	private NewRuntimeWizardDialog wizard;
	
	private NewRuntimeWizardPage wizardPage;

	@Override
	protected void setUp(){
	  super.setUp();
		preference = new RuntimePreferencePage();
		preference.open();
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
	
	@Override
	public void tearDown(){
		preference.cancel();
		super.tearDown();
	}
}
