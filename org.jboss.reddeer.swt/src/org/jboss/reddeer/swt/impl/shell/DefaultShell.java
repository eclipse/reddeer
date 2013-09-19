package org.jboss.reddeer.swt.impl.shell;

import org.jboss.reddeer.swt.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.matcher.TextMatcher;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Default shell returns active shell if available if not it returns first
 * available shell
 * 
 * @author Jiri Peterka, Andrej Podhradsky
 * 
 */
public class DefaultShell extends AbstractShell {

	public DefaultShell(String title) {
		try {
			new WaitUntil(new ShellWithTextIsAvailable(title), TimePeriod.NORMAL);
			swtShell = ShellLookup.getInstance().getShell(new TextMatcher(title));
			WidgetHandler.getInstance().setFocus(swtShell);
			log.info("Shell with title '" + title + "' found");
		} catch (Exception e) {
			throw new SWTLayerException("No shell with title '" + title + "' is available", e);
		}
	}

	public DefaultShell() {
		try {
			swtShell = ShellLookup.getInstance().getActiveShell();
			WidgetHandler.getInstance().setFocus(swtShell);
			log.info("Active shell with title '" + getText() + "' found");
		} catch (Exception e) {
			throw new SWTLayerException("No active shell is available at the moment", e);
		}
	}

}
