package org.jboss.reddeer.junit.internal.requirement.inject;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.junit.Test;

public class NonExistedRequirementTestMock {

	@InjectRequirement
	private String param1;
	
	@Test
	public void testNothing() {
		
	}
	
	public String getParam1() {
		return param1;
	}

}
