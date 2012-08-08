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
	 * Shell menu based on regexSeq
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
				items = s.getMenu().getItems(); 
				
				while (iterator.hasNext()) {
					items = s.getMenu().getItems();
					Pattern p = iterator.next();
					log.info("Current pattern:" + p.pattern());
					if (!found.get())
						// 
						break;
					int itemIndex = 0;

					// iterate through all available menu items
					log.info("Items count: " + items.length);
					for (MenuItem i : items) {
						log.info("Available menuitem:" + i.getText());
						// filter & 
						text = i.getText().replaceAll("&", "");
						log.info("Text:" + text);
						
						// match
						Matcher m =  p.matcher(text);
						if (m.matches()) {
							log.info("Attached menuitem:" + i.getText());
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
						log.info(itemIndex);
						// last item reached
						if (items.length == itemIndex) {
							found.set(false);
							log.debug("Not found pattern:" + iterator.next().pattern());
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
