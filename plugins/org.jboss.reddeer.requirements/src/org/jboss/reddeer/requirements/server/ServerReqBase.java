package org.jboss.reddeer.requirements.server;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.Runtime;
import org.jboss.reddeer.eclipse.wst.server.ui.RuntimePreferencePage;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersView;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;
import org.jboss.reddeer.common.exception.RedDeerException;

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
	
	
	protected void setupServerState(ServerReqState requiredState, ConfiguredServerInfo lastServerConfig) throws ConfiguredServerNotFoundException {
		LOGGER.info("Checking the state of the server '"+lastServerConfig.getServerName()+"'");
		
		org.jboss.reddeer.eclipse.wst.server.ui.view.Server serverInView = getConfiguredServer(lastServerConfig);
		
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
	 * @deprecated in 0.6 Use {@link #removeLastRequiredServerAndRuntime(ConfiguredServerInfo)}.
	 * 
	 * @param lastServerConfig
	 */
	protected void removeLastRequiredServer(ConfiguredServerInfo lastServerConfig) {
		try {
			org.jboss.reddeer.eclipse.wst.server.ui.view.Server serverInView = getConfiguredServer(lastServerConfig);
			//remove server added by last requirement
			serverInView.delete(true);
		} catch(ConfiguredServerNotFoundException e) {
			//server had been already removed
		}
	}
	
	/**
	 * Removes given server and its runtime.
	 * 
	 * @param lastServerConfig Server information.
	 */
	
	protected void removeLastRequiredServerAndRuntime(ConfiguredServerInfo lastServerConfig) {
		try {
			org.jboss.reddeer.eclipse.wst.server.ui.view.Server serverInView = getConfiguredServer(lastServerConfig);
			//remove server added by last requirement
			serverInView.delete(true);
			removeRuntime(lastServerConfig);
		} catch(ConfiguredServerNotFoundException e) {
			//server had been already removed
		}
	}

	private void removeRuntime(ConfiguredServerInfo lastServerConfig) {
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		RuntimePreferencePage runtimePage = new RuntimePreferencePage();
		preferenceDialog.select(runtimePage);
		String runtimeName = getRuntimeNameLabelText(lastServerConfig.getConfig());
		runtimePage.removeRuntime(new Runtime(runtimeName, "test"));
		preferenceDialog.ok();
	}
	
	private org.jboss.reddeer.eclipse.wst.server.ui.view.Server getConfiguredServer(ConfiguredServerInfo lastServerConfig)
			throws ConfiguredServerNotFoundException {
		ServersView serversView = new ServersView();
		serversView.open();
		if(lastServerConfig == null){
			throw new ConfiguredServerNotFoundException("Server has already been removed");
		} 
		final String serverName = lastServerConfig.getServerName();
		try {
			return serversView.getServer(serverName);
		} catch(EclipseLayerException e) {
			LOGGER.warn("Server \"" + serverName + "\" not found. It had been removed.");
			throw new ConfiguredServerNotFoundException("Server \"" + serverName + "\" not found.", e);
		}
	}
	
	protected boolean isLastConfiguredServerPresent(ConfiguredServerInfo lastServerConfig) {
		try {
			getConfiguredServer(lastServerConfig);
		} catch(ConfiguredServerNotFoundException e) {
			return false;
		}
		return true;
	}
	

	/**
	 * 
	 * @param config - server requirement configuration which will be used 
	 * to return appropriate server type label text.
	 * @return server type label text. 
	 */
	public String getServerTypeLabelText(IServerReqConfig config) {
		return config.getServerFamily().getLabel() + " "
				+ config.getServerFamily().getVersion();
	}
	
	/**
	 * 
	 * @param config - server requirement configuration which will be used 
	 * to return appropriate server name label text. 
	 * @return server name label text
	 */
	public String getServerNameLabelText(IServerReqConfig config) {
		return getServerTypeLabelText(config) + " Server";
	}

	/**
	 * 
	 * @param config - server requirement configuration which will be used 
	 * to return appropriate runtime name label text. 
	 * @return runtime name label text
	 */
	public String getRuntimeNameLabelText(IServerReqConfig config) {
		return getServerTypeLabelText(config) + " Runtime";
	}

	

	/**
	 * Exception thrown when configured server was not found.
	 */
	protected class ConfiguredServerNotFoundException extends RedDeerException {

		private static final long serialVersionUID = -1049073209937853734L;
		
		public ConfiguredServerNotFoundException(String message) {
			super(message);
		}

		public ConfiguredServerNotFoundException(String message, Throwable cause) {
			super(message, cause);
		}
		
	}
}