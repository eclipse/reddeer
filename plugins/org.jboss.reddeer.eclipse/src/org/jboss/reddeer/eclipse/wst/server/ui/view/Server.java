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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.ui.IServerModule;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.eclipse.condition.ServerExists;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.wst.server.ui.editor.ServerEditor;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerPublishState;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesDialog;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

/**
 * Represents a server on {@link ServersView}. Contains both, the server data
 * (name, state, status) and operations that can be invoked on server (Start,
 * Stop, Delete etc.). For operations that can be invoked on project added to
 * server see {@link ServerModule}
 * 
 * @author Lucia Jelinkova
 * 
 */
public class Server {

	private static final TimePeriod TIMEOUT = TimePeriod.VERY_LONG;

	private static final String ADD_AND_REMOVE = "Add and Remove...";

	private static final Logger log = Logger.getLogger(Server.class);

	protected TreeItem treeItem;
	
	protected ServersView view;
	
	/**
	 * Instantiates a new server.
	 *
	 * @param treeItem the tree item
	 * @param view the view
	 */
	protected Server(TreeItem treeItem, ServersView view) {
		this.treeItem = treeItem;
		this.view = view;
	}

	/**
	 * Returns a server label.
	 * 
	 * @return Server label
	 */
	public ServerLabel getLabel(){
		activate();
		return new ServerLabel(treeItem);
	}

	/**
	 * Returns list of modules.
	 * 
	 * @return List of modules
	 */
	public List<ServerModule> getModules() {
		activate();
		final List<ServerModule> modules = new ArrayList<ServerModule>();

		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				for (TreeItem item : treeItem.getItems()){
					org.eclipse.swt.widgets.TreeItem swtItem = item.getSWTWidget();
					Object data = swtItem.getData();
					if (data instanceof IModule || data instanceof IServerModule){
						modules.add(createServerModule(item));
					}
				}				
			}
		});

		return modules;
	}

	/**
	 * Return a module with a given name.
	 * 
	 * @param name Module name
	 * @return Module
	 */
	public ServerModule getModule(String name) {
		return getModule(new IsEqual<String>(name));
	}
	
	
	/**
	 * Return a module matching a given matcher.
	 * 
	 * @param stringMatcher
	 *            matcher to match against module name.
	 * @return Module
	 */
	public ServerModule getModule(Matcher<String> stringMatcher) {
		activate();
		for (ServerModule module : getModules()) {
			if (stringMatcher.matches(module.getLabel().getName())) {
				return module;
			}
		}
		throw new EclipseLayerException("There is no module with name matching matcher " + stringMatcher.toString()
				+ " on server " + getLabel().getName());
	}

	/**
	 * Opens a dialog for adding/removing modules.
	 * 
	 * @return Dialog for adding/removing modules
	 */
	public ModifyModulesDialog addAndRemoveModules() {
		activate();
		log.info("Add and remove modules of server");
		new ContextMenu(ADD_AND_REMOVE).select();
		return new ModifyModulesDialog();
	}
	
	/**
	 * Opens the server editor.
	 * 
	 * @return Server editor
	 */
	public ServerEditor open() {
		activate();
		log.info("Open server's editor");
		new ContextMenu("Open").select();
		return createServerEditor(getLabel().getName());
	}

	/**
	 * Starts the server.
	 */
	public void start() {
		activate();
		log.info("Start server " + getLabel().getName());
		if (!ServerState.STOPPED.equals(getLabel().getState())){
			throw new ServersViewException("Cannot start server because it is not stopped");
		}
		operateServerState("Start", ServerState.STARTED);
	}

	/**
	 * Debugs the server.
	 */
	public void debug() {
		activate();
		log.info("Start server in debug '" + getLabel().getName() + "'");
		if (!ServerState.STOPPED.equals(getLabel().getState())){
			throw new ServersViewException("Cannot debug server because it is not stopped");
		}
		operateServerState("Debug", ServerState.DEBUGGING);
	}

	/**
	 * Profiles the server.
	 */
	public void profile() {
		activate();
		log.info("Start server in profiling mode '" + getLabel().getName() + "'");
		if (!ServerState.STOPPED.equals(getLabel().getState())){
			throw new ServersViewException("Cannot profile server because it is not stopped");
		}
		operateServerState("Profile", ServerState.PROFILING);
	}

	/**
	 * Restarts the server.
	 */
	public void restart() {
		activate();
		log.info("Restart server '" + getLabel().getName() + "'");
		if (!getLabel().getState().isRunningState()){
			throw new ServersViewException("Cannot restart server because it is not running");
		}
		operateServerState("Restart", ServerState.STARTED);
	}

	/**
	 * Restarts the server in debug.
	 */
	public void restartInDebug() {
		activate();
		log.info("Restart server in debug '" + getLabel().getName() + "'");
		if (!getLabel().getState().isRunningState()){
			throw new ServersViewException("Cannot restart server in debug because it is not running");
		}
		operateServerState("Restart in Debug", ServerState.DEBUGGING);
	}

	/**
	 * Restarts the server in profile.
	 */
	public void restartInProfile() {
		activate();
		log.info("Restart server in profile '" + getLabel().getName() + "'");
		if (!getLabel().getState().isRunningState()){
			throw new ServersViewException("Cannot restart server in profile because it is not running");
		}
		operateServerState("Restart in Profile", ServerState.PROFILING);
	}

	/**
	 * Stops the server.
	 */
	public void stop() {
		activate();
		log.info("Stop server '" + getLabel().getName() + "'");
		ServerState state = getLabel().getState();
		if (!ServerState.STARTING.equals(state) && !state.isRunningState()){
			throw new ServersViewException("Cannot stop server because it not running");
		}
		operateServerState("Stop", ServerState.STOPPED);
	}

	/**
	 * Publishes the server.
	 */
	public void publish() {
		activate();
		log.info("Publish server '" + getLabel().getName() + "'");
		new ContextMenu("Publish").select();
		waitForPublish();
	}

	/**
	 * Cleans the server.
	 */
	public void clean() {
		activate();
		log.info("Clean server '" + getLabel().getName() + "'");
		new ContextMenu("Clean...").select();
		new DefaultShell("Server");
		new PushButton("OK").click();
		waitForPublish();
	}

	/**
	 * Deletes the server. The server is not stop first.
	 */
	public void delete() {
		activate();
		delete(false);
	}

	/**
	 * Deletes the server. You can specify whether the server is stop first.
	 * 
	 * @param stopFirst Indicates whether the server is stop first.
	 */
	public void delete(boolean stopFirst) {
		activate();
		final String name = getLabel().getName();
		log.info("Delete server '" + name + "'. Stop server first: " + stopFirst);
		ServerState state = getLabel().getState();

		new ContextMenu("Delete").select();	
		new DefaultShell("Delete Server");
		if (!ServerState.STOPPED.equals(state) && !ServerState.NONE.equals(state)){
			new CheckBox().toggle(stopFirst);
		}
		new PushButton("OK").click();
		new WaitWhile(new ServerExists(name), TIMEOUT);
		new WaitWhile(new JobIsRunning(), TIMEOUT);
	}

	/**
	 * Selects the server.
	 */
	protected void select() {
		treeItem.select();
	}

	/**
	 * Operate server state.
	 *
	 * @param menuItem the menu item
	 * @param resultState the result state
	 */
	protected void operateServerState(String menuItem, ServerState resultState){
		log.debug("Triggering action: " + menuItem + " on server " + getLabel().getName());
		ServerState currentState = getLabel().getState();
		
		select();		
		new ContextMenu(menuItem).select();
		
		log.trace("Action on server triggered. Waiting while current state of server gets changed");
		// Wait while server state change takes effect and then wait while state changing job is running
		new WaitWhile(new ServerStateCondition(currentState), TIMEOUT);
		new WaitWhile(new JobIsRunning(), TIMEOUT);
		
		log.trace("Waiting until server state gets to result state.");
		// Wait until server gets to correct state and then wait for running jobs
		new WaitUntil(new ServerStateCondition(resultState), TIMEOUT);
		new WaitWhile(new JobIsRunning(), TIMEOUT);

		log.trace("Performing final check on correct server state.");
		// Test state one more time, because state is depending on settings e.g. port accessibility and something
		// could go wrong at changing state and server could fail to get to the state 
		new WaitUntil(new ServerStateCondition(resultState), TimePeriod.NONE);
		
		log.debug("Operate server's state finished, the result server's state is: '" + getLabel().getState() + "'");
	}

	/**
	 * Wait for publish.
	 */
	protected void waitForPublish(){
		new WaitUntil(new JobIsRunning(), TIMEOUT);
		new WaitWhile(new ServerPublishStateCondition(ServerPublishState.PUBLISHING), TIMEOUT);
		new WaitUntil(new ServerPublishStateCondition(ServerPublishState.SYNCHRONIZED), TIMEOUT);
	}
	
	/**
	 * Creates the server module.
	 *
	 * @param item the item
	 * @return the server module
	 */
	protected ServerModule createServerModule(TreeItem item){
		return new ServerModule(item, view);
	}
	
	/**
	 * Creates the server editor.
	 *
	 * @param title the title
	 * @return the server editor
	 */
	protected ServerEditor createServerEditor(String title){
		return new ServerEditor(title);
	}
	
	/**
	 * Activate.
	 */
	protected void activate(){
		view.activate();
		select();
	}

	private class ServerStateCondition extends AbstractWaitCondition {

		private ServerState expectedState;

		private ServerStateCondition(ServerState expectedState) {
			this.expectedState = expectedState;
		}

		@Override
		public boolean test() {
			return expectedState.equals(getLabel().getState());
		}

		@Override
		public String description() {
			return "server's state is: " + expectedState.getText();
		}
		
		@Override
		public String errorMessage() {
			return "Server expected state was " + expectedState.getText() + " but current state is "
					+ getLabel().getState().getText(); 
		}
	}

	private class ServerPublishStateCondition extends AbstractWaitCondition {

		private ServerPublishState expectedState;

		private ServerPublishStateCondition(ServerPublishState expectedState) {
			this.expectedState = expectedState;
		}

		@Override
		public boolean test() {
			return expectedState.equals(getLabel().getPublishState());
		}

		@Override
		public String description() {
			return "server's publish state is " + expectedState.getText();
		}
		
		@Override
		public String errorMessage() {
			return "Server expected state was " + expectedState.getText() + " but current state is "
					+ getLabel().getPublishState().getText(); 
		}
	}
	
	/**
	 * Returns true when underlying treeItem is not null and is not disposed.
	 *
	 * @return true, if is valid
	 */
	public boolean isValid(){
		return treeItem != null && !treeItem.isDisposed();
	}
	
}

