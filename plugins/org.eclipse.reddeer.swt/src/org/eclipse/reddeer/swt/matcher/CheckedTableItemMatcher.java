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
package org.eclipse.reddeer.swt.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.eclipse.reddeer.swt.api.TableItem;

/**
 * Checks that the table item is checked.
 *  
 * @author Lucia Jelinkova
 *
 */
public class CheckedTableItemMatcher extends TypeSafeMatcher<TableItem> {

	@Override
	protected boolean matchesSafely(TableItem item) {
		return item.isChecked();
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("table item is not checked");
	}
}
