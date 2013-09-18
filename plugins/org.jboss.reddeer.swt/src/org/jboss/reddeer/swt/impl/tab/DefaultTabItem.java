package org.jboss.reddeer.swt.impl.tab;

import org.jboss.reddeer.swt.util.Bot;

/**
 * 
 * @author apodhrad
 *
 */
public class DefaultTabItem extends AbstractTabItem {

	public DefaultTabItem(String tabLabel) {
		tabItem = Bot.get().tabItem(tabLabel);
	}
}
