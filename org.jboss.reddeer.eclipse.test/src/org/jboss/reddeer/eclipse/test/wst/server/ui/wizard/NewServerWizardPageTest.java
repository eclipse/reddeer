package org.jboss.reddeer.eclipse.test.wst.server.ui.wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jboss.reddeer.eclipse.test.wst.server.ui.view.TestServer;
import org.jboss.reddeer.eclipse.wst.server.ui.view.Server;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersView;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizard;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardPage;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NewServerWizardPageTest {
	
	private static final String SERVER_NAME = TestServer.NAME;
	
	private static final String SERVER_PATH = "Basic";
	
	private ServersView view;
	
	private NewServerWizard wizard;

	private NewServerWizardPage wizardPage;
	
	@Before
	public void setup(){
		view = new ServersView();
		wizard = view.newServer();
		wizardPage = wizard.getFirstPage();
	}
	
	@Test
	public void selectType(){
		// TODO enhance this test
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
	}
	
	@Test
	public void setName(){
		wizardPage.selectType(SERVER_PATH, SERVER_NAME);
		wizardPage.setName("ABC server");
		
		wizard.finish();
		
		List<Server> servers = view.getServers();
		assertThat(servers.size(), is(1));
		assertThat(servers.get(0).getLabel().getName(), is("ABC server"));
	}
	
	@Test
	public void setHostName(){
		// TODO enhance this test
		wizardPage.setHostName("ABC");
	}
	
	@After
	public void cleanup(){
		if (wizard != null && NewServerWizard.TITLE.equals(new DefaultShell().getText())){
			wizard.cancel();
		}

		for (Server server : view.getServers()){
			server.delete(false);
		}
	}
}
