package org.jboss.reddeer.swt.condition;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.util.internal.InstanceValidator;
import org.jboss.reddeer.swt.wait.TimePeriod;

/**
 * Condition is met when shell with specific text (title) is available.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 * @author jniederm
 */
public class ShellWithTextIsAvailable implements WaitCondition { 
	private Matcher<String> matcher;
	private static final Logger log = Logger.getLogger(ShellWithTextIsAvailable.class);

	/**
	 * Constructs ShellWithTextIsAvailable wait condition.
	 * Condition is met when shell with the specified title is available.
	 * 
	 * @param text text/name of the shell
	 */
	public ShellWithTextIsAvailable(String title) {
		InstanceValidator.checkNotNull(title, "title");
		this.matcher = new WithTextMatcher(title);
	}

	@Override
	public boolean test() {
		log.debug("Looking for shell with title matching '" + matcher + "'");
		Shell shell = ShellLookup.getInstance().getShell(matcher, TimePeriod.NONE);
		return shell != null;
	}

	@Override
	public String description() {
		return "shell with title matching " + matcher + " is available";
	}
}
