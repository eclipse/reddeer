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

import static org.eclipse.reddeer.common.wait.WaitProvider.waitUntil;
import static org.eclipse.reddeer.common.wait.WaitProvider.waitWhile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.IPublishListener;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.IServerListener;
import org.eclipse.wst.server.core.ServerEvent;
import org.eclipse.wst.server.ui.IServerModule;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.reddeer.common.adaptable.RedDeerAdaptable;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.GroupWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.handler.ItemHandler;
import org.eclipse.reddeer.core.handler.WidgetHandler;
import org.eclipse.reddeer.eclipse.condition.ServerExists;
import org.eclipse.reddeer.eclipse.condition.ServerHasPublishState;
import org.eclipse.reddeer.eclipse.condition.ServerHasState;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerPublishState;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerState;
import org.eclipse.reddeer.eclipse.wst.server.ui.editor.ServerEditor;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesDialog;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Abstract implementation of {@link Server} interface.
 * 
 * @author Lucia Jelinkova, mlabuda@redhat.com
 * 
 */
public abstract class AbstractServer implements Server, RedDeerAdaptable<Server> {
	
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
		new ContextMenuItem("Open").select();
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
		
		PublishListenerCondition listenerCondition = new PublishListenerCondition();
		try{
			new ContextMenuItem("Publish").select();
			waitForPublish(listenerCondition);
		} finally {
			cleanupPublishListener(listenerCondition.getPublishListener());
		}
	}
	
	@Override
	public void clean() {
		select();
		log.info("Clean server '" + getLabel().getName() + "'");
		
		PublishListenerCondition listenerCondition = new PublishListenerCondition();
		
		try{
			new ContextMenuItem("Clean...").select();
			Shell serverShell = new DefaultShell("Server");
			new PushButton("OK").click();
			new WaitWhile(new ShellIsAvailable(serverShell));
			
			waitForPublish(listenerCondition);
		} finally {
			cleanupPublishListener(listenerCondition.getPublishListener());
		}
	}
	
	@SuppressWarnings("restriction")
	private void cleanupServerListener(IServerListener listener){
		if(listener != null){
			org.eclipse.wst.server.core.internal.Server srv = getEclipseServer();
			srv.removeServerListener(listener);
		}
	}
	
	
	@SuppressWarnings("restriction")
	private void cleanupPublishListener(IPublishListener listener){
		if(listener != null){
			org.eclipse.wst.server.core.internal.Server srv = getEclipseServer();
			srv.removePublishListener(listener);
		}
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

		new ContextMenuItem("Delete").select();
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
		new ContextMenuItem(ADD_AND_REMOVE).select();
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
	public <T extends ServerModule> T getModule(Class<T> clazz, String name) {
		return getModule(clazz, new IsEqual<String>(name));
	}
	
	@Override
	public <T extends ServerModule> T getModule(Class<T> clazz, Matcher<String> stringMatcher) {
		ServerModule module = getModule(stringMatcher);
		return module.getAdapter(clazz);
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
	public <T extends ServerModule> List<T> getModules(Class<T> clazz) {
		List<ServerModule> modules = getModules();
		return modules.stream().map(t -> t.getAdapter(clazz)).collect(Collectors.toList());
	}
	
	@Override
	public List<ServerModule> getModules() {
		activate();
		final List<ServerModule> modules = new ArrayList<ServerModule>();

		for (final TreeItem item : treeItem.getItems()) {
			Object data = ItemHandler.getInstance().getData(item.getSWTWidget());
			if (data instanceof IModule || data instanceof IServerModule) {
				modules.add(createServerModule(item));
			}
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
	
	
	class ServerListenerCondition extends AbstractWaitCondition {
		
		private int serverState;
		private String mode;
		
		private boolean finished = false;
		private IServerListener serverListener;
		
		@SuppressWarnings("restriction")
		public ServerListenerCondition(int serverState, String mode) {
			this.serverState = serverState;
			this.mode = mode;
			
			org.eclipse.wst.server.core.internal.Server srv = getEclipseServer();
			serverListener = new IServerListener() {
				
				@Override
				public void serverChanged(ServerEvent arg0) {
					if(serverState == arg0.getServer().getServerState() && 
						(mode == null || mode.equals(arg0.getServer().getMode()))){
						finished = true;
					}
					
				}
			};
			
			srv.addServerListener(serverListener);
		}
		
		public IServerListener getServerListener(){
			return serverListener;
		}

		@Override
		public boolean test() {
			return finished;
		}
		
		@Override
		public String description() {
			if(mode != null){
				return "Server has state "+serverState+" and mode "+mode;
			}
			return "Server has state "+serverState;
		}
		
	}
	
	class PublishListenerCondition extends AbstractWaitCondition {
		
		private boolean finished = false;
		private IPublishListener publishListener;
		
		@SuppressWarnings("restriction")
		public PublishListenerCondition() {
			org.eclipse.wst.server.core.internal.Server srv = getEclipseServer();
			publishListener = new IPublishListener() {
				
				@Override
				public void publishStarted(IServer arg0) {
					
				}
				
				@Override
				public void publishFinished(IServer arg0, IStatus arg1) {
					finished = true;
					
				}
			};
			
			srv.addPublishListener(publishListener);
		}
		
		public IPublishListener getPublishListener(){
			return publishListener;
		}

		@Override
		public boolean test() {
			return finished;
		}
		
		@Override
		public String description() {
			return "Server publish finished";
		}
		
	}
	
	/**
	 * Wait for publish.
	 */
	protected void waitForPublish(PublishListenerCondition listenerCondition) {
		new GroupWait(getServerPublishTimeout(), waitUntil(listenerCondition), 
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
		
		select();
		ServerListenerCondition listenerCondition = null;
		switch(resultState) {
			case STARTED: listenerCondition = new ServerListenerCondition(IServer.STATE_STARTED, "run");
				break;
			case STOPPED: listenerCondition = new ServerListenerCondition(IServer.STATE_STOPPED, null);
				break;
			case DEBUGGING: listenerCondition = new ServerListenerCondition(IServer.STATE_STARTED, "debug");
				break;
			case PROFILING: listenerCondition = new ServerListenerCondition(IServer.STATE_STARTED, "profile");
				break;
			default: throw new EclipseLayerException("Unknown state "+resultState);
		}
		try{
			new ContextMenuItem(menuItem).select();
		
			new GroupWait(getServerStateChangeTimeout(), waitUntil(listenerCondition), 
					waitUntil(new ServerHasState(this, resultState)), waitWhile(new JobIsRunning()));
			
			log.debug("Operate server's state finished, the result server's state is: '" + getLabel().getState() + "'");
		} finally {
			cleanupServerListener(listenerCondition.getServerListener());
		}
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
	
	@SuppressWarnings("restriction")
	private org.eclipse.wst.server.core.internal.Server getEclipseServer(){
		Object o = WidgetHandler.getInstance().getData(treeItem.getSWTWidget());
		return (org.eclipse.wst.server.core.internal.Server)o;
	}
}
