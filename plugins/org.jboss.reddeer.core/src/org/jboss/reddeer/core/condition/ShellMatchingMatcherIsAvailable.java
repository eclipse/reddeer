package org.jboss.reddeer.core.condition;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.core.util.InstanceValidator;

/**
 * Wait condition for a shell matching the specified matcher to the shell title.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class ShellMatchingMatcherIsAvailable implements WaitCondition {

	private Matcher<String> matcher;
	private Logger logger = Logger.getLogger(ShellMatchingMatcherIsAvailable.class);
	
	/**
	 * Creates new ShellMatchingMatcherIsAvailable wait condition with specified matcher.
	 * @param matcher matcher to match shell title
	 */
	public ShellMatchingMatcherIsAvailable(Matcher<String> matcher) {
		InstanceValidator.checkNotNull(matcher, "matcher");
		this.matcher = matcher;
	}

	@Override
	public boolean test() {
		logger.debug("Looking for shell with title matching matcher");
		Shell[] availableShells = ShellLookup.getInstance().getShells();
		for (Shell shell: availableShells) { 
			if (matcher.matches(shell)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String description() {
		return "shell matching matcher is available.";
	}
}
