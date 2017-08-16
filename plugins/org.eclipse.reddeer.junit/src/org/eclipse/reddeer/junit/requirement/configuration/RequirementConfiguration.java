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
package org.eclipse.reddeer.junit.requirement.configuration;

/**
 * Requirement configuration represents a data holder, container for data
 * required to executed more advanced logic with requirements. Requirement
 * configuration consists of several attributes obtained from a source for a
 * test run.
 * 
 * @author mlabuda@redhat.com
 * @since 2.0
 */
public interface RequirementConfiguration {

	/**
	 * Gets ID, unique description of a requirement configuration
	 * 
	 * @return ID/unique description of configuration
	 */
	String getId();

}
