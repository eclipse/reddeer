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

/**
 * Configuration associated with one list of possible configurations representing one run of the tests. 
 * 
 * @author Lucia Jelinkova
 * @author Ondrej Dockal
 *
 */
public class TestRunConfigurationImpl implements TestRunConfiguration {

	private String id = "";

	private List<Object> configurationSet;
	
	private RequirementsConfiguration requirementsConfiguration;
	
	/**
	 * Instantiates a new test run configuration impl.
	 *
	 * @param configurations list of configurations values
	 */
	public TestRunConfigurationImpl(List<Object> configurations) {
		this.configurationSet = configurations;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.internal.configuration.TestRunConfiguration#getId()
	 */
	public String getId() {
		if (id.isEmpty()){
			for (Object config : configurationSet) {
				if (!id.isEmpty()) id += " ";
				id += config.getClass().getSimpleName();
			}
		}
		return !id.isEmpty() ? id : "Empty Configuration";
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.internal.configuration.TestRunConfiguration#getRequirementConfiguration()
	 */
	public RequirementsConfiguration getRequirementConfiguration() {
		if (requirementsConfiguration == null){
			requirementsConfiguration = new RequirementsConfigurationImpl(this.configurationSet);
		}
		return requirementsConfiguration;
	}
}
