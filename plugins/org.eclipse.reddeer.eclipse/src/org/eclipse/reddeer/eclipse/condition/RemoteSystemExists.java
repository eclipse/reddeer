/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.condition;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.eclipse.rse.ui.view.SystemViewPart;
import org.eclipse.reddeer.eclipse.rse.ui.view.System;

/**
 * Returns true, if there is remote system with specified name
 * 
 * @author Pavol Srna, jkopriva@redhat.com
 *
 */
public class RemoteSystemExists extends AbstractWaitCondition {

	private String name;
	private SystemViewPart view;
	private System resultSystem;
	
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
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		try{
			this.resultSystem = this.view.getSystem(this.name);
			return true;
		} catch (RedDeerException rde){
			return false;
		}		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "there is remote system with name: " + this.name;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#errorMessage()
	 */
	@Override
	public String errorMessageWhile() {
		return "system with name: '" + this.name + "' has been found.";
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#errorMessage()
	 */
	@Override
	public String errorMessageUntil() {
		List<String> systems = view.getSystems().stream().map(it -> it.getLabel()).collect(Collectors.toList());
		return "system with name: '" + this.name + "' has not been found. Existing systems: " + systems.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public System getResult() {
		return this.resultSystem;
	}
}
