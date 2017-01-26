/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.test.wst.server.ui.wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jboss.reddeer.eclipse.test.wst.server.ui.view.TestServer;
import org.jboss.reddeer.eclipse.wst.server.ui.view.DefaultServer;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersView;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardPage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class NewServerWizardPageTest {
	
	private static final String SERVER_NAME = TestServer.NAME;
	
	private static final String SERVER_PATH = "Basic";
	
	private ServersView view;
	
	private NewServerWizardDialog wizard;

	private NewServerWizardPage wizardPage;
	
	@Before
	public void setUp(){
		view = new ServersView();
		view.open();
		wizard = view.newServer();
		wizardPage = new NewServerWizardPage();
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
		
		List<DefaultServer> servers = view.getServers();
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
		if (wizard != null && NewServerWizardDialog.TITLE.equals(new DefaultShell().getText())){
			wizard.cancel();
		}

		for (DefaultServer server : view.getServers()){
			server.delete(false);
		}
	}
}
