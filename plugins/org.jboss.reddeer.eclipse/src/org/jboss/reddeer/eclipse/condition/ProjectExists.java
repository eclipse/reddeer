package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.swt.condition.WaitCondition;

/**
 * Returns true if project exists in explorer
 * @author rawagner
 *
 */
public class ProjectExists implements WaitCondition{
	
	private String projectName;
	
	/**
	 * Default constructor
	 * @param projectName name of the project to find
	 */
	public ProjectExists(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public boolean test() {
		PackageExplorer pe = new PackageExplorer();
		pe.open();
		return pe.containsProject(projectName);
	}

	@Override
	public String description() {
		return "Project "+projectName+" exists.";
	}
	
	

}
