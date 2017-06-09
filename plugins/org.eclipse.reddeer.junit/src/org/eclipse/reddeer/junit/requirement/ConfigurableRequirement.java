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
package org.eclipse.reddeer.junit.requirement;

import java.lang.annotation.Annotation;

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;

/**
 * Configurable requirements contains a custom configuration and usually hold
 * more complex logic than other type of requirements.
 * 
 * @author mlabuda@redhat.com
 * @param <T>
 *            configuration of a requirement
 * @param <K>
 *            annotation of a requirement
 * @since 2.0
 */
public interface ConfigurableRequirement<T extends RequirementConfiguration, K extends Annotation>
		extends Requirement<K> {

	/**
	 * Gets class of configuration of a requirement.
	 * 
	 * @return class of configuration of configurable requirement
	 */
	Class<T> getConfigurationClass();

	/**
	 * Sets configuration for a requirement.
	 * 
	 * @param configuration
	 *            configuration to set
	 */
	void setConfiguration(T configuration);

	/**
	 * Gets configuration of a requirement.
	 * 
	 * @return configuration of a requirement.
	 */
	T getConfiguration();

	/**
	 * Gets description of this custom requirement. Description should be
	 * instance specific. E.g. it could be based on some value from requirement
	 * configuration.
	 * 
	 * @return description of this instance of configurable requirement
	 */
	String getDescription();
}
