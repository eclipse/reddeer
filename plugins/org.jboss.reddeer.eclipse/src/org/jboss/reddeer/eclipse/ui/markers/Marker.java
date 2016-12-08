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
package org.jboss.reddeer.eclipse.ui.markers;

import java.util.List;

import org.jboss.reddeer.eclipse.ui.views.MarkersView;
import org.jboss.reddeer.eclipse.ui.views.AbstractMarkersSupportView.Column;
import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Marker represents a marker in Markers view.
 * 
 * @author mlabuda@redhat.com
 * @since 2.0
 */
public class Marker extends AbstractMarker {

	private String markerType;
	
	public Marker(String markerType, TreeItem markerItem) {
		super(markerItem);
		this.markerType = markerType;
	}

	/**
	 * Gets marker type. Returned markers type is plain type 
	 * of marker. E.g. 'Java Problems' without the item number info.
	 * 
	 * @return type of a marker
	 */
	public String getMarkerType() {
		return markerType;
	}
	
	@Override
	protected String getCell(Column column) {
		MarkersView markersView = new MarkersView();
		List<String> columns = markersView.getProblemColumns();
		if (columns.contains(column.toString())) {
			return markerItem.getCell(markersView.getIndexOfColumn(column));
		}
		return null;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
