package org.jboss.reddeer.wiki.junit.injection;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.wiki.junit.injection.UserRequirement.User;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@User(name="admin")
public class UserTest {
	
	@InjectRequirement
	private UserRequirement userRequirement;
	
	@Test
	public void test(){
		System.out.println(userRequirement.getPassword());
	}
}