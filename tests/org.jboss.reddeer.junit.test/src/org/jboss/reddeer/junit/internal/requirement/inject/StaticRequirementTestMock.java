package org.jboss.reddeer.junit.internal.requirement.inject;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;

public class StaticRequirementTestMock {

	@InjectRequirement
	private static RequirementA requirementA;

	public static RequirementA getRequirementA() {
		return requirementA;
	}

}
