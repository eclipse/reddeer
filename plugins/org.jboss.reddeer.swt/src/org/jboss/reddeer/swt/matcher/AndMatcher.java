package org.jboss.reddeer.swt.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Matcher matching weather all matchers are satisfied
 * @author Jiri Peterka
 *
 */
@SuppressWarnings("rawtypes")
public class AndMatcher extends BaseMatcher {

	Matcher[] matchers;
	
	
	public AndMatcher(Matcher... matchers) {
		this.matchers = matchers;
	}
	
	@Override
	public boolean matches(Object item) {
		for (Matcher m : matchers) {
			if (!m.matches(item)) return false;
		}
		return true;
	}

	@Override
	public void describeTo(Description description) {
		// TODO
	}



}
