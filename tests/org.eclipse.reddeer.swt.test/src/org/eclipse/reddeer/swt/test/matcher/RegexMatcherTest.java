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
package org.eclipse.reddeer.swt.test.matcher;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
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
