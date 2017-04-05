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
package org.jboss.reddeer.eclipse.wst.server.ui.cnf;

import static org.jboss.reddeer.common.wait.WaitProvider.waitUntil;
import static org.jboss.reddeer.common.wait.WaitProvider.waitWhile;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.GroupWait;
import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.WidgetIsDisposed;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.eclipse.condition.ServerModuleHasState;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerState;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Represents a module assigned to server {@link Server} on {@link ServersView2}.
 * 
 * @author Lucia Jelinkova
 *
 */
public class ServerModule { 

	private static final Logger log = Logger.getLogger(ServerModule.class);
	
	protected TreeItem treeItem;
	
	protected ServersView2 view;
	
	/**
	 * Instantiates a new server module.
	 *
	 * @param item the item
	 * @param view the view
	 */
	protected ServerModule(TreeItem item, ServersView2 view) {
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
		select();
		return new ModuleLabel(treeItem);
	}

	/**
	 * Removes the server module.
	 */
	public void remove(){
		select();
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
		WithTextMatcher shellMatcher = new WithTextMatcher(new RegexMatcher("Publishing to .*"));
		new WaitUntil(new ShellIsAvailable(shellMatcher), TimePeriod.NORMAL,false);
		new WaitWhile(new ShellIsAvailable(shellMatcher), TimePeriod.LONG);
	}
	
	/**
	 * Activate.
	 */
	protected void activate(){
		view.activate();
	}
	
	/**
	 * Select the server module
	 */
	public void select(){
		activate();
		treeItem.select();
	}
	
	/**
	 * Check if server module can be started (start menu item is enabled)
	 * @return true if server module can be started, false otherwise
	 */
	public boolean canStart(){
		select();
		return new ContextMenu("Start").isEnabled();
	}
	
	/**
	 * Check if server module can be restarted (restart menu item is enabled)
	 * @return true if server module can be restarted, false otherwise
	 */
	public boolean canRestart(){
		select();
		return new ContextMenu("Restart").isEnabled();
	}
	
	/**
	 * Check if server module can be stopped (stop menu item is enabled)
	 * @return true if server module can be stopped, false otherwise
	 */
	public boolean canStop(){
		select();
		return new ContextMenu("Stop").isEnabled();
	}
	
	/**
	 * Start server module
	 */
	public void start(){
		log.info("Start server module with name '" + getLabel().getName() + "'");
		select();
		new ContextMenu("Start").select();
		new WaitWhile(new JobIsRunning());
		new WaitUntil(new ServerModuleHasState(this, ServerState.STARTED));
		new WaitWhile(new JobIsRunning());
	}
	
	/**
	 * Stop server module
	 */
	public void stop(){
		log.info("Stop server module with name '" + getLabel().getName() + "'");
		select();
		new ContextMenu("Stop").select();
		new WaitWhile(new JobIsRunning());
		new WaitUntil(new ServerModuleHasState(this, ServerState.STOPPED));
		new WaitWhile(new JobIsRunning());
	}
	
	/**
	 * Restart server module
	 */
	public void restart(){
		log.info("Restart server module with name '" + getLabel().getName() + "'");
		select();
		new ContextMenu("Restart").select();
		new WaitWhile(new JobIsRunning());
		new WaitUntil(new ServerModuleHasState(this, ServerState.STARTED));
		new WaitWhile(new JobIsRunning());
	}
}

