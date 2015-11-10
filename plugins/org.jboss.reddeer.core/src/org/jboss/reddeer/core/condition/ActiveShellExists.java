package org.jboss.reddeer.core.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.core.lookup.ShellLookup;

/**
 * Condition is met when active shell exists.
 *
 * @author mlabuda@redhat.com
 * @since 0.8.0
 */

public class ActiveShellExists extends AbstractWaitCondition {

	@Override
	public boolean test() {
		return ShellLookup.getInstance().getCurrentActiveShell() != null;
	}

	@Override
	public String description() {
		return "active shell exists";
	}
}