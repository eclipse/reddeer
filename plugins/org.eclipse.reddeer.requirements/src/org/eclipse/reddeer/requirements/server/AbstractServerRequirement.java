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

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.eclipse.wst.server.ui.Runtime;
import org.eclipse.reddeer.eclipse.wst.server.ui.RuntimePreferencePage;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.Server;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersView2;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerState;
import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;

/**
 * 
 * @author Pavol Srna, Radoslav Rabara
 * 
 * Abstract base class for server requirements which provides some useful methods for 
 * server configuration.
 *
 */
public abstract class AbstractServerRequirement {
	
	private static final Logger LOGGER = Logger.getLogger(AbstractServerRequirement.class);
	
	/**
	 * Setup server state.
	 *
	 * @param requiredState the required state
	 * @throws ConfiguredServerNotFoundException the configured server not found exception
	 */
	protected void setupServerState(ServerRequirementState requiredState) {
		LOGGER.info("Checking the state of the server '" + getServerName() + "'");
		
		Server serverInView = getConfiguredServer();
		
		ServerState state = serverInView.getLabel().getState();
		switch(state) {
			case STARTED:
				if(requiredState == ServerRequirementState.STOPPED)
					serverInView.stop();
				break;
			case STOPPED:
				if(requiredState == ServerRequirementState.RUNNING)
					serverInView.start();
				break;
			default:
				new AssertionError("It was expected to have server in "
						+ ServerState.STARTED + " or " + ServerState.STOPPED
						+ "state." + " Not in state "+state+".");
		}
	}
	
	/**
	 * Removes server and its runtime.
	 */
	protected void removeServerAndRuntime() {
		Server serverInView = getConfiguredServer();
		if(serverInView == null){
			return;
		}
		//remove server added by last requirement
		serverInView.delete(true);
		//remove runtime
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		RuntimePreferencePage runtimePage = new RuntimePreferencePage(preferenceDialog);
		preferenceDialog.select(runtimePage);
		runtimePage.removeRuntime(new Runtime(getRuntimeName(), "test"));
		preferenceDialog.ok();
	}
	
	/**
	 * Gets the configured server or null if it does not exist
	 *
	 * @return the configured server
	 */
	protected org.eclipse.reddeer.eclipse.wst.server.ui.cnf.Server getConfiguredServer() {
		ServersView2 serversView = new ServersView2();
		serversView.open();
		for(Server server: serversView.getServers()){
			if(getServerName().equals(server.getLabel().getName())){
				return server;
			}
		}
		return null;
	}

	/**
	 * Checks if is last configured server present.
	 *
	 * @return true, if is last configured server present
	 */
	protected boolean isLastConfiguredServerPresent() {
		return getConfiguredServer() != null;
	}
	
	/**
	 * Gets the server name label text.
	 *
	 * @return server name label text
	 */
	public abstract String getServerName();

	/**
	 * Gets the runtime name label text.
	 *
	 * @return runtime name label text
	 */
	public abstract String getRuntimeName();
	
	public abstract RequirementConfiguration getConfiguration();
}
