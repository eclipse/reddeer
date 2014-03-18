package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.swt.lookup.ShellLookup;

public class ShellIsActive implements WaitCondition{
	@Override
	public boolean test() {
		return ShellLookup.getInstance().getCurrentActiveShell() != null;
	}
	@Override
	public String description() {
		return "shell is active";
	}
}