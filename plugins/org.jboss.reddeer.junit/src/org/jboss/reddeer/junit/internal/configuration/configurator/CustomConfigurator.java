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

import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * Sets one of custom configuration from the given list into the requirement. 
 * 
 * @author Lucia Jelinkova, Ondrej Dockal
 *
 */
public class CustomConfigurator implements RequirementConfigurator {

	private static final Logger log = Logger.getLogger(CustomConfigurator.class);
	
	private List<Object> configurations;
	
	/**
	 * Instantiates a new custom configurator.
	 *
	 * @param configurations list of configurations
	 */
	public CustomConfigurator(List<Object> configurations) {
		this.configurations = configurations;
	}

	/**
	* Iterate over all configurations and tries to cast the configuration object to expected configuration class
	* that must implement {@link CustomConfiguration}.
	* If none of configuration is set, then the {@link RedDeerConfigurationException} is thrown.
	 */
	@Override
	public void configure(Requirement<?> requirement) {
		
		if (!(requirement instanceof CustomConfiguration<?>)) {
			throw new IllegalArgumentException("The requirement does not implement " + CustomConfiguration.class);
		}
		
		log.debug("Setting custom configuration to requirement " + requirement.getClass());
		
		@SuppressWarnings("unchecked")
		CustomConfiguration<Object> customConfiguration = (CustomConfiguration<Object>) requirement;
		
		log.debug("Configuration object associated with requirement " + requirement.getClass() + " is " + customConfiguration.getConfigurationClass());
		
		boolean configurationSet = false;
		for (Object configuration : this.configurations) {
			if(customConfiguration.getConfigurationClass().isAssignableFrom(configuration.getClass())){
				customConfiguration.setConfiguration(configuration);
				log.debug("Configuration successfully set");		
				configurationSet = true;
				break;
			}
		}
		if (!configurationSet) {
			throw new RedDeerConfigurationException("None of the given configurations "
					+ "could have ben set as configuration of the requirement " + requirement.getClass().getName());
		}

	}
}
