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

import java.util.List;
import java.util.stream.Collectors;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.eclipse.rse.ui.view.SystemViewPart;

/**
 * Returns true, if there is remote system with specified name
 * 
 * @author Pavol Srna, jkopriva@redhat.com
 *
 */
public class RemoteSystemExists extends AbstractWaitCondition {

	private String name;
	private SystemViewPart view;
	
	/**
	 * Constructs the condition with a given text.
	 * 
	 * @param name Name
	 */
	public RemoteSystemExists(String name) {
		this.name = name;
		this.view = new SystemViewPart();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		try{
			view.getSystem(this.name);
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
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#errorMessage()
	 */
	@Override
	public String errorMessageWhile() {
		return "system with name: '" + this.name + "' has been found.";
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#errorMessage()
	 */
	@Override
	public String errorMessageUntil() {
		List<String> systems = view.getSystems().stream().map(it -> it.getLabel()).collect(Collectors.toList());
		return "system with name: '" + this.name + "' has not been found. Existing systems: " + systems.toString();
	}
	
}
