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
package org.eclipse.reddeer.junit.test.requirement.configuration.resources;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.SimpleRequirement.SimpleReq;

public class SimpleRequirement implements ConfigurableRequirement<SimpleConfiguration, SimpleReq>{

	private SimpleReq declaration;
	private SimpleConfiguration config;
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface SimpleReq {
		
	}

	@Override
	public void fulfill() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDeclaration(SimpleReq declaration) {
		this.declaration = declaration;
	}

	@Override
	public SimpleReq getDeclaration() {
		return declaration;
	}

	@Override
	public void cleanUp() {	
	}

	@Override
	public Class<SimpleConfiguration> getConfigurationClass() {
		return SimpleConfiguration.class;
	}

	@Override
	public void setConfiguration(SimpleConfiguration configuration) {
		config = configuration;
	}

	@Override
	public SimpleConfiguration getConfiguration() {
		return config;
	}
	
}
