package org.jboss.reddeer.requirements.test.server;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.wst.server.ui.view.Server;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersView;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;
import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.server.ServerReqState;
import org.jboss.reddeer.requirements.server.apache.tomcat.ServerRequirement;
import org.jboss.reddeer.requirements.server.apache.tomcat.ServerRequirement.ApacheTomcatServer;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(RedDeerSuite.class)
@ApacheTomcatServer(state=ServerReqState.RUNNING)
public class ServerRunningTest {
	
	@InjectRequirement
	protected ServerRequirement requirement;
	
	
	@Test
	public void isServerRunningTest(){
		
		ServersView sw = new ServersView();
		Server s = sw.getServer(requirement.getServerNameLabelText(requirement.getConfig()));
		assertTrue(s.getLabel().getState().equals(ServerState.STARTED));
	}

}
