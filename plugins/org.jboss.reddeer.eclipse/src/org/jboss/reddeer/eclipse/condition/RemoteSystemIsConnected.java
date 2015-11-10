package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.eclipse.rse.ui.view.System;

/**
 * Returns true, if remote system with specified name isConnected
 * 
 * @author Pavol Srna
 *
 */
public class RemoteSystemIsConnected extends AbstractWaitCondition {

	private System system;
	
	/**
	 * Construct the condition with a given system.
	 * 
	 * @param system System
	 */
	public RemoteSystemIsConnected(System system) {
		this.system = system;
	}

	@Override
	public boolean test() {
			return system.isConnected();
	}
	
	@Override
	public String description() {
		return "remote system with name: " + this.system.getLabel() + "is connected";
	}
	
}
