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
package org.jboss.reddeer.eclipse.ui.markers.matcher;

import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.ui.views.AbstractMarkersSupportView.Column;

/**
 * Marker matcher for column Resource of a marker.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public class MarkerResourceMatcher extends AbstractMarkerMatcher {

	/**
	 * Creates a new marker matcher matching to whole text of Resource column.
	 * 
	 * @param text
	 *            whole Resource column text of a marker to match
	 */
	public MarkerResourceMatcher(String text) {
		super(text);
	}

	/**
	 * Creates a new marker matcher matching with matcher for Resource column.
	 * 
	 * @param matcher
	 *            matcher to match Resource column of a marker
	 */
	public MarkerResourceMatcher(Matcher<String> matcher) {
		super(matcher);
	}

	@Override
	public Column getColumn() {
		return Column.RESOURCE;
	}
}
