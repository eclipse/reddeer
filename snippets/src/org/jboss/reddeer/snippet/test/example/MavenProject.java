package org.jboss.reddeer.snippet.test.example;

import org.jboss.reddeer.eclipse.core.resources.AbstractProject;
import org.jboss.reddeer.swt.api.TreeItem;

public class MavenProject extends AbstractProject {

    public MavenProject(TreeItem item) {
        super(item);
    }

    @Override
    public String[] getNatureIds() {
        return new String[] {"org.eclipse.m2e.core.maven2Nature"};
    }

    public void updateDependencies() {
    	// here goes logic
    }
}