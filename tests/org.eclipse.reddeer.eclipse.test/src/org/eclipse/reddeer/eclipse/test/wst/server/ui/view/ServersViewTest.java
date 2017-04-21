/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.wst.server.ui.view;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.DefaultServer;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.Server;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewServerWizard;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.junit.Test;

public class ServersViewTest extends ServersViewTestCase{

	@Override
	public void tearDown() {
		super.tearDown();
		for (DefaultServer server : getServersView().getServers()){
			server.delete(false);
		}
	}
	
	@Test
	public void newServer(){
		wizardDialog = getServersView().newServer();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(NewServerWizard.TITLE));
	}

	@Test
	public void getServers_noServers(){
		List<DefaultServer> servers = getServersView().getServers();
		
		assertThat(servers.size(), is(0));
	}

	@Test
	public void getServers(){
		createServer("Server AB");
		createServer("Server A");

		List<DefaultServer> servers = getServersView().getServers();
		assertThat(servers.size(), is(2));
		assertThat(servers.get(0).getLabel().getName(), is("Server A"));
		assertThat(servers.get(1).getLabel().getName(), is("Server AB"));
	}

	@Test(expected=EclipseLayerException.class)
	public void getServer_noServers(){
		getServersView().getServer("Server A");
	}

	@Test(expected=EclipseLayerException.class)
	public void getServer_notFound(){
		createServer("Server AB");

		getServersView().getServer("Server A");
	}

	@Test
	public void getServer(){
		createServer("Server AB");
		createServer("Server A");

		Server server = getServersView().getServer("Server A");
		assertNotNull(server);
		assertThat(server.getLabel().getName(), is("Server A"));
	}
}
