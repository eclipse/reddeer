package org.jboss.reddeer.eclipse.test.jdt.internal.ui;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.junit.Test;

public class ProjectExplorerTest extends RedDeerTest{

	private ProjectExplorer projectExplorer;
	
	@Test
	public void open() {
		projectExplorer = new ProjectExplorer();
		projectExplorer.open();
	}
}
