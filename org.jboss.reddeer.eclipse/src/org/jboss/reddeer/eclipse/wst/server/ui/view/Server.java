package org.jboss.reddeer.eclipse.wst.server.ui.view;

import java.util.List;

import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

/**
 * Represents a server on {@link ServersView}. Contains both, the server data
 * (name, state, status) and operations that can be invoked on server (Start,
 * Stop, Delete etc.). For operations that can be invoked on project added to
 * server see {@link ServerProject}
 * 
 * @author Lucia Jelinkova
 * 
 */
public class Server {
	
	private SWTBotTreeItem treeItem;
	
	private String name;

	public Server(TreeItem treeItem) {
		this.treeItem = new SWTBotTreeItem(treeItem);
		name = parseName(this.treeItem.getText());
	}

	public String getState() {
		throw new UnsupportedOperationException();
	}

	public String getStatus() {
		throw new UnsupportedOperationException();
	}

	public List<ServerProject> getProjects() {
		throw new UnsupportedOperationException();
	}

	public ServerProject getProject() {
		throw new UnsupportedOperationException();
	}

	public void open() {
		throw new UnsupportedOperationException();
	}

	public void start() {
		throw new UnsupportedOperationException();
	}

	public void startInDebug() {
		throw new UnsupportedOperationException();
	}

	public void restart() {
		throw new UnsupportedOperationException();
	}

	public void restartInDebug() {
		throw new UnsupportedOperationException();
	}

	public void stop() {
		throw new UnsupportedOperationException();
	}

	public void publish() {
		throw new UnsupportedOperationException();
	}

	public void clean() {
		throw new UnsupportedOperationException();
	}

	public void delete() {
		select();
		new ContextMenu("Delete").select();
		new DefaultShell("Delete Server");
		new PushButton("OK").click();
	}

	public void addAndRemoveProject() {
		throw new UnsupportedOperationException();
	}

	protected void select() {
		treeItem.select();
	}

	public String parseName(String label){
		if (!label.contains("[")){
			return label.trim();
		}
		return treeItem.getText().substring(0, treeItem.getText().indexOf("[")).trim();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
