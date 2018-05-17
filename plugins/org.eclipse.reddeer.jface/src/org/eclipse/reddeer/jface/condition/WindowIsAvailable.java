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
package org.eclipse.reddeer.jface.condition;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.AndMatcher;
import org.eclipse.reddeer.common.matcher.MatcherBuilder;
import org.eclipse.reddeer.core.lookup.ShellLookup;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.core.util.InstanceValidator;
import org.eclipse.reddeer.jface.api.Window;
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
	private Window window;
	
	public WindowIsAvailable(Window window) {
		InstanceValidator.checkNotNull(window, "window");
		InstanceValidator.checkNotNull(window.getShell(), "window shell");
		this.window = window;
	}
	
	/**
	 * 
	 * @param windowTitle window title
	 */
	public WindowIsAvailable(String windowTitle) {
		this(new WithTextMatcher(windowTitle));
	}
	
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
	public <T extends org.eclipse.jface.window.Window> WindowIsAvailable(Class<T> windowClass, Matcher<?>...matchers){
		WindowMatcher wm = new WindowMatcher(windowClass);
		Matcher<?>[] allMatchers = MatcherBuilder.getInstance().addMatcher(matchers, wm);
		this.matcher  = new AndMatcher(allMatchers);
	}
	
	@Override
	public boolean test() {
		if(window != null) {
			for(org.eclipse.swt.widgets.Shell s: ShellLookup.getInstance().getShells()){
				if(window.getShell().getSWTWidget().equals(s)){
					return true;
				}
			}
		} else {
			logger.debug("Looking for Window matching "+matcher);
			Shell[] availableShells = ShellLookup.getInstance().getShells();
			for (Shell shell: availableShells) { 
				if (matcher.matches(shell)) {
					this.foundShell = shell;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns found shell or null if no shell was found
	 * @return found shell
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Shell getResult() {
		return this.foundShell;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		if(window != null) {
			return "window is available";
		}
		return "window matching "+matcher+" is available.";
	}
	

}
