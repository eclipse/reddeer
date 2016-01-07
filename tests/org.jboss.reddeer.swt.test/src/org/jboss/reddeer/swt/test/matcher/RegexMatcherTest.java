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
