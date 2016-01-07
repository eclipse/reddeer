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
package org.jboss.reddeer.junit.internal.configuration;

import org.jboss.reddeer.junit.internal.configuration.configurator.CustomConfigurator;
import org.jboss.reddeer.junit.internal.configuration.configurator.NullConfigurator;
import org.jboss.reddeer.junit.internal.configuration.configurator.PropertyBasedConfigurator;
import org.jboss.reddeer.junit.internal.configuration.configurator.RequirementConfigurator;
import org.jboss.reddeer.junit.internal.configuration.reader.XMLReader;
import org.jboss.reddeer.junit.internal.configuration.setter.ConfigurationSetter;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;

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
public class RequirementsConfigurationImpl implements RequirementsConfiguration{
	
	private NullConfigurator voidConfigurator;
	
	private PropertyBasedConfigurator propertyConfigurator;
	
	private CustomConfigurator customConfigurator;
	
	/**
	 * Instantiates a new requirements configuration impl.
	 *
	 * @param reader the reader
	 */
	public RequirementsConfigurationImpl(XMLReader reader) {
		super();
		this.voidConfigurator = new NullConfigurator();
		this.propertyConfigurator = new PropertyBasedConfigurator(reader, new ConfigurationSetter());
		this.customConfigurator = new CustomConfigurator(reader);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.internal.configuration.RequirementsConfiguration#configure(org.jboss.reddeer.junit.requirement.Requirement)
	 */
	public void configure(Requirement<?> requirement){
		getConfigurator(requirement).configure(requirement);
	}
	
	/**
	 * Gets the configurator.
	 *
	 * @param requirement the requirement
	 * @return the configurator
	 */
	public RequirementConfigurator getConfigurator(Requirement<?> requirement){
		if (requirement instanceof PropertyConfiguration){
			return propertyConfigurator;
		}
		
		if (requirement instanceof CustomConfiguration<?>){
			return customConfigurator;
		}
		return voidConfigurator;
	}
}
