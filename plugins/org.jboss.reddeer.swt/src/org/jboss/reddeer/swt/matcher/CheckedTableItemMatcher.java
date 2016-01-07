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
package org.jboss.reddeer.swt.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.swt.api.TableItem;

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
