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
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;
import org.eclipse.reddeer.swt.api.TableItem;

/**
 * Checks that the table item column with columnIndex text matches.
 *  
 * @author Vlado Pakan
 *
 */
public class ColumnTableItemMatcher extends TypeSafeMatcher<TableItem> {
	private int columnIndex;
	private Matcher<String> matcher;
	/**
	 * Creates matcher for matching tree item with text of column with index columnIndex 
	 * is equal to columnText
	 * @param columnIndex index of column
	 * @param columnText matcher to match column
	 */
	public ColumnTableItemMatcher (int columnIndex , String columnText){
		this(columnIndex,new IsEqual<String>(columnText));
	}
	/**
	 * Creates matcher for matching tree item with text of column with index columnIndex 
	 * matching matcher
	 * @param columnIndex index of column
	 * @param matcher matcher to match column
	 */
	public ColumnTableItemMatcher (int columnIndex , Matcher<String> matcher){
		this.columnIndex = columnIndex;
		this.matcher = matcher;
	}
	
	@Override
	protected boolean matchesSafely(TableItem item) {
		return matcher.matches(item.getText(columnIndex));
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("table item column with index: " + columnIndex
				+ " matches " + matcher);
	}
}
