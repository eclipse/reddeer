package org.jboss.reddeer.junit.configuration.advanced;

import org.jboss.reddeer.junit.configuration.advanced.UserRequirement.User;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * User test using configuration from custom xml file
 * 
 * Set VM parameter -Drd.config to point to directory with requirements.xml file 
 * -Drd.config=${project_loc}/src/org/jboss/reddeer/junit/configuration/advanced
 * 
 * @author lucia jelinkova
 *
 */
@RunWith(RedDeerSuite.class)
@User(name="admin")
public class UserTest {
	@Test
	public void test(){
		// put your test logic here
	}
}