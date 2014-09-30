package org.jboss.reddeer.eclipse.test.ui.navigator;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ProjectExplorerTest {

	private ProjectExplorer projectExplorer;
	
	@Test
	public void open() {
		projectExplorer = new ProjectExplorer();
		projectExplorer.open();
	}
}
