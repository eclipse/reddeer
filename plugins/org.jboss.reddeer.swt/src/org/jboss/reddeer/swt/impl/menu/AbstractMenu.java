package org.jboss.reddeer.swt.impl.menu;

import org.jboss.reddeer.common.logging.Logger;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.handler.MenuHandler;
import org.jboss.reddeer.swt.lookup.MenuLookup;

/**
 * Abstract class for all Menu implementations
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractMenu implements Menu {

	protected final Logger log = Logger.getLogger(this.getClass());

	protected String[] path;
	protected Matcher<String>[] matchers;
	protected MenuLookup ml = MenuLookup.getInstance();
	protected MenuHandler mh = MenuHandler.getInstance();


	@Override
	public abstract void select();
	
	@Override
	public abstract boolean isSelected();
	
	@Override
	public String getText() {
		throw new UnsupportedOperationException("not yet implemented");
	}
	
}
