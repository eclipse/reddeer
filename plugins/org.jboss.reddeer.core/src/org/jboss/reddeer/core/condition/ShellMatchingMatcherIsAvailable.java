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
package org.jboss.reddeer.core.condition;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.matcher.AndMatcher;
import org.jboss.reddeer.core.util.InstanceValidator;
import org.jboss.reddeer.core.lookup.ShellLookup;

/**
 * Condition is met when a shell matching title with the specified matcher is available.  
 * 
 * @author mlabuda@redhat.com
 *
 */
public class ShellMatchingMatcherIsAvailable extends AbstractWaitCondition {

	private AndMatcher matcher;
	private Logger logger = Logger.getLogger(ShellMatchingMatcherIsAvailable.class);
	private Shell foundShell;
	
	/**
	 * Creates new ShellMatchingMatcherIsAvailable wait condition with specified matcher.
	 * @param matchers matcher to match shell title
	 */
	public ShellMatchingMatcherIsAvailable(Matcher<?>... matchers) {
		InstanceValidator.checkNotNull(matchers, "matcher");
		AndMatcher am  = new AndMatcher(matchers);
		this.matcher = am;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		logger.debug("Looking for shell matching "+matcher);
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
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "shell matching "+matcher+" is available.";
	}
	
	@Override
	public String errorMessageWhile() {
		return "shell matching "+matcher+" is still available.";
	}
	
	@Override
	public String errorMessageUntil() {
		return "shell matching "+matcher+" not found.";
	}
}
