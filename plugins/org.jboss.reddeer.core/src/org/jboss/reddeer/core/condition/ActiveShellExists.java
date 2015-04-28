package org.jboss.reddeer.core.condition;

import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.core.lookup.ShellLookup;

/**
 * Condition is met when active shell exists.
 *
 * @author mlabuda@redhat.com
 * @since 0.8.0
 */

public class ActiveShellExists implements WaitCondition {

	@Override
	public boolean test() {
		return ShellLookup.getInstance().getCurrentActiveShell() != null;
	}

	@Override
	public String description() {
		return "active shell exists";
	}
}