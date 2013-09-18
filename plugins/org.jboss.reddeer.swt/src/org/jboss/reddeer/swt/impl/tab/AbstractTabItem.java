package org.jboss.reddeer.swt.impl.tab;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTabItem;
import org.jboss.reddeer.swt.api.TabItem;

public class AbstractTabItem implements TabItem {

	protected final Logger logger = Logger.getLogger(this.getClass());

	protected SWTBotTabItem tabItem;

	@Override
	public void activate() {
		tabItem.activate();
	}

}
