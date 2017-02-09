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
package org.jboss.reddeer.junit.internal.configuration.configurator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.configuration.entity.PropertyBasedConfiguration;
import org.jboss.reddeer.junit.internal.configuration.reader.XMLReader;
import org.jboss.reddeer.junit.internal.configuration.setter.ConfigurationSetter;
import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * Reads property based configuration from XML file and sets the found properties into the requirement.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class PropertyBasedConfigurator implements RequirementConfigurator{

	private static final Logger log = Logger.getLogger(PropertyBasedConfigurator.class);
	
	private XMLReader reader;
	
	private ConfigurationSetter setter;
	
	private Map<String, PropertyBasedConfiguration> propertyConfigurations;
	
	/**
	 * Instantiates a new property based configurator.
	 *
	 * @param reader the reader
	 * @param setter the setter
	 */
	public PropertyBasedConfigurator(XMLReader reader, ConfigurationSetter setter) {
		super();
		this.reader = reader;
		this.setter = setter;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.internal.configuration.configurator.RequirementConfigurator#configure(org.jboss.reddeer.junit.requirement.Requirement)
	 */
	@Override
	public void configure(Requirement<?> requirement) {
		if (!(requirement instanceof PropertyConfiguration)){
			throw new IllegalArgumentException("The requirement does not implement " + PropertyConfiguration.class);
		}
		log.debug("Setting property based configuration to requirement " + requirement.getClass());
		PropertyBasedConfiguration config = getPropertyConfigurations().get(requirement.getClass().getCanonicalName());
		if (config == null){
			throw new RedDeerConfigurationException("The configuration for requirement " + requirement.getClass() + " was not found in the XML file");
		}
		setter.set(requirement, config);
		log.debug("Configuration successfully set");
	}
	
	/**
	 * Gets the property configurations.
	 *
	 * @return the property configurations
	 */
	public Map<String, PropertyBasedConfiguration> getPropertyConfigurations(){
		if (propertyConfigurations == null){
			propertyConfigurations = loadPropertyConfigurations();
		}
		return propertyConfigurations;
	}
	
	/**
	 * Load property configurations.
	 *
	 * @return map containing property based configuration
	 */
	protected Map<String, PropertyBasedConfiguration> loadPropertyConfigurations(){
		List<PropertyBasedConfiguration> list = reader.getConfiguration(PropertyBasedConfiguration.class);
		Map<String, PropertyBasedConfiguration> map = new HashMap<String, PropertyBasedConfiguration>();
		
		for (PropertyBasedConfiguration config : list){
			if (map.containsKey(config.getRequirementClassName())){
				throw new RedDeerConfigurationException("There is more than one configuration in the XML file for requirement class " + config.getRequirementClassName());
			}
			map.put(config.getRequirementClassName(), config);
		}
		
		return map;
	}
}
