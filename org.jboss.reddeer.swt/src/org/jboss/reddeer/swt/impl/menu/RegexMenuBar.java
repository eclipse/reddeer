package org.jboss.reddeer.swt.impl.menu;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.lookup.impl.ShellLookup;
import org.jboss.reddeer.swt.util.Display;

/**
 * Menu implementation for menu matching a regular expression on active shell
 * Static menuitems only
 * 
 * @author Jiri Peterka
 * 
 */
public class RegexMenuBar extends BasicMenu implements Menu {

	public RegexMenuBar(final String... regexes) {

		
		final AtomicBoolean found = new AtomicBoolean(true);

		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Shell s = new ShellLookup().getActiveShell();
				MenuItem[] items = s.getMenuBar().getItems();

				// menu bar
				int regexIndex = 0;
				// iterate through given path
				String text = "";
				for (String regex : regexes) {
					if (!found.get())
						break;
					int itemIndex = 0;

					// iterate through all available menu items
					for (MenuItem i : items) {
						log.debug("Available menuitem:" + i.getText());
						// filter & 
						text = i.getText().replaceAll("&", "");
						log.info("Text:" + text);
						
						// match
						if (text.matches(regex)) {
							log.debug("Attached menuitem:" + i.getText());
							// last level of regexes?
							regexIndex++;							
							if (regexIndex < regexes.length) {
								items = i.getMenu().getItems();
							}
							// go to next path level
							break;
						}
						
						// no mach, continue
						itemIndex++;
						log.info(itemIndex);
						// last item reached
						if (items.length == itemIndex) {
							found.set(false);
							log.debug("Not found pattern:" + regex);
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
}