package org.jboss.reddeer.eclipse.test.ui.dialogs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.Project;
import org.jboss.reddeer.eclipse.ui.dialogs.ProjectPropertyPage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ProjectPropertyPageTest {

	private static final String PROJECT_NAME = "Property test project";
	
	private Project project;
	
	@Before
	public void setup() {
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = dialog.getFirstPage();
		page1.setProjectName(PROJECT_NAME);
		dialog.finish();

		project = new PackageExplorer().getProject(PROJECT_NAME);
	}
	
	@After 
	public void cleanup(){
		Shell shell = null;
		try {
			shell = new DefaultShell(TestPropertyPage.PAGE_TITLE);
			shell.close();
		} catch (SWTLayerException e){
			// not found, no action needed
		}
		
		project.delete(true);
	}
	
	@Test
	public void open() {
		ProjectPropertyPage page = new TestPropertyPageRedDeer(project);
		page.open();
		
		assertThat(page.getName(), is(TestPropertyPage.PAGE_TITLE));
	}

	private class TestPropertyPageRedDeer extends ProjectPropertyPage {
		public TestPropertyPageRedDeer(Project project) {
			super(project, TestPropertyPage.PAGE_TITLE);
		}
	}
}
