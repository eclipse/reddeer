/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.ui.markers.matcher;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.eclipse.ui.views.markers.AbstractMarkersSupportView.Column;

/**
 * Marker matcher for column ID of a problem.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public class MarkerIDMatcher extends AbstractMarkerMatcher {

	/**
	 * Creates a new marker matcher matching to whole text of ID column.
	 * 
	 * @param text whole ID column text of a marker to match
	 */
	public MarkerIDMatcher(String text) {
		super(text);
	}
	
	/**
	 * Creates a new marker matcher matching with matcher for ID column.
	 * 
	 * @param matcher matcher to match ID column of a marker
	 */
	public MarkerIDMatcher(Matcher<String> matcher) {
		super(matcher);
	}
	
	@Override
	public Column getColumn() {
		return Column.ID;
	}
}
