package org.jboss.reddeer.eclipse.test.wst.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jboss.reddeer.eclipse.wst.server.NewRuntimeWizardPage;
import org.jboss.reddeer.eclipse.wst.server.Runtime;
import org.jboss.reddeer.eclipse.wst.server.RuntimePreferencePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RuntimePreferencePageTest {

	private static final String SERVER_NAME = "HTTP Server";
	
	private static final String SERVER_PATH = "Basic";

	private RuntimePreferencePage preferencePage;

	@Before
	public void setup(){
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
		
		NewRuntimeWizardPage wizard = preferencePage.addRuntime();
		wizard.selectType(SERVER_PATH, SERVER_NAME);
		wizard.finish();
		
		List<Runtime> runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(1));
		assertThat(runtimes.get(0), is(new Runtime(SERVER_NAME, SERVER_NAME)));
	}

	@Test
	public void removeRuntime() {
		preferencePage.open();
		
		NewRuntimeWizardPage wizard = preferencePage.addRuntime();
		wizard.selectType(SERVER_PATH, SERVER_NAME);
		wizard.finish();
		
		wizard = preferencePage.addRuntime();
		wizard.selectType(SERVER_PATH, SERVER_NAME);
		wizard.finish();
		
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
		
		NewRuntimeWizardPage wizard = preferencePage.addRuntime();
		wizard.selectType(SERVER_PATH, SERVER_NAME);
		wizard.finish();
		
		wizard = preferencePage.addRuntime();
		wizard.selectType(SERVER_PATH, SERVER_NAME);
		wizard.finish();
		
		List<Runtime> runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(2));

		preferencePage.removeAllRuntimes();
		runtimes = preferencePage.getServerRuntimes();
		assertThat(runtimes.size(), is(0));
	}
	
	@After
	public void removeRuntimes(){
		preferencePage.open();
		preferencePage.removeAllRuntimes();
		preferencePage.cancel();
	}
}
