package org.jboss.reddeer.eclipse.wst.server.ui.view;

import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Represents a module assigned to server {@link Server} on {@link ServersView}.
 * 
 * @author Lucia Jelinkova
 *
 */
public class ServerModule {
	
	protected TreeItem treeItem;
	
	public ServerModule(TreeItem item) {
		this.treeItem = item;
	}
	
	public ModuleLabel getLabel(){
		return new ModuleLabel(treeItem);
	}
	
	public void remove(){
		throw new UnsupportedOperationException();
	}
}
