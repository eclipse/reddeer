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

	private ServerPublishState status = ServerPublishState.NONE;

	public ModuleLabel(TreeItem item) {
		parse(item);
	}

	@Override
	protected void parseDecoration(String styledText) {
		status = ServerPublishState.getByText(styledText.substring(styledText.indexOf("[") + 1, styledText.lastIndexOf("]")).trim());
	}
	
	public ServerPublishState getPublishState() {
		return status;
	}
}
