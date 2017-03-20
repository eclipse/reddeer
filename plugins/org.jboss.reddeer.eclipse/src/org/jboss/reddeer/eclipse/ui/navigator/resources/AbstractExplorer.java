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
package org.jboss.reddeer.eclipse.ui.navigator.resources;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.eclipse.core.resources.AbstractProject;
import org.jboss.reddeer.eclipse.core.resources.DefaultProject;
import org.jboss.reddeer.eclipse.core.resources.DefaultProjectItem;
import org.jboss.reddeer.eclipse.core.resources.ProjectItem;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.jface.handler.TreeViewerHandler;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Common ancestor for Package and Project Explorer and Resource Navigator and any similar ones.
 * Contains common operations for those explorers.
 * 
 * @author Jiri Peterka
 * @author mlabuda@redhat.com
 *
 */
public class AbstractExplorer extends WorkbenchView {
	
	protected static final Logger log = Logger.getLogger(AbstractExplorer.class);

	public AbstractExplorer(String viewTitle) {
		super(viewTitle);
	}

	/**
	 * Selects projects with specified names.
	 * 
	 * @param projectName names of projects
	 */
	public void selectProjects(String... projectName){
		ArrayList<TreeItem> selectTreeItems = new ArrayList<TreeItem>();
		for(String pname: projectName){
			selectTreeItems.add(getProject(pname).getTreeItem()); //check if project exists
		}
		if (selectTreeItems.size() > 0){
			getTree().selectItems(selectTreeItems.toArray(new TreeItem[]{}));
		}
	}
	
	/**
	 * Selects all projects. If there are not projects do nothing.
	 */
	public void selectAllProjects(){
		List<DefaultProject> projects = getProjects();
		List<TreeItem> projectsItems = new ArrayList<TreeItem>();
		if (projects.size() > 0) {
			for (DefaultProject project: projects) {
				projectsItems.add(project.getTreeItem());
			}
		}
		getTree().selectItems(projectsItems.toArray(new TreeItem[projectsItems.size()]));
	}
	
	/**
	 * Finds out whether a project with specified name exists in explorer or not.
	 * 
	 * @param projectName name of a project
	 * @return true if project exists, false otherwise
	 */
	public boolean containsProject(String projectName) {
		boolean result = false;
		try{
			getProject(projectName);
			result = true;
			} catch (EclipseLayerException ele){
				result = false;
			}
		return result;
	}
	
	/**
	 * Gets all projects located in explorer.
	 * 
	 * @return list of projects in explorer
	 */
	public List<DefaultProject> getProjects(){
		List<DefaultProject> projects = new ArrayList<DefaultProject>();

		TreeViewerHandler treeViewerHandler = TreeViewerHandler.getInstance();
		
		for (TreeItem item : getTree().getItems()){
			String projectName = treeViewerHandler.getNonStyledText(item);
			log.debug("Getting project with name "+projectName);
			if (org.jboss.reddeer.direct.project.Project.isProject(projectName)) {
				projects.add(new DefaultProject(item));
			}
		}
		return projects;
	}
	
	/**
	 * Provides list of all project items in the explorer.
	 * @return list of explorer items
	 */
	public List<ProjectItem> getProjectItems() {
		List<ProjectItem> items = new ArrayList<ProjectItem>();
		
		for (TreeItem item : getTree().getItems()) {
			items.add(new DefaultProjectItem(item));
		}		
		return items;
	}
	
	/**
	 * Removes all projects from file system.
	 */
	public void deleteAllProjects(){
		deleteAllProjects(true);
	}
	
	/**
	 * Removes all projects.
	 * 
	 * @param deleteFromFileSystem true if project should be deleted from file system, false otherwise
	 */
	public void deleteAllProjects(boolean deleteFromFileSystem){
		deleteAllProjects(deleteFromFileSystem, TimePeriod.VERY_LONG);
	}
	
	/**
	 * Removes all projects. Wait for a specified time period while refreshing
	 * a project and while handling its deletion.
	 * 
	 * @param deleteFromFileSystem true if project should be deleted from file system, false otherwise
	 * @param timeout time to wait for refresh of a project and its deletion
	 */
	public void deleteAllProjects(boolean deleteFromFileSystem, TimePeriod timeout){
		activate();
		if(getProjects().size() > 0){
			selectAllProjects();
			new ContextMenu("Refresh").select();
			new WaitWhile(new JobIsRunning(), timeout);
			new ContextMenu("Delete").select();
			Shell s = new DefaultShell("Delete Resources");
			new CheckBox().toggle(deleteFromFileSystem);
			new PushButton("OK").click();
			DeleteUtils.handleDeletion(s, timeout);
		}
	}

	private DefaultTree getTree(){
		activate();
		return new DefaultTree(cTabItem);
	}
		
	/**
	 * Gets project with specified project name located in explorer.
	 *  
	 * @param projectName name of a project
	 * @return project with specified name
	 */
	public DefaultProject getProject(String projectName){
		activate();
		for (DefaultProject project : getProjects()){
			if (project.getName().equals(projectName)){
				return project;
			}
		}
		throw new EclipseLayerException("There is no project with name " + projectName);
	}	
	
	/**
	 * Gets project with specific project type defined by subclass of Abstract Project.
	 * 
	 * @param <T> specific project type
	 * @param projectName name of project to get
	 * @param projectType type of project to get
	 * @return project of specific type with defined name
	 */
	public <T extends AbstractProject> T getProject(final String projectName, Class<T> projectType) {		
		for (TreeItem item : getTree().getItems()){
			try {
				T project =  projectType.getDeclaredConstructor(TreeItem.class).newInstance(item);
				if (project.getName().equals(projectName)) {
					return project;
				}
			} catch (EclipseLayerException ex) {
				// Because there are attempts to create from all tree items projects of specific type but
				// not all of them can fit it.
			} catch (ReflectiveOperationException e) {
				// This should not happen.
			}
		}
		
		// There is no such project
		throw new EclipseLayerException("Required project does not exist. Make sure you are using correct project type"
				+ " and desired project exists.");
	}
}
