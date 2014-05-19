package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.eclipse.rse.ui.view.System;
import org.jboss.reddeer.swt.condition.WaitCondition;

/**
 * Returns true, if remote system with specified name isConnected
 * 
 * @author Pavol Srna
 *
 */
public class RemoteSystemIsConnected implements WaitCondition {

	private System system;
	
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
