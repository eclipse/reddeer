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
package org.jboss.reddeer.junit.requirement;

/**
 * Requirements that want to use custom configuration (not the default property based configuration and XML schema) 
 * need to implement the interface. 
 * 
 * @author Lucia Jelinkova
 *
 * @param <T> Class that will hold the configuration data. 
 */
public interface CustomConfiguration<T> {

	/**
	 * Gets the configuration class.
	 *
	 * @return the configuration class
	 */
	Class<T> getConfigurationClass();
	
	/**
	 * Sets the configuration.
	 *
	 * @param config the new configuration
	 */
	void setConfiguration(T config);
}
