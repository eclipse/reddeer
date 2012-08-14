package org.jboss.reddeer.swt.impl.menu;

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

	public ContextMenu(String... path) {
		
		MenuLookup l = new MenuLookup();
		TextMatchers m = new TextMatchers(path);
		l.lookFor(m.getMatchers());
		this.path = path;
	}
	
	@Override
	public void select() 
	{	
		MenuLookup l = new MenuLookup();
		TextMatchers m = new TextMatchers(path);
		l.select(m.getMatchers());
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
}
