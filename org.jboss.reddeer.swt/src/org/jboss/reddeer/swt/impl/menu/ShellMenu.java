package org.jboss.reddeer.swt.impl.menu;

import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.lookup.impl.MenuLookup;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatchers;

/**
 * Shell menu implementation
 * @author Jiri Peterka
 *
 */
public class ShellMenu extends AbstractMenu implements Menu {
	
	private MenuItem menuItem;

	/**
	 * Create Menu instance from menu of given shell
	 * 
	 * @param shell
	 */
	public ShellMenu(Shell shell) {

	}
	
	/**
	 * Uses WithMnemonicMatcher to match menu item label. It means that all ampersands
	 * and shortcuts within menu item label are ignored when searching for menu
	 * @param path
	 */
	public ShellMenu(final String... path) {
		this(new WithMnemonicMatchers(path).getMatchers());
	}
	
	
	public ShellMenu(final Matcher<String>... matchers) {
		
		MenuLookup ml = new MenuLookup();
		menuItem = ml.lookFor(ml.getActiveShellTopMenuItems(), matchers);		
		this.matchers = matchers;

	}
	
	@Override
	public void select() {
		MenuLookup ml = new MenuLookup();
		ml.select(menuItem);
	}
	

	@Override
	public String getText() {
		MenuLookup ml = new MenuLookup();
		MenuItem i = ml.lookFor(ml.getActiveShellTopMenuItems(), matchers);
		String text = ml.getMenuItemText(i);
		return text;
	}

}
