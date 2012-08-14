package org.jboss.reddeer.swt.impl.menu;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.lookup.impl.MenuLookup;
import org.jboss.reddeer.swt.matcher.TextMatchers;

/**
 * Context Menu implementation
 * 
 * @author Jiri Peterka
 * 
 */
public class ContextMenu extends AbstractMenu implements Menu {

	/**
	 * Context menu given by String path
	 * @param path
	 */
	public ContextMenu(String... path) {
		this(new TextMatchers(path).getMatchers());		
	}
	
	/**
	 * Context menu given by matchers
	 * @param matchers
	 */
	public ContextMenu(Matcher<String>... matchers) {
		
		MenuLookup l = new MenuLookup();
		l.lookFor(l.getTopMenuMenuItemsFromFocus(), matchers);
		this.matchers = matchers;
		
	}	
	
	@Override
	public void select() {
		MenuLookup l = new MenuLookup();
		l.select(l.getTopMenuMenuItemsFromFocus(), matchers);
	}

	
	@Override
	public String getText() {
		throw new UnsupportedOperationException("not yet implemented");
	}
}
