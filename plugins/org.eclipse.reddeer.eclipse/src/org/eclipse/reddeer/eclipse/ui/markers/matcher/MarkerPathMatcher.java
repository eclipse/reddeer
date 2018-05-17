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
 * Marker matcher for column Path of a marker.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public class MarkerPathMatcher extends AbstractMarkerMatcher {
	
	/**
	 * Creates a new marker matcher matching to whole text of Path column.
	 * 
	 * @param text whole Path column text of a marker to match
	 */
	public MarkerPathMatcher(String text) {
		super(text);
	}
	
	/**
	 * marker a new problem matcher matching with matcher for Path column.
	 * 
	 * @param matcher matcher to match Path column of a marker
	 */
	public MarkerPathMatcher(Matcher<String> matcher) {
		super(matcher);
	}

	@Override
	public Column getColumn() {
		return Column.PATH;
	}
}
