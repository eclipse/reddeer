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
package org.eclipse.reddeer.junit.internal.configuration.configurator;

import org.eclipse.reddeer.junit.requirement.Requirement;

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
