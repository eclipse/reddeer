package org.jboss.reddeer.eclipse.wst.server.ui.view;

import org.eclipse.swt.custom.StyleRange;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerPublishState;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.util.Display;

/**
 * Common ancestor for {@link ModuleLabel} and {@link ServerLabel}
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractLabel {

	protected String name;
	
	protected ServerState state = ServerState.NONE;
	
	protected ServerPublishState status = ServerPublishState.NONE;

	protected abstract void parseSingleStateDecoration(String decoration);

	protected void parse(final TreeItem item){
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				org.eclipse.swt.widgets.TreeItem swtItem = item.getSWTWidget();

				if (!isStyled(swtItem)){
					name = item.getText();
				} else {
					parseStyledLabel(item, swtItem);					
				}
			}
		});
	}

	private void parseStyledLabel(final TreeItem item, org.eclipse.swt.widgets.TreeItem swtItem) {
		StyleRange range = getStyle(swtItem);
		name = item.getText().substring(0, range.start).trim();
		String styledText = item.getText().substring(range.start, range.start + range.length);
		parseDecoration(styledText);;
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
	 * Returns if the server label contains state and status information. These are added as "decorations" - as a styled text. 
	 * 
	 * @param swtItem
	 * @return
	 */
	private boolean isStyled(org.eclipse.swt.widgets.TreeItem swtItem){
		return getStyle(swtItem) != null;
	}

	/**
	 * Style is stored in the widget's data and is applied to server's state and status. 
	 * @param swtItem
	 * @return
	 */
	private StyleRange getStyle(org.eclipse.swt.widgets.TreeItem swtItem){
		Object data = swtItem.getData("org.eclipse.jfacestyled_label_key_0");

		if (data == null) {
			return null;
		}

		if (!(data instanceof StyleRange[])){
			throw new EclipseLayerException("Cannot parse server label. Data for key 'org.eclipse.jfacestyled_label_key_0' are " +
					"expected to be of type " + StyleRange[].class + " but are " + data.getClass());
		}

		StyleRange[] styles = (StyleRange[]) data;

		if (styles.length == 0) {
			return null;
		}

		if (styles.length != 1) {
			throw new EclipseLayerException("Cannot parse server label. More than one style found");
		}

		return styles[0];
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
