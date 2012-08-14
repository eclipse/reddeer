package org.jboss.reddeer.swt.impl.menu;

import org.jboss.reddeer.swt.api.Menu;

/**
 * Default menu implementation - menu of active shell by default
 * @author Jiri Peterka
 *
 */
public class DefaultMenu extends AbstractMenu implements Menu {


	public DefaultMenu(String... path) {
		this.path = path;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}


}
