package org.jboss.reddeer.swt.matcher;

import org.hamcrest.Matcher;

/**
 * Builder for building more complex matchers
 *
 * @author Jiri Peterka
 *
 * @deprecated since 0.8, use {@link #org.jboss.reddeer.core.matcher.MatcherBuilder}
 */
@Deprecated

public class MatcherBuilder {

	private static MatcherBuilder instance;

	/**
	 * Creates and returns builder instance
	 *
	 * @return builder instance
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
	 * adds matcher into array of matchers and returns new array
	 * @param matchers given array of matchers
	 * @param matcher given matcher to be added
	 * @return new array containing all matchers
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
