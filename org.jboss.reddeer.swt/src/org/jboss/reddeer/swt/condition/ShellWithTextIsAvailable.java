package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.junit.logging.Logger;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.matcher.ObjectToStingMatcherDecorator;
import org.jboss.reddeer.swt.util.Utils;

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
		Utils.checkNotNull(title, "title");
		this.matcher = wrapStringMatcher(new IsEqual<String>(title));
	}

	/**
	 * @throws IllegalArgumentException if {@code matcher} is {@code null}
	 */
	public ShellWithTextIsAvailable(Matcher<String> matcher) {
		Utils.checkNotNull(matcher, "matcher");
		this.matcher = wrapStringMatcher(matcher);
	}
	
	private static Matcher<String> wrapStringMatcher(final Matcher<String> innerMatcher) {
		return new ObjectToStingMatcherDecorator(innerMatcher);
	}


	@Override
	public boolean test() {
		log.debug("Looking for shell with title matching '" + matcher + "'");
		Shell shell = ShellLookup.getInstance().getShell(matcher);
		return shell != null;
	}

	@Override
	public String description() {
		return "Shell with title matching " + matcher + " is not available";
	}
}
