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

import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * Configures the instance of {@link Requirement}
 * 
 * @author Lucia Jelinkova
 *
 */
public interface RequirementConfigurator {

	/**
	 * Configure.
	 *
	 * @param requirement the requirement
	 */
	void configure(Requirement<?> requirement);
}
