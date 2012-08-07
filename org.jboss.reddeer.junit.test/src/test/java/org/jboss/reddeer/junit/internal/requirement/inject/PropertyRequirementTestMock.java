package org.jboss.reddeer.junit.internal.requirement.inject;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;

public class PropertyRequirementTestMock {

	@InjectRequirement
	private RequirementA requirementA;
	
	public RequirementA getRequirementA() {
		return requirementA;
	}

}
