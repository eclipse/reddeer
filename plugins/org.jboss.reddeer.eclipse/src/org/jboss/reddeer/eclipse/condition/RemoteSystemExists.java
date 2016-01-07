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
import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.eclipse.rse.ui.view.SystemView;

/**
 * Returns true, if there is remote system with specified name
 * 
 * @author Pavol Srna
 *
 */
public class RemoteSystemExists extends AbstractWaitCondition {

	private String name;
	
	/**
	 * Constructs the condition with a given text.
	 * 
	 * @param name Name
	 */
	public RemoteSystemExists(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		try{
			new SystemView().getSystem(this.name);
			return true;
		} catch (RedDeerException rde){
			return false;
		}		
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "there is remote system with name: " + this.name;
	}
	
}
