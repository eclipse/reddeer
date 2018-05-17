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
package org.eclipse.reddeer.junit.internal.configuration.reader;

import java.io.File;
import java.util.List;

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;

/**
 * Configuration reader read a file with requirement configurations
 * 
 * @author mlabuda@redhat.com
 *
 */
public interface ConfigurationReader {

	/**
	 * Loads requirements configurations from a configuration file. Reader should read all configurations
	 * stored in a file of specific format.
	 * 
	 * @param file file with requirements configurations
	 * @return list of requirements configurations
	 */
	List<RequirementConfiguration> loadConfigurations(File file);
	
}
