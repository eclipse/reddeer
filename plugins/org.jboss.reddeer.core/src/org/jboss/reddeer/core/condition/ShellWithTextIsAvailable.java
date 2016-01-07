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
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.util.InstanceValidator;

/**
 * Condition is met when a shell with specific text (title) is available.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 * @author jniederm
 */
public class ShellWithTextIsAvailable extends AbstractWaitCondition { 
	private Matcher<String> matcher;
	private static final Logger log = Logger.getLogger(ShellWithTextIsAvailable.class);

	/**
	 * Constructs ShellWithTextIsAvailable wait condition.
	 * Condition is met when a shell with the specified title is available.
	 *
	 * @param title the title
	 */
	public ShellWithTextIsAvailable(String title) {
		InstanceValidator.checkNotNull(title, "title");
		this.matcher = new WithTextMatcher(title);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		log.debug("Looking for shell with title matching '" + matcher + "'");
		Shell shell = ShellLookup.getInstance().getShell(matcher, TimePeriod.NONE);
		return shell != null;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "shell with title matching " + matcher + " is available";
	}
}
