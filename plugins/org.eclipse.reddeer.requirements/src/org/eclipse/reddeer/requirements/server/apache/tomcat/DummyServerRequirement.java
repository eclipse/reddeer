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
import org.eclipse.reddeer.junit.requirement.CustomConfiguration;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.requirements.server.ConfiguredServerInfo;
import org.eclipse.reddeer.requirements.server.ServerReqBase;
import org.eclipse.reddeer.requirements.server.apache.tomcat.ServerRequirementConfig;
import org.eclipse.reddeer.requirements.server.apache.tomcat.DummyServerRequirement.DummyServer;

public class DummyServerRequirement extends ServerReqBase
	implements Requirement<DummyServer>, CustomConfiguration<ServerRequirementConfig> {

	private static final Logger log = Logger.getLogger(DummyServerRequirement.class);
	
	private ServerRequirementConfig config;
	private DummyServer server;
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface DummyServer {
		
	}


	@Override
	public boolean canFulfill() {
		return true;
	}

	@Override
	public void fulfill() {
		log.info("Fulfilling requirement");
		log.info(this.server.annotationType().getAnnotations().toString());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.requirement.Requirement#setDeclaration(java.lang.annotation.Annotation)
	 */
	@Override
	public void setDeclaration(DummyServer server) {
		this.server = server;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.requirement.CustomConfiguration#getConfigurationClass()
	 */
	@Override
	public Class<ServerRequirementConfig> getConfigurationClass() {
		return ServerRequirementConfig.class;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.requirement.CustomConfiguration#setConfiguration(java.lang.Object)
	 */
	@Override
	public void setConfiguration(ServerRequirementConfig config) {
		this.config = config;
	}

	/**
	 * Gets the config.
	 *
	 * @return the config
	 */
	public ServerRequirementConfig getConfig() {
		return this.config;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ConfiguredServerInfo getConfiguredConfig() {
		// TODO Auto-generated method stub
		return null;
	}

}
