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

import org.eclipse.reddeer.junit.configuration.RedDeerConfigurationException;
import org.eclipse.reddeer.junit.internal.configuration.configurator.NullConfigurator;
import org.eclipse.reddeer.junit.requirement.CustomConfiguration;
import org.eclipse.reddeer.junit.requirement.PropertyConfiguration;
import org.eclipse.reddeer.junit.requirement.Requirement;

/**
 * Configures requirements when no configuration file was specified.
 * 
 * @author rhopp
 * @see RequirementsConfiguration
 */

public class NullRequirementsConfiguration implements
		RequirementsConfiguration {
	
	private NullConfigurator voidConfigurator;

	/**
	 * Instantiates a new null requirements configuration.
	 */
	public NullRequirementsConfiguration() {
		super();
		this.voidConfigurator = new NullConfigurator();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.internal.configuration.RequirementsConfiguration#configure(org.eclipse.reddeer.junit.requirement.Requirement)
	 */
	@Override
	public void configure(Requirement<?> requirement) {
		if (requirement instanceof PropertyConfiguration || requirement instanceof CustomConfiguration){
			throw new RedDeerConfigurationException("Class "+requirement.getClass().toString()+" requires configuration file, but no configuration file was specified");
		}
		voidConfigurator.configure(requirement);
	}
}
