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
package org.jboss.reddeer.eclipse.ui.views.markers;

import java.util.List;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.eclipse.condition.AbstractExtendedMarkersViewIsUpdating;
import org.jboss.reddeer.eclipse.ui.markers.Marker;
import org.jboss.reddeer.eclipse.ui.markers.matcher.AbstractMarkerMatcher;

/**
 * Represents the Markers view.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class AllMarkersView extends AbstractMarkersSupportView {


	
	/**
	 * Constructs a view with title Markers.
	 */
	public AllMarkersView() {
		super("Markers");
	}
	
	/**
	 * Gets a list of markers of specified type matching specified matchers.
	 * 
	 * @param markerType type of a problem, provide type of problem without
	 * 			 numerous info in parenthesis, e.g. 'Java Problems', 'Maven Problems' etc.
	 * @param matchers matchers of columns
	 * @return list of markers
	 */
	public List<Marker> getMarker(String markerType, AbstractMarkerMatcher... matchers) {
		activate();
		new WaitUntil(new MarkersViewMarkerIsUpdating(),TimePeriod.SHORT,false);
		new WaitWhile(new MarkersViewMarkerIsUpdating());
		
		return getMarkers(Marker.class, markerType, matchers);
	}
	
	/**
	 * Returns true if Problems view marker is updating its UI.
	 */
	@SuppressWarnings("restriction")
	private class MarkersViewMarkerIsUpdating extends AbstractExtendedMarkersViewIsUpdating {

		public MarkersViewMarkerIsUpdating() {
			super(AllMarkersView.this, org.eclipse.ui.internal.views.markers.AllMarkersView.class);
		}
	}
}
