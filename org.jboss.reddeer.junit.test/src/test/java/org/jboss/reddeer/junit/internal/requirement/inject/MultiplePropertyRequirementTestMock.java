package org.jboss.reddeer.junit.internal.requirement.inject;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.junit.Test;

public class MultiplePropertyRequirementTestMock {

	@InjectRequirement
	private RequirementA requirementA1;
	
	@InjectRequirement
	private RequirementA requirementA2;
	
	@Test
	public void testNothing() {
		
	}
	
}
