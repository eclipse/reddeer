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
 * Provides no action upon requirement configuration. Used for requirements that require no configuration.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class NullConfigurator implements RequirementConfigurator {

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.internal.configuration.configurator.RequirementConfigurator#configure(org.eclipse.reddeer.junit.requirement.Requirement)
	 */
	@Override
	public void configure(Requirement<?> requirement) {
		// do nothing, no configuration required
	}
}
