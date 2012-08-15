package org.jboss.reddeer.swt.impl.menu;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.lookup.impl.MenuLookup;

/**
 * Abstract class for all Menu implementations
 * 
 * @author Jiri Peterka
 * 
 */
public abstract class AbstractMenu implements Menu {

	protected final Logger log = Logger.getLogger(this.getClass());

	SWTBotMenu menu;
	String[] path;
	Matcher<String>[] matchers;


	@Override
	public void select() 
	{	
		MenuLookup l = new MenuLookup();		
		l.select(null, this.matchers);
	}
	
	@Override
	public String getText() {
		throw new UnsupportedOperationException("not yet implemented");
	}
}
