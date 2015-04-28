package org.jboss.reddeer.core.condition;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.util.InstanceValidator;

/**
 * Condition is met when a shell with specific text (title) is available.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 * @author jniederm
 */
public class ShellWithTextIsAvailable implements WaitCondition { 
	private Matcher<String> matcher;
	private static final Logger log = Logger.getLogger(ShellWithTextIsAvailable.class);

	/**
	 * Constructs ShellWithTextIsAvailable wait condition.
	 * Condition is met when a shell with the specified title is available.
	 * 
	 * @param text title of the shell
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
