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
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.ComplexRequirement.ComplexReq;

public class ComplexRequirement implements ConfigurableRequirement<ComplexConfiguration, ComplexReq>{
	
	private ComplexConfiguration config;
	private ComplexReq declaration;
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface ComplexReq {
		
	}

	@Override
	public void fulfill() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDeclaration(ComplexReq declaration) {
		this.declaration = declaration;
	}

	@Override
	public ComplexReq getDeclaration() {
		return declaration;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<ComplexConfiguration> getConfigurationClass() {
		return ComplexConfiguration.class;
	}

	@Override
	public void setConfiguration(ComplexConfiguration configuration) {
		config = configuration;
	}

	@Override
	public ComplexConfiguration getConfiguration() {
		return config;
	}
	
}
