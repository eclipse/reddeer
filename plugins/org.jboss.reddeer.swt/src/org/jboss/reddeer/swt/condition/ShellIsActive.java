package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.swt.lookup.impl.ShellLookup;

public class ShellIsActive implements WaitCondition{
	@Override
	public boolean test() {
		return new ShellLookup().getCurrentActiveShell() != null;
	}
	@Override
	public String description() {
		return "Shell is active";
	}
}