package org.jboss.reddeer.swt.impl.tab;

import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Widget;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.TabFolder;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.TabFolderHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * Abstract class for all TabFolder implementations
 * 
 * @author Andrej Podhradsky
 * 
 */
public class AbstractTabFolder implements TabFolder {

	protected final Logger logger = Logger.getLogger(this.getClass());

	protected org.eclipse.swt.widgets.TabFolder swtTabFolder;

	protected AbstractTabFolder(final org.eclipse.swt.widgets.TabFolder swtTabFolder) {
		if (swtTabFolder != null) {
			this.swtTabFolder = swtTabFolder;
		} else {
			throw new SWTLayerException("SWT TabFolder passed to constructor is null");
		}
	}

	@Override
	public String[] getTabItemLabels() {
		TabItem[] tabItem = TabFolderHandler.getInstance().getTabItems(swtTabFolder);
		String[] tabItemLabel = new String[tabItem.length];
		for (int i = 0; i < tabItem.length; i++) {
			tabItemLabel[i] = WidgetHandler.getInstance().getText(tabItem[i]);
		}
		return tabItemLabel;
	}

	@Override
	public Widget getSWTWidget() {
		return swtTabFolder;
	}

	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtTabFolder);
	}
}
