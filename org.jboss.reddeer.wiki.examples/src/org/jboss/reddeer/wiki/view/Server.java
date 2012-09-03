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
		// implementation
	}

	public void stop() {
		// implementation
	}
}
