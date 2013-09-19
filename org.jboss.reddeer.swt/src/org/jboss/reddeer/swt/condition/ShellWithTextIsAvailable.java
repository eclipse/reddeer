package org.jboss.reddeer.swt.condition;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.matcher.TextMatcher;

/**
 * Condition is fulfilled when shell with title is available
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 * 
 */
public class ShellWithTextIsAvailable implements WaitCondition {

	private String title;
	private final Logger log = Logger.getLogger(this.getClass());

	public ShellWithTextIsAvailable(String title) {
		this.title = title;
	}

	@Override
	public boolean test() {
		log.debug("Looking for shell with title '" + title + "'");
		Shell shell = ShellLookup.getInstance().getShell(new TextMatcher(title));
		return shell != null;
	}

	@Override
	public String description() {
		return "Shell with title " + title + " is not available";
	}
}
