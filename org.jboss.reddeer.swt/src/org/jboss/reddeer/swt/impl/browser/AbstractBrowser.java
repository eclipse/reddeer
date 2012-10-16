package org.jboss.reddeer.swt.impl.browser;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotBrowser;
import org.jboss.reddeer.swt.exception.SWTLayerException;

/**
 * Abstract class for all Browsers implementations
 * @author Jiri Peterka
 *
 */
public class AbstractBrowser {
	
	protected void setReady() {
		try {
			browser.setFocus();
		}
		catch (WidgetNotFoundException e) {
			new SWTLayerException("Browser is not available");
		}
	}
	protected SWTBotBrowser browser;
	
	
}
