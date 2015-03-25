package org.jboss.reddeer.swt.test.matcher;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.junit.Test;

public class RegexMatcherTest {

	@Test
	public void regexMatcherMatchesEverythingTest() {
		Matcher<String> m = new RegexMatcher(".*");
		assertThat("test", m);
		assertThat("", m);
	}

	@Test
	public void regexMatcherDigitsTest() {
		Matcher<String> m = new RegexMatcher("\\d*");
		assertThat("123", m);
	}

	@Test(expected = AssertionError.class)
	public void regexMatcherFailureTest() {
		Matcher<String> m = new RegexMatcher("\\d*");
		assertThat("123s", m);
	}

}
