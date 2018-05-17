/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.wst.server.ui.view;

import java.io.File;

import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.condition.ServerExists;
import org.eclipse.reddeer.eclipse.test.Activator;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.Server;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersView2;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewServerWizard;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardPage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ServersViewTestCase {

	public static final File ZIP_FILE = new File(Activator.getTestResourcesLocation(ServersViewTest.class), "server-project.zip");
	
	protected static final String PROJECT_1 = "server-project";
	
	protected static final String PROJECT_2 = "server-project-2";
	
	protected static final String PROJECT_3 = "server-project-3";
	
	private static ServersView2 serversView;

	protected static NewServerWizard wizardDialog;
	
	@After
	public void tearDown(){
		if (wizardDialog != null && NewServerWizard.TITLE.equals(new DefaultShell().getText())){
			wizardDialog.cancel();
		}
	}
	
	@AfterClass
	public static void deleteServers(){
		for (Server server : getServersView().getServers()){
			server.delete(false);
		}
	}
	
	public static ServersView2 getServersView() {
		serversView = new ServersView2();
		serversView.open();
		return serversView;
	}
	
	protected static void createServer(String name) {
		wizardDialog = getServersView().newServer();

		NewServerWizardPage newServerPage = new NewServerWizardPage(wizardDialog);
		newServerPage.selectType("Basic", TestServer.NAME);
		newServerPage.setName(name);		

		wizardDialog.finish();
		
		new WaitUntil(new ServerExists(name));	
	}
	
	protected static void importProjects(){
		ExternalProjectImportWizardDialog wizard  = new ExternalProjectImportWizardDialog();
		wizard.open();

		WizardProjectsImportPage wizardPage = new WizardProjectsImportPage(wizard);
		wizardPage.setArchiveFile(ZIP_FILE.getAbsolutePath());
		wizardPage.selectProjects("server-project", "server-project-2", "server-project-3");

		wizard.finish();
	}
}
