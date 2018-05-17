/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.jface.window;

import org.eclipse.reddeer.core.util.InstanceValidator;
import org.hamcrest.Matcher;

/**
 * Openable defines runnable action how open JFace window
 * @author rawagner
 *
 */
public abstract class Openable implements Runnable {
	
	private Matcher<?>[] shellMatchers;
	
	public Openable(Matcher<?>... shellMatchers){
		InstanceValidator.checkNotNull(shellMatchers, "shellMatchers");
		if(shellMatchers.length == 0){
			throw new IllegalArgumentException("shellMatchers cannot be empty!");
		}
		this.shellMatchers = shellMatchers;
	}
	
	public Matcher<?>[] getShellMatchers(){
		return this.shellMatchers;
	}

}
