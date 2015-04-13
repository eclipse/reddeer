package org.jboss.reddeer.eclipse.test.jst.j2ee.ui.project.facet;

import static org.junit.Assert.*;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet.UtilityProjectFirstPage;
import org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet.UtilityProjectWizard;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class UtilityProjectWizardTest {

	
	private static final String PROJECT_NAME ="UtilityProject";
	
	@After
	public void deleteProject() {
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		DeleteUtils.forceProjectDeletion(pe.getProject(PROJECT_NAME), true);
	}
	
	@Test
	public void createProject(){
		UtilityProjectWizard uw = new UtilityProjectWizard();
		uw.open();
		UtilityProjectFirstPage up = new UtilityProjectFirstPage();
		up.setProjectName(PROJECT_NAME);
		uw.finish();
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		assertTrue(pe.containsProject(PROJECT_NAME));
	}
}
