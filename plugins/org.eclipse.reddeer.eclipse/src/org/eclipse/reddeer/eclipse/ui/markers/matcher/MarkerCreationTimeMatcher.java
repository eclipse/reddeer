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
package org.eclipse.reddeer.eclipse.ui.markers.matcher;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.eclipse.ui.views.markers.AbstractMarkersSupportView.Column;

/**
 * Marker matcher for column Creation Time of a marker.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public class MarkerCreationTimeMatcher extends AbstractMarkerMatcher {

	/**
	 * Creates a new Marker matcher matching to whole text of Creation Time column.
	 * 
	 * @param text whole Creation Time column text of a Marker to match
	 */
	public MarkerCreationTimeMatcher(String text) {
		super(text);
	}
	
	/**
	 * Creates a new Marker matcher matching with matcher for Creation Time column.
	 * 
	 * @param matcher matcher to match Creation Time column of a Marker
	 */
	public MarkerCreationTimeMatcher(Matcher<String> matcher) {
		super(matcher);
	}
	
	@Override
	public Column getColumn() {
		return Column.CREATION_TIME;
	}
}
