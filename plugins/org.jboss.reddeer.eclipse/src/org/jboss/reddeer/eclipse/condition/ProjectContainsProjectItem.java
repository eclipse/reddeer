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
package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;

/**
 * Returns true if project contains specified project item
 * @author rawagner
 *
 */
public class ProjectContainsProjectItem extends AbstractWaitCondition {
	
	private Project project;
	private String[] path;
	
	/**
	 * Default Constructor.
	 *
	 * @param project to check
	 * @param itemPath path of item (including item) to search for
	 */
	public ProjectContainsProjectItem(Project project, String... itemPath) {
		this.project = project;
		this.path = itemPath;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		try{
			project.getProjectItem(path);
		} catch (EclipseLayerException ex){
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "Project "+project.getName()+ " contains project item "+path[path.length-1];
	}
	
}