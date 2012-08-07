package org.jboss.reddeer.junit.internal.requirement.inject;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;

public class NonExistedRequirementTestMock {

	@InjectRequirement
	private String param1;
	
	public String getParam1() {
		return param1;
	}

}
