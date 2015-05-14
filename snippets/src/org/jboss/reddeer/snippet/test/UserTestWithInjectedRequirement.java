package org.jboss.reddeer.snippet.test;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.snippet.requirement.simple.UserRequirement;
import org.jboss.reddeer.snippet.requirement.simple.UserRequirement.User;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@User
public class UserTestWithInjectedRequirement {

	@InjectRequirement
	private UserRequirement userRequirement;

	@Test
	public void test() {
		System.out.println(userRequirement.getIp());
	}
}