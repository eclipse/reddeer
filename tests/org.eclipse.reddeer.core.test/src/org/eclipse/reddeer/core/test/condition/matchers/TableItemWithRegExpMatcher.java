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
package org.eclipse.reddeer.core.test.condition.matchers;

import org.eclipse.swt.widgets.TableItem;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.core.test.condition.shells.TableShell;

/**
*
* @author Jan Novak <jnovak@redhat.com>
*/
public class TableItemWithRegExpMatcher extends BaseMatcher<TableItem> {

	private String searchRegexp;
	private RegexMatcher regExp;

	public TableItemWithRegExpMatcher(String searchRegexp) {
		this.searchRegexp = searchRegexp;
		this.regExp = new RegexMatcher(searchRegexp);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("table item matched by regexp ").appendText(searchRegexp);
	}

	@Override
	public boolean matches(Object o) {
		return (o instanceof TableItem) && regExp.matches(TableShell.getIndexCell((TableItem) o));
	}
}
