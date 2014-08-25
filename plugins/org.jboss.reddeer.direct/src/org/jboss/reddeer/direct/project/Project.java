package org.jboss.reddeer.direct.project;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

/**
 * Utils handling project via API
 * @author vlado pakan
 *
 */
public class Project {
	/**
	 * Deletes Eclipse project via Eclipse API
	 * @param projectName
	 * @param deleteContent
	 * @param force
	 */
	public static void delete(String projectName, boolean deleteContent , boolean force ){
		try {
			ResourcesPlugin.getWorkspace()
				.getRoot()
				.getProject(projectName)
				.delete(deleteContent, force, null);
		} catch (CoreException ce) {
			throw new RuntimeException("Unalbe to delete project " + projectName,ce);
		}
	}
}
