package org.jboss.reddeer.wiki.junit.annotation.simple;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.wiki.junit.annotation.simple.AdminUserRequirement.AdminUser;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@AdminUser
public class AdminUserTest {
	
	@Test
	public void test(){
		
	}
}