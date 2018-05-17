/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.wst.server.ui.cnf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.logging.LoggingUtils;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewServerWizard;
import org.eclipse.reddeer.swt.api.Tree;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Represents the Servers view. This class contains methods that can be invoked even 
 * if no server is selected. You can invoke server specific operations on instances 
 * of {@link Server} implementation. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ServersView2 extends WorkbenchView {

	public static final String TITLE = "Servers";

	private static final Logger log = Logger.getLogger(ServersView2.class);

	/**
	 * Constructs the view with {@value #TITLE}.
	 */
	public ServersView2() {
		super(TITLE);
	}

	/**
	 * Opens a wizard for adding new server.
	 * 
	 * @return Wizard for adding new server
	 */
	public NewServerWizard newServer(){
		activate();
		log.info("Create new server");
		new ContextMenuItem("New","Server").select();
		new DefaultShell(NewServerWizard.TITLE);
		return new NewServerWizard();
	}

	/**
	 * Gets list of default servers.
	 * 
	 * @return list of default servers
	 */
	public List<Server> getServers() {
		List<Server> servers = new ArrayList<Server>();

		Tree tree;
		try {
			tree = getServersTree();
		} catch (CoreLayerException e){
			return new ArrayList<Server>();
		}
		for (TreeItem item : tree.getItems()){
			if (item != null && !item.isDisposed()){
				servers.add(new DefaultServer(item));	
			}			
		}
		return servers;
	}

	/**
	 * Gets a default server with a given name.
	 * 
	 * @param name Server name
	 * @return Server with a given name.
	 */
	public Server getServer(String name){
		return getServer(DefaultServer.class, name);
	}
	
	/**
	 * Gets a server of specified type with a given name
	 * 
	 * @param <T> type of server
	 * @param clazz type of a server
	 * @param name Server name
	 * @return Server of specified type with a given name
	 */
	public <T extends Server> T getServer(Class<T> clazz, String name) {
		for (Server server : getServers()){
			if (server.isValid() && server.getLabel().getName().equals(name)){
				return server.getAdapter(clazz);
			}
		}
		log.info("Requested server '" + name + "' was not found on Servers view");
		log.info("Available servers are: " + LoggingUtils.format(getServersNames()));
		throw new EclipseLayerException("There is no server with name " + name);
	}

	/**
	 * Gets the servers tree.
	 *
	 * @return the servers tree
	 */
	protected Tree getServersTree(){
		activate();
		return new DefaultTree(this);
	}
	
	private Object[] getServersNames() {
		List<Server> servers = getServers();
		Object[] names = new Object[servers.size()];
		
		for (int i = 0; i < servers.size(); i++){
			names[i] = servers.get(i).getLabel().getName();
		}
		
		return names;
	}
}
