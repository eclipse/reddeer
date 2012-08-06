package org.jboss.reddeer.junit.internal.requirement.inject;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.junit.Test;

public class PropertyRequirementTestMock {

	@InjectRequirement
	private RequirementA requirementA;
	
	@Test
	public void testNothing() {
		
	}
	
}
