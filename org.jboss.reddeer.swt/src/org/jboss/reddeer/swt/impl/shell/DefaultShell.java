package org.jboss.reddeer.swt.impl.shell;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Default shell returns first available shell 
 * @author Jiri Peterka
 *
 */
public class DefaultShell extends AbstractShell implements Shell {

	
	public DefaultShell(String title) {
		try {
			shell = Bot.get().shell(title);
			log.info("Default " + title + " found");
		}
		catch (WidgetNotFoundException e) {
			throw new WidgetNotAvailableException("No shell is available at the moment");
		}
	}
	

	public DefaultShell() {
		try {
			shell = Bot.get().activeShell();
			log.info("No active shell found");
		}
		catch (WidgetNotFoundException e) {
			throw new WidgetNotAvailableException("No active shell is available at the moment");
		}
	}
	
}	
	

