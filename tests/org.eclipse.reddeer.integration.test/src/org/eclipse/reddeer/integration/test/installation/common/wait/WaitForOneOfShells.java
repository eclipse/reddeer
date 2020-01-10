/*******************************************************************************
 * Copyright (c) 2020 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/

package org.eclipse.reddeer.integration.test.installation.common.wait;

import java.util.Arrays;
import java.util.Iterator;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;

public class WaitForOneOfShells {

	public WaitForOneOfShells(String... shellHeaders) {
		this(TimePeriod.VERY_LONG, shellHeaders);
	}
	
	public WaitForOneOfShells(TimePeriod timePeriod, String... shellHeaders) {
		Iterator<String> iterator = Arrays.asList(shellHeaders).iterator();
		StringBuilder builder = new StringBuilder("(");

		while (iterator.hasNext()) {
			builder.append(iterator.next());

			if (iterator.hasNext()) {
				builder.append("|");
			}
		}

		builder.append(")");

		RegexMatcher matcher = new RegexMatcher(builder.toString());
		new WaitUntil(new ShellIsAvailable(new WithTextMatcher(matcher)), timePeriod);
	}
}
