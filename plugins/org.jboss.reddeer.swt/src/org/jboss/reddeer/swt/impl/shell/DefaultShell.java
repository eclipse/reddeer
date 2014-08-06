package org.jboss.reddeer.swt.impl.shell;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.lookup.ShellLookup;
/**
 * Default shell returns active shell if available if not it returns first
 * available shell
 * 
 * @author Jiri Peterka, Andrej Podhradsky
 * 
 */
public class DefaultShell extends AbstractShell {
	private static final Logger log = Logger.getLogger(DefaultShell.class);
	public DefaultShell(String title) {
		super(ShellLookup.getInstance().getShell(title));
		try {
			setFocus();
			log.debug("Shell with title '" + title + "' found");
		} catch (Exception e) {
			throw new SWTLayerException("No shell with title '" + title + "' is available", e);
		}
	}

	public DefaultShell() {
		super(ShellLookup.getInstance().getActiveShell());
		try {
			setFocus();
			log.debug("Active shell with title '" + getText() + "' found");
		} catch (Exception e) {
			throw new SWTLayerException("No active shell is available at the moment", e);
		}
	}

}
