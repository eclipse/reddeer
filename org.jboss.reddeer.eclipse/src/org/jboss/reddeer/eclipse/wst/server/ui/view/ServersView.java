package org.jboss.reddeer.eclipse.wst.server.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardDialog;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.workbench.view.View;

/**
 * Represents the Servers view. This class contains methods that can be invoked even 
 * if no server is selected. You can invoke server specific operations on {@link Server} instances. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ServersView extends View {
	
	public static final String TITLE = "Servers";

	private static final Logger log = Logger.getLogger(ServersView.class);
	
	public ServersView() {
		super(TITLE);
	}

	public NewServerWizardDialog newServer(){
		log.info("Creating new server");
		open();
		new ContextMenu("New","Server").select();
		new DefaultShell("New Server");
		return new NewServerWizardDialog();
	}

	public List<Server> getServers(){
		List<Server> servers = new ArrayList<Server>();

		Tree tree;
		try {
			tree = getServersTree();
		} catch (SWTLayerException e){
			return new ArrayList<Server>();
		}
		for (TreeItem item : tree.getItems()){
			servers.add(new Server(item));
		}
		return servers;
	}

	public Server getServer(String name){
		for (Server server : getServers()){
			if (server.getLabel().getName().equals(name)){
				return server;
			}
		}
		throw new EclipseLayerException("There is no server with name " + name);
	}

	protected Tree getServersTree(){
		open();
		return new DefaultTree();
	}
}
