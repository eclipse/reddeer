/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.reddeer.core.test.condition.matchers;

import org.eclipse.swt.widgets.TableItem;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.core.test.condition.shells.TableShell;

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
