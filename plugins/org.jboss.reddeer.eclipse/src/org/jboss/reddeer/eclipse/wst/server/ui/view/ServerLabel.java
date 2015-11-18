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

	/**
	 * Instantiates a new server label.
	 *
	 * @param item the item
	 */
	public ServerLabel(TreeItem item) {
		parse(item);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.wst.server.ui.view.AbstractLabel#parseSingleStateDecoration(java.lang.String)
	 */
	@Override
	protected void parseSingleStateDecoration(String stateString) {
			state = ServerState.getByText(stateString);
	}
}
