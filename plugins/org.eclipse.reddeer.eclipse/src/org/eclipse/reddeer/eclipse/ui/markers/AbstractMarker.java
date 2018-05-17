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
package org.eclipse.reddeer.eclipse.ui.markers;

import org.eclipse.reddeer.eclipse.ui.views.markers.QuickFixWizard;
import org.eclipse.reddeer.eclipse.ui.views.markers.AbstractMarkersSupportView.Column;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;

/**
 * Abstract marker item in view supporting markers. Get methods return values of a specific column
 * of a marker. If there is no value for a specific column or a column is hidden, null is returned.
 * 
 * @author mlabuda@redhat.com
 * @since 2.0
 *
 */
public abstract class AbstractMarker {
	
	protected TreeItem markerItem;
	
	public AbstractMarker(TreeItem markerItem) {
		this.markerItem = markerItem;
	}

	protected abstract String getCell(Column column);
	
	/**
	 * Gets description of the marker. Description contains specific information about marker.
	 * 
	 * @return description of the marker
	 */
	public String getDescription() {
		return getCell(Column.DESCRIPTION);
	}

	/**
	 * Gets resource of a marker - place where the marker is occurred.
	 * 
	 * @return resource of the marker
	 */
	public String getResource() {
		return getCell(Column.RESOURCE);
	}

	/**
	 * Gets path to the marker. If getResource returns only package, this is empty. If getResource contains a class
	 * of a marker then path contains specific package containing the class.
	 * 
	 * @return path to the marker
	 */
	public String getPath() {
		return getCell(Column.PATH);
	}

	/**
	 * Gets ID of the marker. ID is numeric value.
	 * 
	 * @return ID of the marker
	 */
	public String getId() {
		return getCell(Column.ID);
	}

	/**
	 * Gets location of the marker. Location is either line in a file with the marker or contains information
	 * that marker is present on build path etc.
	 * 
	 * @return location of the marker
	 */
	public String getLocation() {
		return getCell(Column.LOCATION);
	}

	/**
	 * Gets nature of the marker.
	 * 
	 * @return nature of the marker
	 */
	public String getType() {
		return getCell(Column.TYPE);
	}

	/**
	 * Gets time when the marker was created.
	 * 
	 * @return creation time of the marker
	 */
	public String getCreationTime() {
		return getCell(Column.CREATION_TIME);
	}
	
	/**
	 * Open quickfix.
	 *
	 * @return Quickfix wizard
	 */
	public QuickFixWizard openQuickFix(){
		markerItem.select();
		new ContextMenuItem("Quick Fix").select();
		return new QuickFixWizard();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		String description = getDescription();
		String resource = getResource(); 
		String path = getPath();
		String id = getId();
		String location = getLocation();
		String type = getType();
		String creationTime = getCreationTime();
		
		if (description != null) {
			builder.append("description: '" + description + "'; ");
		}
		if (resource != null) {
			builder.append("resource: '" + resource + "'; ");
		}
		if (path != null) {
			builder.append("path: '" + path + "'; ");
		}
		if (id != null) {
			builder.append("id: '" + id + "'; ");
		}
		if (location != null) {
			builder.append("location: '" + location + "'; ");
		}
		if (type != null) {
			builder.append("type: '" + type + "'; ");
		}
		if (creationTime != null) {
			builder.append("creationTime: '" + creationTime + "'; ");
		}
		return builder.toString();
	}
}
