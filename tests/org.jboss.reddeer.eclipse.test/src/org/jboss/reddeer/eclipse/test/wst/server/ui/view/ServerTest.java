package org.jboss.reddeer.eclipse.test.wst.server.ui.view;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jboss.reddeer.eclipse.wst.server.ui.view.Server;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerPublishState;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewException;
import org.junit.Test;

public class ServerTest extends ServersViewTestCase {

	private static final String SERVER_1 = "Server 1";
	
	private static final String SERVER_2 = "Server 2";
	
	private Server server1;
	
	@Override
	protected void setUp(){
	  super.setUp();
		createServer(SERVER_1);
		createServer(SERVER_2);
		
		server1 = serversView.getServer(SERVER_1);
	}
	
	@Test
	public void start_stoppedServer(){
		server1.start();
		
		assertThat(server1.getLabel().getState(), is(ServerState.STARTED));
	}
	
	@Test(expected=ServersViewException.class)
	public void start_runningServer(){
		server1.start();
		server1.start();
	}
	
	@Test
	public void debug_stoppedServer(){
		server1.debug();
		
		assertThat(server1.getLabel().getState(), is(ServerState.DEBUGGING));
	}
	
	@Test(expected=ServersViewException.class)
	public void debug_runningServer(){
		server1.start();
		server1.debug();
	}
	
	@Test
	public void profile_stoppedServer(){
		server1.profile();
		
		assertThat(server1.getLabel().getState(), is(ServerState.PROFILING));
	}
	
	@Test(expected=ServersViewException.class)
	public void profile_runningServer(){
		server1.start();
		server1.profile();
	}
	
	@Test(expected=ServersViewException.class)
	public void restart_stoppedServer(){
		server1.restart();
	}
	
	@Test
	public void restart_runningServer(){
		server1.debug();
		server1.restart();
		
		assertThat(server1.getLabel().getState(), is(ServerState.STARTED));
	}
	
	@Test(expected=ServersViewException.class)
	public void restartInDebug_stoppedServer(){
		server1.restartInDebug();
	}
	
	@Test
	public void restartInDebug_runningServer(){
		server1.debug();
		server1.restartInDebug();
		
		assertThat(server1.getLabel().getState(), is(ServerState.DEBUGGING));
	}
	
	@Test(expected=ServersViewException.class)
	public void restartInProfile_stoppedServer(){
		server1.restartInProfile();
	}
	
	@Test
	public void restartInProfile_runningServer(){
		server1.profile();
		server1.restartInProfile();
		
		assertThat(server1.getLabel().getState(), is(ServerState.PROFILING));
	}
	
	@Test(expected=ServersViewException.class)
	public void stop_stoppedServer(){
		server1.stop();
	}
	
	@Test
	public void stop_runningServer(){
		server1.start();
		server1.stop();
		
		assertThat(server1.getLabel().getState(), is(ServerState.STOPPED));
	}
	
	@Test
	public void publish(){
		server1.publish();
		
		assertThat(server1.getLabel().getPublishState(), is(ServerPublishState.SYNCHRONIZED));
	}
	
	@Test
	public void clean(){
		server1.clean();
		
		assertThat(server1.getLabel().getPublishState(), is(ServerPublishState.SYNCHRONIZED));
	}
	
	@Test
	public void delete_runningServer(){
		server1.start();
		server1.delete(false);
		
		List<Server> servers = serversView.getServers();
		assertThat(servers.size(), is(1));
		assertThat(servers.get(0).getLabel().getName(), is(SERVER_2));
	}
	
	@Test
	public void delete_runningServerAndStop(){
		server1.start();
		server1.delete(true);
		
		List<Server> servers = serversView.getServers();
		assertThat(servers.size(), is(1));
		assertThat(servers.get(0).getLabel().getName(), is(SERVER_2));
	}
	
	@Test
	public void delete_stoppedServer(){
		server1.delete(false);
		
		List<Server> servers = serversView.getServers();
		assertThat(servers.size(), is(1));
		assertThat(servers.get(0).getLabel().getName(), is(SERVER_2));
	}
	
	@Test
	public void delete_stoppedServerAndStop(){
		server1.delete(true);
		
		List<Server> servers = serversView.getServers();
		assertThat(servers.size(), is(1));
		assertThat(servers.get(0).getLabel().getName(), is(SERVER_2));
	}
}
