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
package org.eclipse.reddeer.junit.test.internal.requirement.inject;

import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.junit.test.internal.requirement.inject.RequirementA.ReqA;
import org.eclipse.reddeer.requirements.property.PropertyConfiguration;

public class RequirementA implements ConfigurableRequirement<PropertyConfiguration, ReqA> {

	private ReqA declaration;
	private PropertyConfiguration config;
	
	public @interface ReqA {
		
	}

	@Override
	public void fulfill() {
		// nothing
	}

	@Override
	public void setDeclaration(ReqA declaration) {
		this.declaration = declaration;
		
	}

	@Override
	public ReqA getDeclaration() {
		return declaration;
	}

	@Override
	public void cleanUp() {
	}

	@Override
	public Class<PropertyConfiguration> getConfigurationClass() {
		return PropertyConfiguration.class;
	}

	@Override
	public void setConfiguration(PropertyConfiguration configuration) {
		config = configuration;
	}

	@Override
	public PropertyConfiguration getConfiguration() {
		return config;
	}
}
