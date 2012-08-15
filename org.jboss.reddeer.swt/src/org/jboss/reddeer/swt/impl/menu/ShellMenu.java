package org.jboss.reddeer.swt.impl.menu;

import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.lookup.impl.MenuLookup;
import org.jboss.reddeer.swt.matcher.TextMatchers;

/**
 * Shell menu implementation
 * @author Jiri Peterka
 *
 */
public class ShellMenu extends AbstractMenu implements Menu {

	/**
	 * Create Menu instance from menu of given shell
	 * 
	 * @param shell
	 */
	public ShellMenu(Shell shell) {

	}
	
	
	public ShellMenu(final String... path) {
		this(new TextMatchers(path).getMatchers());
	}
	
	
	public ShellMenu(final Matcher<String>... matchers) {
		
		MenuLookup ml = new MenuLookup();
		ml.lookFor(ml.getActiveShellTopMenuItems(), matchers);		
		this.matchers = matchers;

	}
	
	@Override
	public void select() {
		MenuLookup ml = new MenuLookup();
		ml.select(ml.getActiveShellTopMenuItems(), matchers);
	}
	

	@Override
	public String getText() {
		MenuLookup ml = new MenuLookup();
		MenuItem i = ml.lookFor(ml.getActiveShellTopMenuItems(), matchers);
		String text = ml.getMenuItemText(i);
		return text;
	}

}
