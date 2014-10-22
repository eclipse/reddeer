package org.jboss.reddeer.eclipse.test.wst.server.ui.view;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.wst.server.ui.view.Server;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardDialog;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.Test;

public class ServersViewTest extends ServersViewTestCase{

	@Test
	public void newServer(){
		serversView.open();
		wizardDialog = serversView.newServer();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(NewServerWizardDialog.TITLE));
	}

	@Test
	public void getServers_noServers(){
		serversView.open();
		List<Server> servers = serversView.getServers();
		
		assertThat(servers.size(), is(0));
	}

	@Test
	public void getServers(){
		createServer("Server AB");
		createServer("Server A");

		serversView.open();
		List<Server> servers = serversView.getServers();
		assertThat(servers.size(), is(2));
		assertThat(servers.get(0).getLabel().getName(), is("Server A"));
		assertThat(servers.get(1).getLabel().getName(), is("Server AB"));
	}

	@Test(expected=EclipseLayerException.class)
	public void getServer_noServers(){
		serversView.open();
		serversView.getServer("Server A");
	}

	@Test(expected=EclipseLayerException.class)
	public void getServer_notFound(){
		serversView.open();
		createServer("Server AB");

		serversView.getServer("Server A");
	}

	@Test
	public void getServer(){
		serversView.open();
		createServer("Server AB");
		createServer("Server A");

		Server server = serversView.getServer("Server A");
		assertNotNull(server);
		assertThat(server.getLabel().getName(), is("Server A"));
	}
}
