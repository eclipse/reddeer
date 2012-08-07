package org.jboss.reddeer.junit.internal.requirement.inject;

import org.junit.Test;

public class NoRequirementTestMock {

	private String param1 = "0";
	
	@Test
	public void testNothing() {
		
	}
	
	public String getParam1() {
		return param1;
	}

}
