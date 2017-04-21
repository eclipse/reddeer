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
package org.eclipse.reddeer.junit.internal.configuration;

import java.util.List;

import org.eclipse.reddeer.junit.internal.configuration.configurator.CustomConfigurator;
import org.eclipse.reddeer.junit.internal.configuration.configurator.NullConfigurator;
import org.eclipse.reddeer.junit.internal.configuration.configurator.PropertyBasedConfigurator;
import org.eclipse.reddeer.junit.internal.configuration.configurator.RequirementConfigurator;
import org.eclipse.reddeer.junit.internal.configuration.setter.ConfigurationSetter;
import org.eclipse.reddeer.junit.requirement.CustomConfiguration;
import org.eclipse.reddeer.junit.requirement.PropertyConfiguration;
import org.eclipse.reddeer.junit.requirement.Requirement;

/**
 * Configures the provided requirement object depending on the implemented interface:
 * <ol>
 * 		<li>If the requirement implements the {@link PropertyConfiguration} the properties are read from XML and injected</li>
 * 		<li>If the requirement implements the {@link CustomConfiguration} the configuration is set through {@link CustomConfiguration#setConfiguration(Object)} method</li>
 * 		<li>Otherwise no configuration is performed</li>
 * </ol>
 * 
 * @author Lucia Jelinkova
 * @see TestRunConfiguration
 */
public class RequirementsConfigurationImpl implements RequirementsConfiguration {
	
	private NullConfigurator voidConfigurator;
	
	private PropertyBasedConfigurator propertyConfigurator;
	
	private CustomConfigurator customConfigurator;
	
	/**
	 * Instantiates a new requirements configuration impl.
	 *
	 * @param configurations list of configurations values
	 */
	public RequirementsConfigurationImpl(List<Object> configurations) {
		super();
		this.voidConfigurator = new NullConfigurator();
		this.propertyConfigurator = new PropertyBasedConfigurator(configurations, new ConfigurationSetter());
		this.customConfigurator = new CustomConfigurator(configurations);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.internal.configuration.RequirementsConfiguration#configure(org.eclipse.reddeer.junit.requirement.Requirement)
	 */
	public void configure(Requirement<?> requirement) {
		getConfigurator(requirement).configure(requirement);
	}
	
	/**
	 * Gets the configurator.
	 *
	 * @param requirement the requirement
	 * @return the configurator
	 */
	public RequirementConfigurator getConfigurator(Requirement<?> requirement) {
		if (requirement instanceof PropertyConfiguration){
			return propertyConfigurator;
		}
		
		if (requirement instanceof CustomConfiguration<?>) {
			return customConfigurator;
		}
		return voidConfigurator;
	}
}
