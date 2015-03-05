package org.jboss.reddeer.requirements.test.server;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersView;
import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.server.ServerReqState;
import org.jboss.reddeer.requirements.server.apache.tomcat.ServerRequirement;
import org.jboss.reddeer.requirements.server.apache.tomcat.ServerRequirement.ApacheTomcatServer;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@ApacheTomcatServer(state=ServerReqState.PRESENT)
public class ServerCleanupTest {
	
	@InjectRequirement
	protected ServerRequirement requirement;
	
	@Test
	public void testServerCleanup(){
		requirement.cleanUp();
		ServersView sw = new ServersView();
		sw.open();
		assertEquals(0,sw.getServers().size());
	}

}
