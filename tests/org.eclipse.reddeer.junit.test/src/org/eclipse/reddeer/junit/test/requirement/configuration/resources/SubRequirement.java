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
package org.eclipse.reddeer.junit.test.requirement.configuration.resources;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.SubRequirement.SubReq;

/**
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class SubRequirement implements ConfigurableRequirement<SubConfiguration, SubReq> {

	private SubReq declaration;
	private SubConfiguration config;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface SubReq {

	}

	@Override
	public void fulfill() {

	}

	@Override
	public void setDeclaration(SubReq declaration) {
		this.declaration = declaration;
	}

	@Override
	public SubReq getDeclaration() {
		return declaration;
	}

	@Override
	public void cleanUp() {
	}

	@Override
	public Class<SubConfiguration> getConfigurationClass() {
		return SubConfiguration.class;
	}

	@Override
	public void setConfiguration(SubConfiguration configuration) {
		config = configuration;
	}

	@Override
	public SubConfiguration getConfiguration() {
		return config;
	}

}
