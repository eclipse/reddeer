package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.swt.lookup.ShellLookup;

/**
 * Condition is met when shell is active.
 * 
 * @author mlabuda
 *
 */
public class ShellIsActive implements WaitCondition {
	
	@Override
	public boolean test() {
		return ShellLookup.getInstance().getCurrentActiveShell() != null;
	}

	@Override
	public String description() {
		return "shell is active";
	}
}