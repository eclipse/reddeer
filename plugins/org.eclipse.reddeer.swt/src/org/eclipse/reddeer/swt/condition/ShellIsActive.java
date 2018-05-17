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
package org.eclipse.reddeer.swt.condition;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.condition.ShellMatchingMatcherIsActive;
import org.eclipse.reddeer.core.lookup.ShellLookup;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.core.util.InstanceValidator;
import org.eclipse.reddeer.swt.api.Shell;

/**
 * Wait condition for shells checking whether some shell is active (empty
 * constructor) or using Shell.equals (parameterized constructor).
 * 
 * @author rhopp, mlabuda@redhat.com
 */
public class ShellIsActive extends ShellMatchingMatcherIsActive {
	
	private Shell shell;
	private static final Logger log = Logger.getLogger(ShellIsActive.class);
	
	/**
	 * Fulfilled, when active shell is equal to given shell.
	 * 
	 * @param shell Shell to compare to.
	 */
	public ShellIsActive(Shell shell){
		InstanceValidator.checkNotNull(shell, "shell");
		this.shell = shell;
	}
	
	/**
	 * Fulfilled, when active shell is matching given matchers.
	 * 
	 * @param matchers matchers to match active shell
	 */
	public ShellIsActive(Matcher<?>... matchers){
		super(matchers);
	}
	
	/**
	 * Fulfilled, when active shell's text is matching given text.
	 * 
	 * @param shellText text of shell
	 */
	public ShellIsActive(String shellText){
		super(new WithTextMatcher(shellText));
	}
	
	@Override
	public boolean test() {
		if (shell != null){
			org.eclipse.swt.widgets.Shell currentActiveShell = ShellLookup.getInstance()
					.getCurrentActiveShell();
			if (currentActiveShell == null) {
				log.debug("Current active shell is null");
				return false;
			}
			return currentActiveShell.equals(shell.getSWTWidget());
		}
		return super.test();
	}

	@Override
	public String description() {
		if(shell != null){
			return "shell is active";
		}
		return super.description();
	}
}
