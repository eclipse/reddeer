package org.jboss.reddeer.swt.impl.menu;

import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.lookup.impl.MenuLookup;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatchers;

/**
 * Context Menu implementation for all context menu related to some Control.
 * Control must have focus to provide context menu
 * 
 * @author Jiri Peterka
 * 
 */
public class ContextMenu extends AbstractMenu implements Menu {

	/**
	 * Context menu given by String path
	 * Uses WithMnemonicMatcher to match menu item label. It means that all ampersands
	 * and shortcuts within menu item label are ignored when searching for menu
	 * @param path
	 */
	public ContextMenu(String... path) {
		this(new WithMnemonicMatchers(path).getMatchers());		
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
		MenuLookup ml = new MenuLookup();
		MenuItem i = ml.lookFor(ml.getTopMenuMenuItemsFromFocus(), matchers);
		String text = ml.getMenuItemText(i);
		return text;
	}
}
