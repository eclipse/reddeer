package org.jboss.reddeer.swt.impl.menu;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Abstract class for all Menu implementations
 * @author Jiri Peterka
 *
 */
public abstract class BasicMenu implements Menu {
	
	protected final Logger log = Logger.getLogger(this.getClass());
	
	SWTBotMenu menu;
	
	@Override
	public void select(String... items) {
		
		String current = "";
		try {
			log.info("Menu selection:");
			for (String item : items) {
				current = item;
				menu = Bot.get().menu(item);
				log.debug(item + " -> ");
			}
			menu.click();		
			log.info("Last item clicked ");
		}
		catch (WidgetNotFoundException e) {
			
			String message =  "Menuitem " + current + " cannot be found,  " + e.getMessage(); 
			log.error(message);
			throw new WidgetNotAvailableException(message);
		}
	}
	
	@Override
	public void select() {
		if (menu != null) {
			menu.click();
		}		
	}
	
}
