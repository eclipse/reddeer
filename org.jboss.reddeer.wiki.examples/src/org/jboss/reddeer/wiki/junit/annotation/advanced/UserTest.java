package org.jboss.reddeer.wiki.junit.annotation.advanced;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.wiki.junit.annotation.advanced.UserRequirement.User;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@User(name="admin")
public class UserTest {
	
	@Test
	public void test(){
		System.out.println("test");
	}
}