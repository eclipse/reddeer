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
package org.jboss.reddeer.eclipse.core.resources;

import java.util.List;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Abstract Project used as parent of specific projects (e.g. Java, Maven,
 * General...).
 * 
 * @author mlabuda@redhat.com
 *
 */
public abstract class AbstractProject extends DefaultResource implements Project {

	/**
	 * Creates {@link AbstractProject}.
	 *
	 * @param item
	 *            the item
	 */
	public AbstractProject(TreeItem item) {
		super(item);
		if (!natureMatches(treeViewerHandler.getNonStyledText(item), getNatureIds())) {
			throw new EclipseLayerException(
					"Project nature IDs of chosen project does not " + "match required project nature IDs");
		}
	}

	@Override
	abstract public String[] getNatureIds();

	private boolean natureMatches(String projectName, String[] requiredNatureIds) {
		List<String> projectNatureIds = org.jboss.reddeer.direct.project.Project.getProjectNatureIds(projectName);
		if (requiredNatureIds != null) {
			for (String requiredNatureId : requiredNatureIds) {
				if (!projectNatureIds.contains(requiredNatureId)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void delete(boolean deleteFromFileSystem) {
		deleteResource(deleteFromFileSystem);
	}
}
