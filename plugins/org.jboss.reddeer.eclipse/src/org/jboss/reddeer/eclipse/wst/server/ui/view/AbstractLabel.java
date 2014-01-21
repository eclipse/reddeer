package org.jboss.reddeer.eclipse.wst.server.ui.view;

import org.eclipse.swt.custom.StyleRange;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
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

	protected abstract void parseDecoration(String decoration);

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

	public String getName() {
		return name;
	}
}
