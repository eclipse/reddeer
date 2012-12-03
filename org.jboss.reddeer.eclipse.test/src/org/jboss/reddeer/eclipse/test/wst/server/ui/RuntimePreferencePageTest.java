package org.jboss.reddeer.eclipse.test.wst.server.ui;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jboss.reddeer.eclipse.wst.server.ui.Runtime;
import org.jboss.reddeer.eclipse.wst.server.ui.RuntimePreferencePage;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardPage;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.junit.Test;

public class RuntimePreferencePageTest extends RedDeerTest{

	private static final String SERVER_NAME = TestServerRuntime.NAME;
	
	private static final String SERVER_PATH = "Basic";

	private RuntimePreferencePage preferencePage;

	@Override
	protected void setUp(){
	  super.setUp();
		preferencePage = new RuntimePreferencePage();
	}

	@Test
	public void open() throws InterruptedException{
		preferencePage.open();
		assertThat(preferencePage.getName(), is("Server " + RuntimePreferencePage.PAGE_NAME));
	}

	@Test
	public void addRuntime() {
		preferencePage.open();
		
		NewRuntimeWizardPage wizardPage = preferencePage.addRuntime().getFirstPage();
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardPage.getWizardDialog().finish();
		
		List<Runtime> runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(1));
		assertThat(runtimes.get(0), is(new Runtime(SERVER_NAME, SERVER_NAME)));
	}

	@Test
	public void removeRuntime() {
		preferencePage.open();
		
		NewRuntimeWizardPage wizardPage = preferencePage.addRuntime().getFirstPage();
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardPage.getWizardDialog().finish();
		
		wizardPage = preferencePage.addRuntime().getFirstPage();
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardPage.getWizardDialog().finish();
		
		List<Runtime> runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(2));
		
		preferencePage.removeRuntime(new Runtime(SERVER_NAME + " (2)", null));
		runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(1));
		
		preferencePage.removeRuntime(new Runtime(SERVER_NAME, null));
		runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(0));
	}
	
	@Test
	public void removeAllRuntime() {
		preferencePage.open();
		
		NewRuntimeWizardPage wizardPage = preferencePage.addRuntime().getFirstPage();
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardPage.getWizardDialog().finish();
		
		wizardPage = preferencePage.addRuntime().getFirstPage();
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardPage.getWizardDialog().finish();
		
		List<Runtime> runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(2));

		preferencePage.removeAllRuntimes();
		runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(0));
	}
	
	@Override
	protected void tearDown(){
		preferencePage.open();
		preferencePage.removeAllRuntimes();
		preferencePage.cancel();
		super.tearDown();
	}
}
