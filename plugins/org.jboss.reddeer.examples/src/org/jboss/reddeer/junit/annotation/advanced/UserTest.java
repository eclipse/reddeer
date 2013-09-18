package org.jboss.reddeer.junit.annotation.advanced;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.junit.annotation.advanced.UserRequirement.User;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@User(name="admin")
/**
 * Test with parameterized requirement User
 * @author lucia jelinkova
 *
 */
public class UserTest {
	
	@Test
	public void test(){
		// put test logic here
	}
}