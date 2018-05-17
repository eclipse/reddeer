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
package org.eclipse.reddeer.junit.test.internal.requirement.inject;

import org.eclipse.reddeer.junit.requirement.inject.InjectRequirement;

public class GrandParentRequirementTestMock {

	@InjectRequirement
	private RequirementA requirementA1;
	
	@InjectRequirement
	private RequirementA requirementA2;
	
	public RequirementA getRequirementA1() {
		return requirementA1;
	}

	public RequirementA getRequirementA2() {
		return requirementA2;
	}

}
