package org.jboss.reddeer.swt.impl.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	Logger log = Logger.getLogger(BasicMenu.class);
	SWTBotMenu menu;
	
	@Override
	public void select(String... items) {
		
		String current = "";
		try {
			log.info("Menu selection:");
			
			current = items[0];
			menu = Bot.get().menu(current);
			
			String[] subMenuItems = removeElementInArray(items, 0);
			
			for (String item : subMenuItems) {
				current = item;
				menu = menu.menu(item);
				log.info(item + " -> ");
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
	
	private String[] removeElementInArray(String[] array, int position) {
		List<String> list = new ArrayList<String>(Arrays.asList(array));
		list.remove(position);
		String[] temp = new String[array.length-1]; 
		list.toArray(temp);
		return temp;
	}
}
