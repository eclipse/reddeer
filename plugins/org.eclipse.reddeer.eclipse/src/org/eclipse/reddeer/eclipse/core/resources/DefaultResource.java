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
package org.eclipse.reddeer.eclipse.core.resources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.jface.exception.JFaceLayerException;
import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * Default implementation of generic resource in various explorers or navigator.
 * 
 * @author mlabuda@redhat.com
 * @since 2.0
 */
public class DefaultResource extends AbstractResource {

	public DefaultResource(TreeItem item) {
		super(item);
	}
	
	@Override
	public Resource getResource(String... path) {
		activateWrappingView();
		TreeItem item = treeItem;
		for (int i = 0; i < path.length; i++) {
			String pathSegment = path[i];
			try {
				item = item.getItem(pathSegment);
			} catch (CoreLayerException ex) {
				// there is no item with specific path segment, time to use name
				// without decorators
				try {
					item = treeViewerHandler.getTreeItem(item, pathSegment);
				} catch (JFaceLayerException exception) {
					// non existing item
					logger.debug("Obtaining direct children on the current level");
					List<TreeItem> items = item.getItems();
					logger.debug("Item \"" + pathSegment + "\" was not found. Available items on the current level:");
					for (TreeItem treeItem : items) {
						logger.debug("\"" + treeItem.getText() + "\"");
					}
					throw new EclipseLayerException("Cannot get resource specified by path."
							+ "Resource either does not exist or solution is ambiguous because "
							+ "of existence of more tree items on the path with same name without decorators");
				}
			}
		}
		
		return new DefaultResource(item);
	}
	
	@Override
	public List<Resource> getChildren() {
		activateWrappingView();
		List<Resource> children = new ArrayList<Resource>();

		for (TreeItem item : treeItem.getItems()) {
			children.add(new DefaultResource(item));
		}

		return children;
	}
}
