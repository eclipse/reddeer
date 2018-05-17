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
package org.eclipse.reddeer.eclipse.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.core.resources.ProjectItem;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;

/**
 * Returns true if project contains specified project item
 * @author rawagner
 *
 */
public class ProjectContainsProjectItem extends AbstractWaitCondition {
	
	private DefaultProject project;
	private String[] path;
	private ProjectItem resultProject;
	
	
	/**
	 * Default Constructor.
	 *
	 * @param project to check
	 * @param itemPath path of item (including item) to search for
	 */
	public ProjectContainsProjectItem(DefaultProject project, String... itemPath) {
		this.project = project;
		this.path = itemPath;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		try{
			this.resultProject = project.getProjectItem(path);
		} catch (EclipseLayerException ex){
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "Project "+project.getName()+ " contains project item "+path[path.length-1];
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public ProjectItem getResult() {
		return this.resultProject;
	}
	
}