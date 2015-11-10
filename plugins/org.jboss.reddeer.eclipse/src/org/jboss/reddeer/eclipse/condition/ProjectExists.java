package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.eclipse.jdt.ui.AbstractExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;

/**
 * Returns true if project exists in explorer
 * @author rawagner
 *
 */
public class ProjectExists extends AbstractWaitCondition{
	
	private String projectName;
	private AbstractExplorer explorer;
	
	/**
	 * Default constructor
	 * @param projectName name of the project to find
	 */
	public ProjectExists(String projectName) {
		this(projectName , new PackageExplorer());
	}
	
	/**
	 * Constructor with specified view
	 * @param projectName name of the project to find
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
}
