package org.jboss.reddeer.eclipse.test.wst.server.ui.view;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.jboss.reddeer.eclipse.wst.server.ui.view.ServerLabel;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerStatus;
import org.junit.Test;


public class ServerLabelTest {

	@Test
	public void nameOnly(){
		ServerLabel label = new ServerLabel("Server 1 ");
		
		assertThat(label.getName(), is("Server 1"));
		assertThat(label.getState(), nullValue());
		assertThat(label.getStatus(), nullValue());
	}

	@Test
	public void nameAndState(){
		ServerLabel label = new ServerLabel("Server 1  [Started ]");
		
		assertThat(label.getName(), is("Server 1"));
		assertThat(label.getState(), is(ServerState.STARTED));
		assertThat(label.getStatus(), nullValue());
	}
	
	@Test
	public void nameStateAndStatus(){
		ServerLabel label = new ServerLabel("Server 1  [Started, Republish ]");
		
		assertThat(label.getName(), is("Server 1"));
		assertThat(label.getState(), is(ServerState.STARTED));
		assertThat(label.getStatus(), is(ServerStatus.REPUBLISH));
	}
}
