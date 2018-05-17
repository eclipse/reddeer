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
package org.eclipse.reddeer.eclipse.ui.views.markers;

import java.util.List;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.condition.AbstractExtendedMarkersViewIsUpdating;
import org.eclipse.reddeer.eclipse.ui.markers.Marker;
import org.eclipse.reddeer.eclipse.ui.markers.matcher.AbstractMarkerMatcher;

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
