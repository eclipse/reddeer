package org.jboss.reddeer.requirements.cleanworkspace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.Project;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;

/**
 * This requirement ensures, that all projects are deleted from workspace (aka. workspace is clean)
 * 
 * @author rhopp
 * 
 */

public class CleanWorkspaceRequirement implements Requirement<CleanWorkspace> {

	
	@Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
	public @interface CleanWorkspace{
		
	}
	
	/**
	 * This should be possible every time.
	 */
	@Override
	public boolean canFulfill() {
		return true; 
	}

	/**
	 * Deletes all projects from workspace
	 */
	
	@Override
	public void fulfill() {	
		PackageExplorer packageExplorer = new PackageExplorer();
		packageExplorer.open();
		List<Project> projects = packageExplorer.getProjects();
		for (Project project : projects) {
			project.delete(true);
		}
	}

	@Override
	public void setDeclaration(CleanWorkspace declaration) {
		// nothing to do here
	}

}
