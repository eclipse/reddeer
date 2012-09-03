package org.jboss.reddeer.wiki.view;

import java.util.List;

import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardDialog;
import org.jboss.reddeer.workbench.view.View;

public class ServersView extends View {

	public static final String TITLE = "Servers";

	public ServersView() {
		super(TITLE);
	}

	public NewServerWizardDialog newServer(){
		// implementation
		return new NewServerWizardDialog();
	}

	public List<Server> getServers(){
		// implementation
		return null;
	}

	public Server getServer(String name){
		// implementation
		return null;
	}
}
