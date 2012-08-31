package org.jboss.reddeer.eclipse.wst.server.ui.view;

import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerPublishState;

/**
 * Parses and holds information displayed in the server's label on 
 * {@link ServersView}
 * 
 * @author Lucia Jelinkova
 *
 */
public class ServerLabel {

	private String name;
	
	private ServerState state;
	
	private ServerPublishState status;
	
	public ServerLabel(String label) {
		parse(label);
	}

	private void parse(String label){
		if (!label.contains("[")){
			name = label.trim();
			return;
		}
		name = label.substring(0, label.indexOf("[")).trim();
		
		if(label.matches(".*[.*,.*].*")){
			state = ServerState.getByText(label.substring(label.indexOf("[") + 1, label.lastIndexOf(",")).trim());
			status = ServerPublishState.getByText(label.substring(label.indexOf(",") + 1, label.lastIndexOf("]")).trim());
		} else {
			state = ServerState.getByText(label.substring(label.indexOf("[") + 1, label.lastIndexOf("]")).trim());
		}
	}
	
	public String getName() {
		return name;
	}
	
	public ServerState getState() {
		return state;
	}
	
	public ServerPublishState getPublishState() {
		return status;
	}
}
