/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
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

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
			return system.isConnected();
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "remote system with name: " + this.system.getLabel() + "is connected";
	}
	
}
