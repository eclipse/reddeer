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
