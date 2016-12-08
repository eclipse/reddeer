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
 * Marker matcher for column Location of a marker.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public class MarkerLocationMatcher extends AbstractMarkerMatcher {

	/**
	 * Creates a new marker matcher matching to whole text of Location column.
	 * 
	 * @param text whole Location column text of a marker to match
	 */
	public MarkerLocationMatcher(String text) {
		super(text);
	}
	
	/**
	 * Creates a new marker matcher matching with matcher for Location column.
	 * 
	 * @param matcher matcher to match Location column of a marker
	 */
	public MarkerLocationMatcher(Matcher<String> matcher) {
		super(matcher);
	}
	
	@Override
	public Column getColumn() {
		return Column.LOCATION;
	}
}
