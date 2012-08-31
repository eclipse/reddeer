package org.jboss.reddeer.wiki.introduction.example;

import org.jboss.reddeer.eclipse.wst.server.ui.view.Server;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersView;

public class HelloWorld {

	public HelloWorld() {
	}

	public static void main(String[] args) {
		ServersView serversView = new ServersView();
		Server server = serversView.getServer("My Server");
		server.start();
	}
}
