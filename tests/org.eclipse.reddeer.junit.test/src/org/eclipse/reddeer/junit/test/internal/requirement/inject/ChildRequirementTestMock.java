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
package org.eclipse.reddeer.junit.test.internal.requirement.inject;

import org.eclipse.reddeer.junit.requirement.inject.InjectRequirement;

public class ChildRequirementTestMock extends ParentRequirementTestMock {

	@InjectRequirement
	private RequirementA requirementA4;
	
	public RequirementA getRequirementA4() {
		return requirementA4;
	}
}
