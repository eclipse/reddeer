package org.jboss.reddeer.swt.impl.menu;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.lookup.impl.ShellLookup;
import org.jboss.reddeer.swt.util.Display;

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
	
	/**
	 * 
	 * @param matchers
	 */
	public ShellMenu(final Matcher<String>... matchers) {
		
		final AtomicBoolean found = new AtomicBoolean(true);

		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				org.eclipse.swt.widgets.Shell s = new ShellLookup().getActiveShell();
				MenuItem[] items = null;

				// menu bar
				int regexIndex = 0;
				// iterate through given path
				String text = "";
				Iterator<Matcher<String>> iterator = Arrays.asList(matchers).iterator();
				items = s.getMenuBar().getItems(); 
				
				while (iterator.hasNext()) {
					Matcher<String> m = iterator.next();
					int itemIndex = 0;
					
					if (!found.get())
						// 
						break;

					// iterate through all available menu items
					log.debug("Items count: " + items.length);
					for (MenuItem i : items) {
						log.debug("Available menuitem:" + i.getText());
						// filter & 
						text = i.getText().replaceAll("&", "");
						log.debug("Text:" + text);
						
						// match
						if (m.matches(text)) {
							log.debug("Attached menuitem:" + i.getText());
							// last level of regexes?
							regexIndex++;							
							if (regexIndex < matchers.length) {
								items = i.getMenu().getItems();
							}
							// skip to next requested path level
							break;
						}
						
						// no mach, continue
						itemIndex++;
						log.debug(itemIndex + "/" + items.length);
						// last item reached
						if (items.length == itemIndex) {
							log.debug("Last item reached");
							found.set(false);
						}
					}
					
				}
			}

		});

		
		if (!found.get()) {
			String message = "Menu item not found";
			log.error(message);
			throw new WidgetNotAvailableException(message);

		}
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

}
