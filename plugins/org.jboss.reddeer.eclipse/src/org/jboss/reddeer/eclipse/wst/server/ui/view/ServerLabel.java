package org.jboss.reddeer.eclipse.wst.server.ui.view;

import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;
import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Parses and holds information displayed in the server's label on 
 * {@link ServersView}
 * 
 * @author Lucia Jelinkova
 *
 */
public class ServerLabel extends AbstractLabel {

	public ServerLabel(TreeItem item) {
		parse(item);
	}

	@Override
	protected void parseSingleStateDecoration(String stateString) {
			state = ServerState.getByText(stateString);
	}
}
