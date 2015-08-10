package org.jboss.reddeer.snippet.requirements.custom;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.snippet.requirements.custom.UserRequirement.User;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@User(name="admin")
public class UserTest {
	@Test
	public void test(){
		// put your test logic here
	}
}