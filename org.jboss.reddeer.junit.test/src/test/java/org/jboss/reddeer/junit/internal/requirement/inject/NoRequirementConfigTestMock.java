package org.jboss.reddeer.junit.internal.requirement.inject;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.junit.Test;

public class NoRequirementConfigTestMock {

	@InjectRequirement
	private RequirementB requirementA;
	
	@Test
	public void testNothing() {
		
	}

	public RequirementB getRequirementA() {
		return requirementA;
	}
	
}
