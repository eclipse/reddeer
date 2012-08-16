package org.jboss.reddeer.eclipse.wst.server.ui.view;

/**
 * Represents a project assigned to server {@link Server} on {@link ServersView}. Contains both, the project data (name, state, status) and
 * operations that can be invoked on server (Start, Stop).
 * 
 * @author Lucia Jelinkova
 *
 */
public class ServerProject {

	public ServerProject() {
	}
	
	public String getName(){
		throw new UnsupportedOperationException();
	}
	
	public String getState(){
		throw new UnsupportedOperationException();
	}
	
	public String getStatus(){
		throw new UnsupportedOperationException();
	}
	
	public void remove(){
		throw new UnsupportedOperationException();
	}
}
