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

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;

/**
 * Condition is met when a shell with specific text (title) is available.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 * @author jniederm
 */
public class ShellWithTextIsAvailable extends ShellMatchingMatcherIsAvailable { 

	/**
	 * Constructs ShellWithTextIsAvailable wait condition.
	 * Condition is met when a shell with the specified title is available.
	 *
	 * @param title the title
	 */
	public ShellWithTextIsAvailable(String title) {
		super(new WithTextMatcher(title));
	}
	
	/**
	 * Constructs ShellWithTextIsAvailable wait condition. Condition is met when
	 * a shell matching matcher is available.
	 * 
	 * @param matcher matcher matching title of the shell
	 */
	public ShellWithTextIsAvailable(Matcher<String> matcher) {
		super(matcher);
	}
}
