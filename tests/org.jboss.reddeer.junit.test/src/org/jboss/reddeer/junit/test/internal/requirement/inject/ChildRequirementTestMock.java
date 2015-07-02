package org.jboss.reddeer.junit.test.internal.requirement.inject;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;

public class ChildRequirementTestMock extends ParentRequirementTestMock {

	@InjectRequirement
	private RequirementA requirementA1;
	
	public RequirementA getRequirementA1() {
		return requirementA1;
	}
}
