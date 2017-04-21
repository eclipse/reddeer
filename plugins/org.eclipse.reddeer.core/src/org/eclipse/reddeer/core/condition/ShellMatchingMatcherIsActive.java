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
package org.eclipse.reddeer.core.condition;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.matcher.AndMatcher;
import org.eclipse.reddeer.core.util.InstanceValidator;
import org.eclipse.reddeer.core.lookup.ShellLookup;

/**
 * Condition is met when a shell with specified text (title) is active.
 * 
 * @author Vlado Pakan
 * @author jniederm
 */

public class ShellMatchingMatcherIsActive extends AbstractWaitCondition {
	
	private AndMatcher matcher;

	/**
	 * Constructs ShellWithTextIsActive wait condition. Condition is met when
	 * a shell matching matcher is active.
	 * 
	 * @param matchers matchers matching shell
	 */
	public ShellMatchingMatcherIsActive(org.hamcrest.Matcher<?>... matchers) {
		InstanceValidator.checkNotNull(matchers, "matchers");
		AndMatcher am  = new AndMatcher(matchers);
		this.matcher = am;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		Shell currentActiveShell = ShellLookup.getInstance().getCurrentActiveShell();
		if(currentActiveShell != null){
			if(matcher.matches(currentActiveShell)){
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "shell matching " + matcher.toString() + " is active";
	}

}