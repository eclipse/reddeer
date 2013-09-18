package org.jboss.reddeer.junit.annotation.simple;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.junit.annotation.simple.AdminUserRequirement.AdminUser;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@AdminUser
/**
 * Test with AdminUser requirement
 * @author lucia jelinova
 *
 */
public class AdminUserTest {
	
	@Test
	public void test(){
	  // put test logic here	
	}
}