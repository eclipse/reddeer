package org.jboss.reddeer.eclipse.wst.server.ui.view;

import org.jboss.reddeer.jface.viewer.handler.TreeViewerHandler;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerPublishState;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;
import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Common ancestor for {@link ModuleLabel} and {@link ServerLabel}
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractLabel {

	private TreeViewerHandler treeViewerHandler = TreeViewerHandler.getInstance(); 
	
	protected String name;
	
	protected ServerState state = ServerState.NONE;
	
	protected ServerPublishState status = ServerPublishState.NONE;

	protected abstract void parseSingleStateDecoration(String decoration);

	protected void parse(final TreeItem item){
		name = treeViewerHandler.getNonStyledText(item);
		String[] styledTexts = treeViewerHandler.getStyledTexts(item);
		if (styledTexts != null) {
			parseDecoration(styledTexts[0]);
		}
	}
	
	private void parseDecoration(String styledText) {
		if(styledText.contains(",")){
			state = ServerState.getByText(styledText.substring(styledText.indexOf("[") + 1, styledText.lastIndexOf(",")).trim());
			status = ServerPublishState.getByText(styledText.substring(styledText.indexOf(",") + 1, styledText.lastIndexOf("]")).trim());
		} else {
			parseSingleStateDecoration(styledText.substring(styledText.indexOf("[") + 1, styledText.lastIndexOf("]")).trim());
		}	
	}

	/**
	 * Returns a server name.
	 * 
	 * @return Server name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns a server state.
	 * 
	 * @return Server state
	 */
	public ServerState getState() {
		return state;
	}
	
	/**
	 * Returns a server publish state.
	 * 
	 * @return Server publish state
	 */
	public ServerPublishState getPublishState() {
		return status;
	}
}
