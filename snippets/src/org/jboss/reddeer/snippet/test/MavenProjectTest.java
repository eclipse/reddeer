package org.jboss.reddeer.snippet.test;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.snippet.test.example.MavenProject;
import org.junit.Test;

public class MavenProjectTest {

	@Test
	public void getMavenProject() {
		MavenProject mavenProject = 
		        new ProjectExplorer().getProject("mavenProject01", MavenProject.class);
		mavenProject.updateDependencies();
	}
}
