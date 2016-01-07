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
	
	/**
	 * Gets the server name.
	 *
	 * @return the server name
	 */
	public String getServerName() {
		return serverName;
	}
	
	/**
	 * Gets the config.
	 *
	 * @return the config
	 */
	public IServerReqConfig getConfig() {
		return config;
	}
}
