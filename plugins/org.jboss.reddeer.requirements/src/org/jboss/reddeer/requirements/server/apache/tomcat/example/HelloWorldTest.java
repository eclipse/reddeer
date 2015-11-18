package org.jboss.reddeer.requirements.server.apache.tomcat.example;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.server.apache.tomcat.ServerRequirement.ApacheTomcatServer;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@ApacheTomcatServer()
public class HelloWorldTest {
	
	/**
	 * Test hello world.
	 */
	@Test
	public void testHelloWorld(){
		System.out.println("Hello World!");
	}

}
