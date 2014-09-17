package org.jboss.reddeer.junit.configuration.simple;

import org.jboss.reddeer.junit.configuration.simple.UserRequirement.User;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Test with User requirement using configuration from simple xml file
 * 
 * Set VM parameter -Dreddeer.config to point to directory with requirements.xml file 
 * -Dreddeer.config=${project_loc}/src/org/jboss/reddeer/junit/configuration/simple
 * 
 * @author lucia jelinkova
 *
 */
@RunWith(RedDeerSuite.class)
@User
public class UserTest {
	@Test
	public void test(){
		// put test logic here
	}
}