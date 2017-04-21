/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.requirements.server;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.wst.server.ui.Runtime;
import org.eclipse.reddeer.eclipse.wst.server.ui.RuntimePreferencePage;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersView2;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerState;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;

/**
 * 
 * @author Pavol Srna, Radoslav Rabara
 * 
 * Abstract base class for server requirements which provides some useful methods for 
 * server configuration.
 * <ul>
 * 	<li>void setupServerState(ServerReqState requiredState, ConfiguredServerInfo lastServerConfig)</li>
 * 	<li>void removeLastRequiredServer(ConfiguredServerInfo lastServerConfig)</li>
 * 	<li>boolean isLastConfiguredServerPresent(ConfiguredServerInfo lastServerConfig)</li>
 * 	<li>String getServerTypeLabelText(IServerReqConfig config)</li>
 * 	<li>String getServerNameLabelText(IServerReqConfig config)</li>
 * 	<li>String getRuntimeNameLabelText(IServerReqConfig config)</li>
 * </ul>
 *
 */
public abstract class ServerReqBase {
	
	private static final Logger LOGGER = Logger.getLogger(ServerReqBase.class);
	
	private ServersView2 serversView;
	
	/**
	 * Setup server state.
	 *
	 * @param requiredState the required state
	 * @throws ConfiguredServerNotFoundException the configured server not found exception
	 */
	protected void setupServerState(ServerReqState requiredState) throws ConfiguredServerNotFoundException {
		LOGGER.info("Checking the state of the server '"+getConfiguredConfig().getServerName()+"'");
		
		org.eclipse.reddeer.eclipse.wst.server.ui.cnf.Server serverInView = getConfiguredServer();
		
		ServerState state = serverInView.getLabel().getState();
		switch(state) {
			case STARTED:
				if(requiredState == ServerReqState.STOPPED)
					serverInView.stop();
				break;
			case STOPPED:
				if(requiredState == ServerReqState.RUNNING)
					serverInView.start();
				break;
			default:
				new AssertionError("It was expected to have server in "
						+ ServerState.STARTED + " or " + ServerState.STOPPED
						+ "state." + " Not in state "+state+".");
		}
	}
	
	/**
	 * Removes given server and its runtime.
	 */
	protected void removeLastRequiredServerAndRuntime() {
		try {
			org.eclipse.reddeer.eclipse.wst.server.ui.cnf.Server serverInView = getConfiguredServer();
			//remove server added by last requirement
			serverInView.delete(true);
			removeRuntime();
		} catch(ConfiguredServerNotFoundException e) {
			//server had been already removed
		}
	}

	private void removeRuntime() {
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		RuntimePreferencePage runtimePage = new RuntimePreferencePage();
		preferenceDialog.select(runtimePage);
		String runtimeName = getRuntimeNameLabelText();
		runtimePage.removeRuntime(new Runtime(runtimeName, "test"));
		preferenceDialog.ok();
	}
	
	/**
	 * Gets the configured server.
	 *
	 * @return the configured server
	 * @throws ConfiguredServerNotFoundException the configured server not found exception
	 */
	protected org.eclipse.reddeer.eclipse.wst.server.ui.cnf.Server getConfiguredServer()
			throws ConfiguredServerNotFoundException {
		getServersView().open();
		if(getConfiguredConfig() == null){
			throw new ConfiguredServerNotFoundException("Server has already been removed");
		} 
		final String serverName = getConfiguredConfig().getServerName();
		try {
			return getServersView().getServer(serverName);
		} catch(EclipseLayerException e) {
			LOGGER.warn("Server \"" + serverName + "\" not found. It had been removed.");
			throw new ConfiguredServerNotFoundException("Server \"" + serverName + "\" not found.", e);
		}
	}

	/**
	 * Checks if is last configured server present.
	 *
	 * @return true, if is last configured server present
	 */
	protected boolean isLastConfiguredServerPresent() {
		try {
			getConfiguredServer();
		} catch(ConfiguredServerNotFoundException e) {
			return false;
		}
		return true;
	}
	

	/**
	 * Gets the server type label text.
	 *
	 * @return server type label text.
	 */
	public String getServerTypeLabelText() {
		return getConfig().getServerFamily().getLabel() + " "
				+ getConfig().getServerFamily().getVersion();
	}
	
	/**
	 * Gets the server name label text.
	 *
	 * @return server name label text
	 */
	public String getServerNameLabelText() {
		return getServerTypeLabelText() + " Server";
	}

	/**
	 * Gets the runtime name label text.
	 *
	 * @return runtime name label text
	 */
	public String getRuntimeNameLabelText() {
		return getServerTypeLabelText() + " Runtime";
	}

	/**
	 * Gets the servers view.
	 *
	 * @return the servers view
	 */
	protected ServersView2 getServersView() {
		if (serversView == null){
			serversView = createServersView();
		}
		return serversView;
	}	
	
	/**
	 * This method allows the subclasses to define own ServersView implementation and 
	 * thus create own implementation of Server and modify start/stop behavior. 
	 *
	 * @return the servers view
	 */
	protected ServersView2 createServersView() {
		return new ServersView2();
	}
	
	public abstract IServerReqConfig getConfig();
	
	public abstract ConfiguredServerInfo getConfiguredConfig();

	/**
	 * Exception thrown when configured server was not found.
	 */
	protected class ConfiguredServerNotFoundException extends RedDeerException {

		private static final long serialVersionUID = -1049073209937853734L;
		
		/**
		 * Instantiates a new configured server not found exception.
		 *
		 * @param message the message
		 */
		public ConfiguredServerNotFoundException(String message) {
			super(message);
		}

		/**
		 * Instantiates a new configured server not found exception.
		 *
		 * @param message the message
		 * @param cause the cause
		 */
		public ConfiguredServerNotFoundException(String message, Throwable cause) {
			super(message, cause);
		}
		
	}
}