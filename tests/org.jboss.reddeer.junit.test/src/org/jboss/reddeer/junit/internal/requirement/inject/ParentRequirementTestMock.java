package org.jboss.reddeer.junit.internal.requirement.inject;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;

public class ParentRequirementTestMock extends GrandParentRequirementTestMock{

	@InjectRequirement
	private RequirementA requirementA1;
	
	public RequirementA getRequirementA1() {
		return requirementA1;
	}

}
