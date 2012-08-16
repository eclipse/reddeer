package org.jboss.reddeer.eclipse.test.wst.server.ui.view;

import org.jboss.reddeer.eclipse.wst.server.ui.view.Server;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersView;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizard;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardPage;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;

public class ServersViewTestCase {

	protected ServersView serversView = new ServersView();

	protected NewServerWizard wizardDialog;
	
	@After
	public void cleanup(){
		if (wizardDialog != null && NewServerWizard.TITLE.equals(new DefaultShell().getText())){
			wizardDialog.cancel();
		}

		for (Server server : serversView.getServers()){
			server.delete();
		}
	}
	
	protected void createServer(String name) {
		wizardDialog = serversView.newServer();

		NewServerWizardPage newServerPage = wizardDialog.getFirstPage();
		newServerPage.setName(name);
		newServerPage.selectType("Basic", TestServer.NAME);

		wizardDialog.finish();
	}
}
