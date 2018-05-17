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
package org.eclipse.reddeer.gef.matcher;

import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Matches tool entry with a given label.
 * 
 * @author apodhrad
 *
 */
public class IsToolEntryWithLabel extends BaseMatcher<PaletteEntry> {

	private String label;

	/**
	 * Instantiates a new checks if is tool entry with label.
	 *
	 * @param label the label
	 */
	public IsToolEntryWithLabel(String label) {
		this.label = label;
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.Matcher#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(Object obj) {
		if (obj instanceof ToolEntry) {
			ToolEntry toolEntry = (ToolEntry) obj;
			return toolEntry.getLabel().equals(label);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	@Override
	public void describeTo(Description desc) {
		desc.appendText("is ToolEntry with label '" + label + "'");
	}

}
