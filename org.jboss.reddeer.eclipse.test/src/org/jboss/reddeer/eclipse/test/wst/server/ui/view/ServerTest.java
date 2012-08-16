package org.jboss.reddeer.eclipse.test.wst.server.ui.view;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jboss.reddeer.eclipse.wst.server.ui.view.Server;
import org.junit.Before;
import org.junit.Test;

public class ServerTest extends ServersViewTestCase {

	private static final String SERVER_1 = "Server 1";
	
	private static final String SERVER_2 = "Server 2";
	
	private Server server1;
	
	@Before
	public void createServers(){
		createServer(SERVER_1);
		createServer(SERVER_2);
		
		server1 = serversView.getServer(SERVER_1);
	}
	
	@Test
	public void delete(){
		server1.delete();
		
		List<Server> servers = serversView.getServers();
		assertThat(servers.size(), is(1));
		assertThat(servers.get(0).getName(), is(SERVER_2));
	}
	
	@Test
	public void parseName_withStatus(){
		String name = server1.parseName("Server 1  [Started]");
		
		assertThat(name, is(SERVER_1));
	}
	
	@Test
	public void parseName_noStatus(){
		String name = server1.parseName("Server 1");
		
		assertThat(name, is(SERVER_1));
	}
}
