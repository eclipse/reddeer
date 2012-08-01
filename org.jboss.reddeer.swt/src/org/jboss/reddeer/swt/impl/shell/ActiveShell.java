package org.jboss.reddeer.swt.impl.shell;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Shell implementation of active shell
 * 
 * @author Jiri Peterka
 * 
 */
public class ActiveShell extends BasicShell implements Shell {

	public ActiveShell(String title) {
		this();
		logger.info("Looking for shell with title:" + title);
		if (!title.equals(shell.getText())) {			
			String message = "Active shell title is "
					+ shell.getText() + " but expected is " + title;
			
			logger.error(message);		
			throw new WidgetNotAvailableException(message);
		}
	}

	public ActiveShell() {

		try {
			shell = Bot.get().activeShell();
			shell.activate();
			shell.setFocus();
			logger.info("Active Shell found:" + shell.getText());
		}
		catch (WidgetNotFoundException e) {
			throw new WidgetNotAvailableException("Active Shell is not available");
		}
	}

}
