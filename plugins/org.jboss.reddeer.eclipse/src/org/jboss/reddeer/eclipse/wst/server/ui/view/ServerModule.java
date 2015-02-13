package org.jboss.reddeer.eclipse.wst.server.ui.view;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * Represents a module assigned to server {@link Server} on {@link ServersView}.
 * 
 * @author Lucia Jelinkova
 *
 */
public class ServerModule {

	private static final Logger log = Logger.getLogger(ServerModule.class);
	
	protected TreeItem treeItem;
	
	protected ServersView view;

	/**
	 * @deprecated Use {@link #ServerModule(TreeItem, ServersView)}
	 * @param item
	 */
	public ServerModule(TreeItem item) {
		if (item == null) {
			throw new IllegalArgumentException("item can't be null");
		}
		this.treeItem = item;
		this.view = new ServersView();
		view.open();
	}
	
	protected ServerModule(TreeItem item, ServersView view) {
		if (item == null) {
			throw new IllegalArgumentException("item can't be null");
		}
		this.treeItem = item;
		this.view = view;
	}

	/**
	 * Returns module's label as {@link ModuleLabel}.
	 */
	public ModuleLabel getLabel() {
		activate();
		return new ModuleLabel(treeItem);
	}

	/**
	 * Removes the server module.
	 */
	public void remove(){
		activate();
		if (treeItem == null) {
			throw new EclipseLayerException("ServerModule was already removed");
		}
		log.info("Remove server module with name " + getLabel().getName());
		final String workbenchTitle = new WorkbenchShell().getText();
		new ShellMenu("Edit", "Delete").select();
		new WaitUntil(new ShellWithTextIsActive("Server"));
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsAvailable("Server"));
		new WaitUntil(new ShellWithTextIsActive(workbenchTitle));
		new WaitWhile(new JobIsRunning());
		treeItem = null;
	}
	
	protected void activate(){
		view.activate();
		treeItem.select();
	}
}
