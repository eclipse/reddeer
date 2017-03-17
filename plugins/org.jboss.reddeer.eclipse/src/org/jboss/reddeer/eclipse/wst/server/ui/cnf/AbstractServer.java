/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.ui.IServerModule;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.common.adaptable.RedDeerAdaptable;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.wait.GroupWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.eclipse.condition.ServerExists;
import org.jboss.reddeer.eclipse.condition.ServerHasPublishState;
import org.jboss.reddeer.eclipse.condition.ServerHasState;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerPublishState;
import org.jboss.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerState;
import org.jboss.reddeer.eclipse.wst.server.ui.editor.ServerEditor;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesDialog;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Abstract implementation of {@link Server} interface.
 * 
 * @author Lucia Jelinkova, mlabuda@redhat.com
 * 
 */
public class AbstractServer implements Server, RedDeerAdaptable<Server> {
	
	private TimePeriod stateChangeTimeout = TimePeriod.getCustom(600);
	
	private TimePeriod publishTimeout = TimePeriod.VERY_LONG;

	protected static final String ADD_AND_REMOVE = "Add and Remove...";

	protected static final Logger log = Logger.getLogger(AbstractServer.class);

	protected TreeItem treeItem;

	protected ServersView2 view;

	/**
	 * Creates a new abstract server.
	 *
	 * @param treeItem
	 *            the tree item
	 */
	protected AbstractServer(TreeItem treeItem) {
		this.treeItem = treeItem;
		view = new ServersView2();
	}
	
	@Override
	public TreeItem getTreeItem() {
		return treeItem;
	}
	
	@Override
	public boolean isValid() {
		return treeItem != null && !treeItem.isDisposed();
	}
	
	@Override
	public ServerLabel getLabel() {
		activate();
		return new ServerLabel(treeItem);
	}
	
	@Override
	public void select() {
		activate();
		treeItem.select();
	}
	
	@Override
	public ServerEditor open() {
		select();
		log.info("Open server's editor");
		new ContextMenu("Open").select();
		return createServerEditor(getLabel().getName());
	}
	
	@Override
	public void start() {
		select();
		log.info("Start server " + getLabel().getName());
		if (!ServerState.STOPPED.equals(getLabel().getState())) {
			throw new ServersViewException("Cannot start server because it is not stopped");
		}
		operateServerState("Start", ServerState.STARTED);
	}
	
	@Override
	public void stop() {
		select();
		log.info("Stop server '" + getLabel().getName() + "'");
		ServerState state = getLabel().getState();
		if (!ServerState.STARTING.equals(state) && !state.isRunningState()) {
			throw new ServersViewException("Cannot stop server because it not running");
		}
		operateServerState("Stop", ServerState.STOPPED);
	}
	
	@Override
	public void restart() {
		select();
		log.info("Restart server '" + getLabel().getName() + "'");
		if (!getLabel().getState().isRunningState()) {
			throw new ServersViewException("Cannot restart server because it is not running");
		}
		operateServerState("Restart", ServerState.STARTED);
	}
	
	@Override
	public void restartInDebug() {
		select();
		log.info("Restart server in debug '" + getLabel().getName() + "'");
		if (!getLabel().getState().isRunningState()) {
			throw new ServersViewException("Cannot restart server in debug because it is not running");
		}
		operateServerState("Restart in Debug", ServerState.DEBUGGING);
	}

	@Override
	public void restartInProfile() {
		select();
		log.info("Restart server in profile '" + getLabel().getName() + "'");
		if (!getLabel().getState().isRunningState()) {
			throw new ServersViewException("Cannot restart server in profile because it is not running");
		}
		operateServerState("Restart in Profile", ServerState.PROFILING);
	}
	
	@Override
	public void debug() {
		select();
		log.info("Start server in debug '" + getLabel().getName() + "'");
		if (!ServerState.STOPPED.equals(getLabel().getState())) {
			throw new ServersViewException("Cannot debug server because it is not stopped");
		}
		operateServerState("Debug", ServerState.DEBUGGING);
	}

	@Override
	public void profile() {
		select();
		log.info("Start server in profiling mode '" + getLabel().getName() + "'");
		if (!ServerState.STOPPED.equals(getLabel().getState())) {
			throw new ServersViewException("Cannot profile server because it is not stopped");
		}
		operateServerState("Profile", ServerState.PROFILING);
	}
	
	@Override
	public void publish() {
		select();
		log.info("Publish server '" + getLabel().getName() + "'");
		new ContextMenu("Publish").select();
		waitForPublish();
	}

	@Override
	public void clean() {
		select();
		log.info("Clean server '" + getLabel().getName() + "'");
		new ContextMenu("Clean...").select();
		Shell serverShell = new DefaultShell("Server");
		new PushButton("OK").click();
		new WaitWhile(new ShellIsAvailable(serverShell));
		waitForPublish();
	}

	@Override
	public void delete() {
		select();
		delete(false);
	}

	@Override
	public void delete(boolean stopFirst) {;
		select();
		final String name = getLabel().getName();
		log.info("Delete server '" + name + "'. Stop server first: " + stopFirst);
		ServerState state = getLabel().getState();

		new ContextMenu("Delete").select();
		Shell deleteShell  =new DefaultShell("Delete Server");
		if (!ServerState.STOPPED.equals(state) && !ServerState.NONE.equals(state)) {
			new CheckBox().toggle(stopFirst);
		}
		new PushButton("OK").click();
		new WaitWhile(new ShellIsAvailable(deleteShell));
		// basically there is a stop action invoked, therefore waiting with decreasing time out is included
		long startTimeOfAction = System.currentTimeMillis();
		new WaitWhile(new ServerExists(name), getServerStateChangeTimeout());
		new WaitWhile(new JobIsRunning(), 
				getRemainingTimeoutPeriod(getServerStateChangeTimeout(), startTimeOfAction));
	}
	
	@Override
	public TimePeriod getServerStateChangeTimeout() {
		return stateChangeTimeout;
	}
	
	@Override
	public void setServerStateChangeTimeout(TimePeriod timeout) {
		stateChangeTimeout = timeout;
	}
	
	@Override
	public TimePeriod getServerPublishTimeout() {
		return publishTimeout;
	}
	
	@Override
	public void setServerPublishTimeout(TimePeriod timeout) {
		publishTimeout = timeout;
	}
	
	@Override
	public ModifyModulesDialog addAndRemoveModules() {
		select();
		log.info("Add and remove modules of server");
		new ContextMenu(ADD_AND_REMOVE).select();
		return new ModifyModulesDialog();
	}

	@Override
	public void activate() {
		view.activate();
	}

	@Override
	public ServerModule getModule(String name) {
		return getModule(new IsEqual<String>(name));
	}

	@Override
	public ServerModule getModule(Matcher<String> stringMatcher) {
		for (ServerModule module : getModules()) {
			if (stringMatcher.matches(module.getLabel().getName())) {
				return module;
			}
		}
		throw new EclipseLayerException("There is no module with name matching matcher " + stringMatcher.toString()
				+ " on server " + getLabel().getName());
	}
	
	@Override
	public List<ServerModule> getModules() {
		activate();
		final List<ServerModule> modules = new ArrayList<ServerModule>();

		for (final TreeItem item : treeItem.getItems()) {
			Display.syncExec(new Runnable() {

				@Override
				public void run() {
					org.eclipse.swt.widgets.TreeItem swtItem = item.getSWTWidget();
					Object data = swtItem.getData();
					if (data instanceof IModule || data instanceof IServerModule) {
						modules.add(createServerModule(item));
					}
				}
			});
		}

		return modules;
	}

	@Override
	public Object[] getAdapterConstructorArguments() {
		return new Object[] {treeItem};
	}
	
	@Override
	public Class<?>[] getAdapterConstructorClasses() {
		return new Class<?>[] {TreeItem.class};
	}
	
	/**
	 * Wait for publish.
	 */
	protected void waitForPublish() {
		new GroupWait(getServerPublishTimeout(), waitUntil(new JobIsRunning()),
				waitWhile(new ServerHasPublishState(this, ServerPublishState.PUBLISHING)),
				waitUntil(new ServerHasPublishState(this, ServerPublishState.SYNCHRONIZED)),
				waitWhile(new JobIsRunning()));
	}	
	
	/**
	 * Operate server state.
	 *
	 * @param menuItem
	 *            the menu item
	 * @param resultState
	 *            the result state
	 */
	protected void operateServerState(String menuItem, ServerState resultState) {
		log.debug("Triggering action: " + menuItem + " on server " + getLabel().getName());
		ServerState currentState = getLabel().getState();
		TimePeriod remainingTimeout = getServerStateChangeTimeout();
		
		select();
		new ContextMenu(menuItem).select();

		log.trace("Action on server triggered. Waiting while current state of server gets changed");
		// Wait while server state change takes effect and then wait while state
		// changing job is running
		remainingTimeout = new GroupWait(remainingTimeout, waitWhile(new ServerHasState(this, currentState)), 
				waitWhile(new JobIsRunning())).getRemainingTimeout();
		
		log.trace("Waiting until server state gets to result state.");
		// Wait until server gets to correct state and then wait for running
		// jobs
		new GroupWait(remainingTimeout, waitUntil(new ServerHasState(this, resultState)), 
				waitWhile(new JobIsRunning()));

		log.trace("Performing final check on correct server state.");
		// Test state one more time, because state is depending on settings e.g.
		// port accessibility and something
		// could go wrong at changing state and server could fail to get to the
		// state
		new WaitUntil(new ServerHasState(this, resultState), TimePeriod.NONE);

		log.debug("Operate server's state finished, the result server's state is: '" + getLabel().getState() + "'");
	}

	/**
	 * Gets remaining time to time out based on provided start time of the action and old timeout.
	 * 
	 * @param oldTimeout value in seconds of old remaining timeout
	 * @param startTimeOfAction time in milliseconds when the action started
	 * @return remaining TimePeriod until whole timeout is fulfilled
	 */
	private TimePeriod getRemainingTimeoutPeriod(TimePeriod oldTimeout, long startTimeOfAction) {
		long diffTime = Math.round((System.currentTimeMillis() - startTimeOfAction) / 1000);
		long remainingTimeout = oldTimeout.getSeconds() - diffTime;
		if (remainingTimeout < 0) {
			return TimePeriod.NONE;
		}
		return TimePeriod.getCustom(remainingTimeout);
	}
	
	private ServerModule createServerModule(TreeItem item) {
		return new ServerModule(item, view);
	}
	
	private ServerEditor createServerEditor(String title) {
		return new ServerEditor(title);
	}
}
