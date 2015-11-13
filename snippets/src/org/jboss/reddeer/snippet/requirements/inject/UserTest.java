package org.jboss.reddeer.snippet.requirements.inject;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.snippet.requirements.inject.UserRequirement.User;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Test injecting user requirement
 * 
 * Set VM parameter -Drd.config to point to directory with requirements.xml file 
 * -Drd.config=${project_loc}/src/org/jboss/reddeer/junit/injection
 * 
 * @author lucia jelinkova
 *
 */
@RunWith(RedDeerSuite.class)
@User
public class UserTest {
	
	@InjectRequirement
	private UserRequirement userRequirement;
	
	@Test
	public void test(){
		System.out.println(userRequirement.getPassword());
	}
}