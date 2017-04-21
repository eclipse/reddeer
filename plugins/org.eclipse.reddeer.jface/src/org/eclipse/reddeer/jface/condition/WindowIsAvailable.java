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
package org.eclipse.reddeer.jface.condition;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.AndMatcher;
import org.eclipse.reddeer.common.matcher.MatcherBuilder;
import org.eclipse.reddeer.core.lookup.ShellLookup;
import org.eclipse.reddeer.jface.matcher.WindowMatcher;

/**
 * Condition is met when Window matching given matchers is available
 * @author rawagner
 *
 */
public class WindowIsAvailable extends AbstractWaitCondition{
	
	private Logger logger = Logger.getLogger(WindowIsAvailable.class);
	private AndMatcher matcher;
	private Shell foundShell;
	
	/**
	 * 
	 * @param matchers to match window.
	 */
	public WindowIsAvailable(Matcher<?>... matchers) {
		this(org.eclipse.jface.window.Window.class, matchers);
	}
	
	/**
	 * 
	 * @param windowClass if you need to specify window class (found in shell's data)
	 * @param matchers to match window
	 */
	public WindowIsAvailable(Class<?> windowClass, Matcher<?>...matchers){
		WindowMatcher wm = new WindowMatcher(windowClass);
		Matcher<?>[] allMatchers = MatcherBuilder.getInstance().addMatcher(matchers, wm);
		this.matcher  = new AndMatcher(allMatchers);
	}
	
	@Override
	public boolean test() {
		logger.debug("Looking for Window matching "+matcher);
		Shell[] availableShells = ShellLookup.getInstance().getShells();
		for (Shell shell: availableShells) { 
			if (matcher.matches(shell)) {
				foundShell = shell;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns found shell or null if no shell was found
	 * @return found shell
	 */
	public Shell getResult(){
		return foundShell;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "window matching "+matcher+" is available.";
	}
	

}
