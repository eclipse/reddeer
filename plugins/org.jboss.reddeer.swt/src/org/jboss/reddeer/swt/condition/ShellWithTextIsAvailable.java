package org.jboss.reddeer.swt.condition;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.util.internal.InstanceValidator;

/**
 * Condition is fulfilled when shell with title is available
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 * @author jniederm
 * 
 */
public class ShellWithTextIsAvailable implements WaitCondition { 
	private Matcher<String> matcher;
	private final Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * @throws IllegalArgumentException if {@code title} is {@code null}
	 */
	public ShellWithTextIsAvailable(String title) {
		InstanceValidator.checkNotNull(title, "title");
		this.matcher = new WithTextMatcher(title);
	}

	@Override
	public boolean test() {
		log.debug("Looking for shell with title matching '" + matcher + "'");
		Shell shell = ShellLookup.getInstance().getShell(matcher);
		return shell != null;
	}

	@Override
	public String description() {
		return "shell with title matching " + matcher + " is available";
	}
}
