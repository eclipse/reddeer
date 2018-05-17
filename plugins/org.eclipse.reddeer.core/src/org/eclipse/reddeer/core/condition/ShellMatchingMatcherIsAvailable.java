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
package org.eclipse.reddeer.core.condition;

import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.matcher.AndMatcher;
import org.eclipse.reddeer.core.util.InstanceValidator;
import org.eclipse.reddeer.core.lookup.ShellLookup;

/**
 * Condition is met when a shell matching title with the specified matcher is available.  
 * 
 * @author mlabuda@redhat.com
 *
 */
public class ShellMatchingMatcherIsAvailable extends AbstractWaitCondition {

	private AndMatcher matcher;
	protected Shell foundShell;
	
	/**
	 * Creates new ShellMatchingMatcherIsAvailable wait condition with specified matcher.
	 * @param matchers matchers to match shell
	 */
	public ShellMatchingMatcherIsAvailable(Matcher<?>... matchers) {
		InstanceValidator.checkNotNull(matchers, "matcher");
		AndMatcher am  = new AndMatcher(matchers);
		this.matcher = am;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		Shell[] availableShells = ShellLookup.getInstance().getShells();
		for (Shell shell: availableShells) { 
			if (matcher.matches(shell)) {
				this.foundShell = shell;
				return true;
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
	public Shell getResult(){
		return this.foundShell;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
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
