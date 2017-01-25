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

import java.util.List;
import java.util.stream.Collectors;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.eclipse.jdt.ui.AbstractExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;

/**
 * Returns true if project exists in explorer
 * @author rawagner
 * @contributor jkopriva@redhat.com
 *
 */
public class ProjectExists extends AbstractWaitCondition{
	
	private String projectName;
	private AbstractExplorer explorer;
	
	/**
	 * Default constructor.
	 *
	 * @param projectName name of the project to find
	 */
	public ProjectExists(String projectName) {
		this(projectName , new PackageExplorer());
	}
	
	/**
	 * Constructor with specified view.
	 *
	 * @param projectName name of the project to find
	 * @param explorer the explorer
	 */
	public ProjectExists(String projectName , AbstractExplorer explorer) {
		this.projectName = projectName;
		this.explorer = explorer;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		explorer.open();
		return explorer.containsProject(projectName);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "Project "+projectName+" exists.";
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#errorMessageWhile()
	 */
	@Override
	public String errorMessageWhile() {
		return "Project with name '" + projectName + "' still exists.";
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#errorMessageUntil()
	 */
	@Override
	public String errorMessageUntil() {
		List<String> projects = explorer.getProjects().stream().map(it -> it.getName()).collect(Collectors.toList());
		return "Project with name '" + projectName + "' does not exists. Existing projects: " + projects.toString();
	}
}
