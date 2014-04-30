package org.jboss.reddeer.requirements.server;

import org.jboss.reddeer.requirements.server.apache.tomcat.ServerRequirementConfig;

/**
 * Contains informations about configured server via server requirement.
 * The configured server is defined by its name(name displayed in servers view)
 * and configuration (ServerRequirementConfig).
 * 
 * @author Pavol Srna, Radoslav Rabara
 * @see ServerRequirementConfig
 */
public class ConfiguredServerInfo {
	
	private String serverName;
	private IServerReqConfig config;
	
	/**
	 * Define configured server by its name and configuration.
	 * 
	 * @param serverName is the name of the configured server
	 * @param config configuration which was used to configure server
	 */
	public ConfiguredServerInfo(String serverName, IServerReqConfig config) {
		this.serverName = serverName;
		this.config = config;
	}
	
	public String getServerName() {
		return serverName;
	}
	
	public IServerReqConfig getConfig() {
		return config;
	}
}
