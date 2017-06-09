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
package org.eclipse.reddeer.requirements.server.apache.tomcat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.requirements.server.ConfiguredServerInfo;
import org.eclipse.reddeer.requirements.server.ServerReqBase;
import org.eclipse.reddeer.requirements.server.apache.tomcat.DummyServerRequirement.DummyServer;

public class DummyServerRequirement extends ServerReqBase
	implements ConfigurableRequirement<ServerRequirementConfiguration, DummyServer> {

	private static final Logger log = Logger.getLogger(DummyServerRequirement.class);
	
	private ServerRequirementConfiguration config;
	private DummyServer server;
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface DummyServer {
		
	}

	@Override
	public void fulfill() {
		log.info("Fulfilling requirement");
		log.info(this.server.annotationType().getAnnotations().toString());
	}

	@Override
	public void setDeclaration(DummyServer server) {
		this.server = server;
	}

	@Override
	public Class<ServerRequirementConfiguration> getConfigurationClass() {
		return ServerRequirementConfiguration.class;
	}

	@Override
	public void setConfiguration(ServerRequirementConfiguration config) {
		this.config = config;
	}

	@Override
	public void cleanUp() {
	}

	@Override
	public ConfiguredServerInfo getConfiguredConfig() {
		return null;
	}

	@Override
	public DummyServer getDeclaration() {
		return server;
	}

	@Override
	public ServerRequirementConfiguration getConfiguration() {
		return config;
	}

	@Override
	public String getDescription() {
		return config.getId();
	}

	@Override
	public ServerRequirementConfiguration getConfig() {
		return config;
	}
}
