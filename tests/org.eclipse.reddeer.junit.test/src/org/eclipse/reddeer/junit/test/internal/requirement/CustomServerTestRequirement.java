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
package org.eclipse.reddeer.junit.test.internal.requirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.junit.test.internal.requirement.CustomServerTestRequirement.CustomServerAnnotation;

public class CustomServerTestRequirement
		implements ConfigurableRequirement<CustomServerTestConfiguration, CustomServerAnnotation> {

	private CustomServerTestConfiguration config;
	private CustomServerAnnotation declaration;
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface CustomServerAnnotation {
	}

	@Override
	public void fulfill() {
	}

	@Override
	public void setDeclaration(CustomServerAnnotation declaration) {
		this.declaration = declaration;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
	}

	@Override
	public Class<CustomServerTestConfiguration> getConfigurationClass() {
		return CustomServerTestConfiguration.class;
	}

	@Override
	public void setConfiguration(CustomServerTestConfiguration configuration) {
		this.config = configuration;
	}

	@Override
	public CustomServerAnnotation getDeclaration() {
		return declaration;
	}

	@Override
	public CustomServerTestConfiguration getConfiguration() {
		return config;
	}

	@Override
	public String getDescription() {
		return config.getId();
	}

}
