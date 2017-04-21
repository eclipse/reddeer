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

/**
 * Represents configuration for one test run.
 * 
 * @author rhopp
 *
 */

public interface TestRunConfiguration {
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId();
	
	/**
	 * Gets the requirement configuration.
	 *
	 * @return the requirement configuration
	 */
	public RequirementsConfiguration getRequirementConfiguration();

}
