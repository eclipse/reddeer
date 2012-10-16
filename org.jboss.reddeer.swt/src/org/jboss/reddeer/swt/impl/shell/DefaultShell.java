package org.jboss.reddeer.swt.impl.shell;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Default shell returns active shell if available
 * if not it returns first available shell
 *  
 * @author Jiri Peterka
 *
 */
public class DefaultShell extends AbstractShell implements Shell {

	
	public DefaultShell(String title) {
		try {
			shell = Bot.get().shell(title);
			log.info("Shell with title '" + title + "' found");
		}
		catch (WidgetNotFoundException e) {
			throw new SWTLayerException("No shell is available at the moment");
		}
	}
	

	public DefaultShell() {
		try {
			shell = Bot.get().activeShell();
			log.info("Active shell with title '" + shell.getText() + "' found");
		}
		catch (WidgetNotFoundException e) {
			throw new SWTLayerException("No active shell is available at the moment");
		}
	}
}	
	

