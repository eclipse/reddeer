package org.jboss.reddeer.swt.impl.toolbar;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
import org.jboss.reddeer.swt.api.ToolItem;

/**
 * Abstract class for all Toolbar implementations
 * @author Jiri Peterka
 *
 */
public class AbstractToolItem implements ToolItem {

	protected SWTBotToolbarButton botToolItem;

	@Override
	public void click() {
		botToolItem.click();
		
	}

	@Override
	public String getToolTipText() {
		return botToolItem.getToolTipText();

	}

	
	
}
