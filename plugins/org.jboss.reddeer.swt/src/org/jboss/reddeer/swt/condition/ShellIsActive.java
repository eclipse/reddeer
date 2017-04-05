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
package org.jboss.reddeer.swt.condition;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.condition.ShellMatchingMatcherIsActive;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.util.InstanceValidator;
import org.jboss.reddeer.swt.api.Shell;

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
