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
package org.eclipse.reddeer.eclipse.test.wst.server.ui.wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.eclipse.reddeer.eclipse.test.wst.server.ui.view.TestServer;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.Server;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersView2;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewServerWizard;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardPage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class NewServerWizardPageTest {
	
	private static final String SERVER_NAME = TestServer.NAME;
	
	private static final String SERVER_PATH = "Basic";
	
	private ServersView2 view;
	
	private NewServerWizard wizard;

	private NewServerWizardPage wizardPage;
	
	@Before
	public void setUp(){
		view = new ServersView2();
		view.open();
		wizard = view.newServer();
		wizardPage = new NewServerWizardPage(wizard);
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
	public void tearDown(){
		if (wizard != null && !wizard.getShell().isDisposed()){
			wizard.cancel();
		}

		for (Server server : view.getServers()){
			server.delete(false);
		}
	}
}
