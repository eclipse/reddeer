package org.jboss.reddeer.swt.impl.menu;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.widgets.MenuItem;
import org.jboss.reddeeer.swt.regex.RegexSeq;
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
	 * Shell menu based on regexSeq (regular expression path)
	 * @param regexSeq
	 */
	public ShellMenu(final RegexSeq regexSeq) {

		
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
				Iterator<Pattern> iterator = regexSeq.getIterator();
				items = s.getMenuBar().getItems(); 
				
				while (iterator.hasNext()) {
					Pattern p = iterator.next();
					log.debug("Current pattern:" + p.pattern());
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
						Matcher m =  p.matcher(text);
						if (m.matches()) {
							log.debug("Attached menuitem:" + i.getText());
							// last level of regexes?
							regexIndex++;							
							if (regexIndex < regexSeq.getPatterns().size()) {
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
							log.debug("Not found pattern:" + p.pattern());
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
