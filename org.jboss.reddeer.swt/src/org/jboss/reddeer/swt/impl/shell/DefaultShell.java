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
public class DefaultShell extends BasicShell implements Shell {

	public DefaultShell() {
		try {
			shell = Bot.get().shells()[0];
			logger.info("Default [0] Shell found");
		}
		catch (WidgetNotFoundException e) {
			throw new WidgetNotAvailableException("No shell is available at the moment");
		}
	}
	
}	
	

