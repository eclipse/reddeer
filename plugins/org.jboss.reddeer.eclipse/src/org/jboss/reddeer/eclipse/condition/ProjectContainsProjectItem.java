package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.common.condition.WaitCondition;

/**
 * Returns true if project contains specified project item
 * @author rawagner
 *
 */
public class ProjectContainsProjectItem implements WaitCondition {
	
	private Project project;
	private String[] path;
	
	/**
	 * Default Constructor
	 * @param project to check
	 * @param itemPath path of item (including item) to search for
	 */
	public ProjectContainsProjectItem(Project project, String... itemPath) {
		this.project = project;
		this.path = itemPath;
	}

	@Override
	public boolean test() {
		try{
			project.getProjectItem(path);
		} catch (EclipseLayerException ex){
			return false;
		}
		return true;
	}

	@Override
	public String description() {
		return "Project "+project.getName()+ " contains project item "+path[path.length-1];
	}
	
}