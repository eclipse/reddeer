package org.jboss.reddeer.eclipse.wst.server.ui.view;

import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerPublishState;
import org.jboss.reddeer.swt.api.TreeItem;


/**
 * Parses and holds information displayed in the module's label on 
 * {@link ServersView}
 * 
 * @author Lucia Jelinkova
 *
 */
public class ModuleLabel extends AbstractLabel{

	public ModuleLabel(TreeItem item) {
		parse(item);
	}

	@Override
	protected void parseSingleStateDecoration(String stateString) {
			status = ServerPublishState.getByText(stateString);
	}
}
