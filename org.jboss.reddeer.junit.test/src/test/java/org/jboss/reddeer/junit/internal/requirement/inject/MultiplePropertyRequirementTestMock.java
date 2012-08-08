package org.jboss.reddeer.junit.internal.requirement.inject;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;

public class MultiplePropertyRequirementTestMock {

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
