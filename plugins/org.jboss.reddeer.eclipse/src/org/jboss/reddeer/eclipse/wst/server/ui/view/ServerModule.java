/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.wst.server.ui.view;

import static org.jboss.reddeer.common.wait.WaitProvider.waitUntil;
import static org.jboss.reddeer.common.wait.WaitProvider.waitWhile;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.GroupWait;
import org.jboss.reddeer.core.condition.WidgetIsDisposed;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;

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
	 * Instantiates a new server module.
	 *
	 * @param item the item
	 * @param view the view
	 */
	protected ServerModule(TreeItem item, ServersView view) {
		if (item == null) {
			throw new IllegalArgumentException("item can't be null");
		}
		this.treeItem = item;
		this.view = view;
	}

	/**
	 * Returns module's label as {@link ModuleLabel}.
	 *
	 * @return the label
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
		log.info("Remove server module with name '" + getLabel().getName() + "'");
		new ShellMenu("Edit", "Delete").select();
		Shell serverShell = new DefaultShell("Server");
		new PushButton("OK").click();
		new GroupWait(waitWhile(new ShellIsAvailable(serverShell)),
				waitWhile(new JobIsRunning()), waitUntil(new WidgetIsDisposed(treeItem.getSWTWidget())));
		treeItem = null;
	}
	
	/**
	 * Activate.
	 */
	protected void activate(){
		view.activate();
		treeItem.select();
	}
	
	/**
	 * Select the server module
	 */
	public void select(){
		treeItem.select();
	}
}
