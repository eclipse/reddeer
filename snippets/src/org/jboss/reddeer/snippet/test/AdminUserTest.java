package org.jboss.reddeer.snippet.test;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.snippet.requirement.AdminUserRequirement.AdminUser;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@AdminUser
public class AdminUserTest {
       
	@Test
    public void test(){
		// test admin user
	}
}

