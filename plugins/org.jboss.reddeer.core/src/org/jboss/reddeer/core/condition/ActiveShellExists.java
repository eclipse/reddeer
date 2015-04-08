package org.jboss.reddeer.core.condition;

import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.core.lookup.ShellLookup;

/**
 * Condition is met when shell is active.
 *
 * @author mlabuda@redhat.com
 *
 */

public class ActiveShellExists implements WaitCondition {

	@Override
	public boolean test() {
		return ShellLookup.getInstance().getCurrentActiveShell() != null;
	}

	@Override
	public String description() {
		return "shell is active";
	}
}