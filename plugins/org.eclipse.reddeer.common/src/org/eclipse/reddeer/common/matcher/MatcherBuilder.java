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
package org.eclipse.reddeer.common.matcher;

import org.hamcrest.Matcher;

/**
 * Builder for building more complex matchers.
 *
 * @author Jiri Peterka
 *
 */
public class MatcherBuilder {

	private static MatcherBuilder instance;

	/**
	 * Gets singleton instance of MatcherBuilder.
	 *
	 * @return instance of MatcherBuilder
	 */
	public static MatcherBuilder getInstance() {
		if (instance == null) {
			instance = new MatcherBuilder();
		}
		return instance;
	}

	private MatcherBuilder() {
	}

	/**
	 * Adds matcher to the beginning of array of matchers.
	 * 
	 * @param matchers array of matchers
	 * @param matcher matcher to add to array of matchers
	 * @return new array containing old array of matchers and new matcher 
	 */
	@SuppressWarnings("rawtypes")
	public Matcher[] addMatcher(Matcher[] matchers, Matcher matcher) {
		Matcher[] finalMatchers = new Matcher[matchers.length + 1];
		finalMatchers[0]=matcher;
		for (int i = 1; i < finalMatchers.length; i++) {
			finalMatchers[i] = matchers[i-1];
		}

		return finalMatchers;
	}
}
