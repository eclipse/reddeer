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

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.swt.api.Browser;

/**
 * Condition is met when page is fully loaded in a {@link Browser}.
 * 
 * @author Vlado Pakan
 *
 */
public class PageIsLoaded extends AbstractWaitCondition {

	private Browser browser;

	/**
	 * Constructs PageIsLoaded wait condition. Condition is met when 
	 * page is loaded in specified browser. 
	 * 
	 * @param browser browser where to test page availability status (loaded or not)
	 */
	public PageIsLoaded(Browser browser) {
		this.browser = browser;
	}

	@Override
	public boolean test() {
		return browser.isPageLoaded();
	}

	@Override
	public String description() {
		return "page is loaded to browser";
	}

}
