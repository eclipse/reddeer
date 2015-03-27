package org.jboss.reddeer.swt.impl.shell;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.exception.SWTLayerException;
import org.jboss.reddeer.core.lookup.ShellLookup;
/**
 * Default shell returns active shell if available if not it returns first
 * available shell
 * 
 * @author Jiri Peterka, Andrej Podhradsky, mlabuda@redhat.com
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

	/**
	 * 
	 * Creates a new DefaultShell matching specified matcher. First found shell with 
	 * specified matcher is created. Beware, there is no strict (deterministic) order of shells.
	 * 
	 * @param matcher matcher to match title of a shell
	 */
	public DefaultShell(Matcher<String> matcher) {
		super(ShellLookup.getInstance().getShell(matcher));
		try {
			setFocus();
			log.debug("Shell matching specified matcher is found and focused");
		} catch (Exception e) {
			throw new SWTLayerException("Shell matching specified matcher was not focused successfully.", e);
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
