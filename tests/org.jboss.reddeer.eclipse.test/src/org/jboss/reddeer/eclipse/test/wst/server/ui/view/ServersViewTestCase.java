package org.jboss.reddeer.eclipse.test.wst.server.ui.view;

import java.io.File;

import org.jboss.reddeer.eclipse.condition.ServerExists;
import org.jboss.reddeer.eclipse.test.Activator;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.jboss.reddeer.eclipse.wst.server.ui.view.Server;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersView;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardPage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.junit.After;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ServersViewTestCase {

	public static final File ZIP_FILE = new File(Activator.getTestResourcesLocation(ServersViewTest.class), "server-project.zip");
	
	protected static final String PROJECT_1 = "server-project";
	
	protected static final String PROJECT_2 = "server-project-2";
	
	protected static final String PROJECT_3 = "server-project-3";
	
	protected ServersView serversView = new ServersView();

	protected NewServerWizardDialog wizardDialog;
	
	@After
	public void tearDown(){
		if (wizardDialog != null && NewServerWizardDialog.TITLE.equals(new DefaultShell().getText())){
			wizardDialog.cancel();
		}

		for (Server server : serversView.getServers()){
			server.delete(false);
		}
	}
	
	protected void createServer(String name) {
		serversView.open();
		wizardDialog = serversView.newServer();

		NewServerWizardPage newServerPage = wizardDialog.getFirstPage();
		newServerPage.selectType("Basic", TestServer.NAME);
		newServerPage.setName(name);		

		wizardDialog.finish();
		
		new WaitUntil(new ServerExists(name));	
	}
	
	protected static void importProjects(){
		ExternalProjectImportWizardDialog wizard  = new ExternalProjectImportWizardDialog();
		wizard.open();

		WizardProjectsImportPage wizardPage = wizard.getFirstPage();
		wizardPage.setArchiveFile(ZIP_FILE.getAbsolutePath());
		wizardPage.selectProjects("server-project", "server-project-2", "server-project-3");

		wizard.finish();
	}
}
