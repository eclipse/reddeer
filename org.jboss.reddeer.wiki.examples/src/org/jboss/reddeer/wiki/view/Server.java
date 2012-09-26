package org.jboss.reddeer.wiki.view;

import java.util.List;

import org.jboss.reddeer.swt.api.TreeItem;

public class Server {

	private TreeItem treeItem;
	
	public Server(TreeItem treeItem) {
		this.treeItem = treeItem;
	}

	public List<ServerProject> getProjects() {
		throw new UnsupportedOperationException();
	}

	public void start() {
		System.out.println("Starting server: " + treeItem.getText());
		// implementation
	}

	public void stop() {
		System.out.println("Stopping server: " + treeItem.getText());
		// implementation
	}
}
