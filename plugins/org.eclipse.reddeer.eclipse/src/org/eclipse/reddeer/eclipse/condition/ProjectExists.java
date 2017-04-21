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
package org.eclipse.reddeer.eclipse.condition;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.AbstractExplorer;

/**
 * Returns true if project exists in explorer
 * @author rawagner, jkopriva@redhat.com
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
		this(projectName , new PackageExplorerPart());
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

	@Override
	public boolean test() {
		explorer.open();
		return explorer.containsProject(projectName);
	}

	@Override
	public String description() {
		return "Project "+projectName+" exists.";
	}

	@Override
	public String errorMessageWhile() {
		return "Project with name '" + projectName + "' still exists.";
	}
	
	@Override
	public String errorMessageUntil() {
		List<String> projects = explorer.getProjects().stream().map(it -> it.getName()).collect(Collectors.toList());
		return "Project with name '" + projectName + "' does not exists. Existing projects: " + projects.toString();
	}
}
