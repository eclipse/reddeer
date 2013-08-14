package org.jboss.reddeer.eclipse.test.wst.server.ui.view;

import org.jboss.reddeer.eclipse.condition.ServerExists;
import org.jboss.reddeer.eclipse.wst.server.ui.view.Server;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersView;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardPage;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

public class ServersViewTestCase extends RedDeerTest {

	protected ServersView serversView = new ServersView();

	protected NewServerWizardDialog wizardDialog;
	
	@Override
	protected void tearDown(){
		if (wizardDialog != null && NewServerWizardDialog.TITLE.equals(new DefaultShell().getText())){
			wizardDialog.cancel();
		}

		for (Server server : serversView.getServers()){
			server.delete(false);
		}
		
		super.tearDown();
	}
	
	protected void createServer(String name) {
		wizardDialog = serversView.newServer();

		NewServerWizardPage newServerPage = wizardDialog.getFirstPage();
		newServerPage.selectType("Basic", TestServer.NAME);
		newServerPage.setName(name);		

		wizardDialog.finish();
		
		new WaitUntil(new ServerExists(name));	

		
	}
}
