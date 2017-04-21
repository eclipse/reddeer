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
 * Represents Run configuration, when no config file is specified.
 * 
 * @author rhopp
 *
 */
public class NullTestRunConfiguration implements TestRunConfiguration {

	private RequirementsConfiguration requirementsConfiguration;
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.internal.configuration.TestRunConfiguration#getId()
	 */
	@Override
	public String getId() {
		return "default";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.internal.configuration.TestRunConfiguration#getRequirementConfiguration()
	 */
	@Override
	public RequirementsConfiguration getRequirementConfiguration() {
		if (requirementsConfiguration == null){
			requirementsConfiguration = new NullRequirementsConfiguration();
		}
		return requirementsConfiguration;
	}

}
