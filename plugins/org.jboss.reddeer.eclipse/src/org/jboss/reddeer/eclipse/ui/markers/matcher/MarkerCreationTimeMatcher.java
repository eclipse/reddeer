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
