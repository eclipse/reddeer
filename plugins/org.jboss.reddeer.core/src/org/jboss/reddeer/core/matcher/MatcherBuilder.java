package org.jboss.reddeer.core.matcher;

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
	 * Adds matcher into array of matchers.
	 * 
	 * @param matchers array of matchers
	 * @param matcher matcher to add to array of matchers
	 * @return new array containing old array of matchers and new matcher 
	 */
	@SuppressWarnings("rawtypes")
	public Matcher[] addMatcher(Matcher[] matchers, Matcher matcher) {
		Matcher[] finalMatchers = new Matcher[matchers.length + 1];
		for (int i = 0; i < matchers.length; i++) {
			finalMatchers[i] = matchers[i];
		}
		finalMatchers[finalMatchers.length - 1] = matcher;

		return finalMatchers;
	}
}
