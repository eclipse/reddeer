package org.jboss.reddeer.direct.condition;

import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.jboss.reddeer.swt.condition.WaitCondition;

/**
 * Project exist is condition checking wheather workspace contains 
 * specific project
 * @author Jiri Peterka
 *
 */
public class ProjectExists implements WaitCondition {

	private String name; 

	/**
	 * Condition check if project exists
	 * @param name project name
	 */
	public ProjectExists(String name) {
		this.name = name;
	}

	/**
	 * Tests if project exists 
	 * @return returns true if project exists, false otherwise
	 */
	@Override	
	public boolean test() {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		return project.exists();
	}

	/**
	 * Returns condition description
	 * @return condition description
	 */
	public String description() {
		return "Project " + name + " exists";		
	}

}
