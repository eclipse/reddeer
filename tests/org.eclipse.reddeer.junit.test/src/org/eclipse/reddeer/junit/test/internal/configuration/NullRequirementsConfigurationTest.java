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
package org.eclipse.reddeer.junit.test.internal.configuration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import org.eclipse.reddeer.junit.configuration.RedDeerConfigurationException;
import org.eclipse.reddeer.junit.internal.configuration.NullRequirementsConfiguration;
import org.eclipse.reddeer.junit.requirement.CustomConfiguration;
import org.eclipse.reddeer.junit.requirement.PropertyConfiguration;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.junit.Test;


public class NullRequirementsConfigurationTest{
	
	private NullRequirementsConfiguration config = new NullRequirementsConfiguration();

	//TODO create tests - rhopp
	
	@Test(expected=RedDeerConfigurationException.class)
	public void propertyBased(){
		Requirement<?> requirement = mock(Requirement.class, withSettings().extraInterfaces(PropertyConfiguration.class));
		config.configure(requirement);
	}
	
	@Test(expected=RedDeerConfigurationException.class)
	public void custom(){
		Requirement<?> requirement = mock(Requirement.class, withSettings().extraInterfaces(CustomConfiguration.class));
		config.configure(requirement);
	}
	
	@Test
	public void void_caching(){
		Requirement<?> requirement = mock(Requirement.class);
		config.configure(requirement);
	}

}
