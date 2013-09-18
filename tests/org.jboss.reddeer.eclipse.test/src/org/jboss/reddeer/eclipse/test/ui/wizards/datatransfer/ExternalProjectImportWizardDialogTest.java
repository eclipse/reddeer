package org.jboss.reddeer.eclipse.test.ui.wizards.datatransfer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.test.Activator;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage.ImportProject;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.junit.Test;

public class ExternalProjectImportWizardDialogTest extends RedDeerTest {

	private static final File RESOURCES_DIR = new File(Activator.getTestResourcesLocation(ExternalProjectImportWizardDialogTest.class), "projectImport");
	
	private static final String PROJECT_A = "ProjectA";
	
	private static final String PROJECT_B = "ProjectB";
	
	private static final String PROJECT_C = "ProjectC";

	private ExternalProjectImportWizardDialog wizard;
	
	private WizardProjectsImportPage wizardPage;
	
	@Override
	protected void setUp(){
	  super.setUp();
		wizard  = new ExternalProjectImportWizardDialog();
		wizard.open();
		
		wizardPage = wizard.getFirstPage();
	}
	
	@Test
	public void setRootDirectory() {
		wizardPage.setRootDirectory(new File(RESOURCES_DIR, "directory").getAbsolutePath());
		
		List<ImportProject> projects = wizardPage.getProjects();
		assertProjects(projects, true, true, true);
	}

	@Test
	public void setArchiveFile() {
		wizardPage.setArchiveFile(new File(RESOURCES_DIR, "zip/projectImport.zip").getAbsolutePath());
		
		List<ImportProject> projects = wizardPage.getProjects();
		assertProjects(projects, true, true, true);
	}

	@Test
	public void copyProjectsIntoWorkspace_directory_true() {
		wizardPage.setRootDirectory(new File(RESOURCES_DIR, "directory").getAbsolutePath());
		wizardPage.copyProjectsIntoWorkspace(true);
	}
	
	@Test
	public void copyProjectsIntoWorkspace_directory_false() {
		wizardPage.setRootDirectory(new File(RESOURCES_DIR, "directory").getAbsolutePath());
		wizardPage.copyProjectsIntoWorkspace(false);
	}
	
	@Test(expected=EclipseLayerException.class)
	public void copyProjectsIntoWorkspace_zipFile_true() {
		wizardPage.setArchiveFile(new File(RESOURCES_DIR, "zip/projectImport.zip").getAbsolutePath());
		wizardPage.copyProjectsIntoWorkspace(false);
	}
	
	@Test(expected=EclipseLayerException.class)
	public void copyProjectsIntoWorkspace_zipFile_false() {
		wizardPage.setArchiveFile(new File(RESOURCES_DIR, "zip/projectImport.zip").getAbsolutePath());
		wizardPage.copyProjectsIntoWorkspace(false);
	}
	
	@Test
	public void selectAllProjects() {
		wizardPage.setRootDirectory(new File(RESOURCES_DIR, "directory").getAbsolutePath());
		wizardPage.deselectAllProjects();
		wizardPage.selectAllProjects();
		
		List<ImportProject> projects = wizardPage.getProjects();
		assertProjects(projects, true, true, true);
	}
	
	@Test
	public void deselectAllProjects() {
		wizardPage.setRootDirectory(new File(RESOURCES_DIR, "directory").getAbsolutePath());
		wizardPage.deselectAllProjects();
		
		List<ImportProject> projects = wizardPage.getProjects();
		assertProjects(projects, false, false, false);
	}
	
	@Test
	public void selectProjects() {
		wizardPage.setRootDirectory(new File(RESOURCES_DIR, "directory").getAbsolutePath());
		wizardPage.selectProjects(PROJECT_B, PROJECT_C);
		
		List<ImportProject> projects = wizardPage.getProjects();
		assertProjects(projects, false, true, true);
	}
	
	@Test
	public void selectProjects_none() {
		wizardPage.setRootDirectory(new File(RESOURCES_DIR, "directory").getAbsolutePath());
		wizardPage.selectProjects();
		
		List<ImportProject> projects = wizardPage.getProjects();
		assertProjects(projects, false, false, false);
	}
	
	@Test
	public void getProjects_none(){
		List<ImportProject> projects = wizardPage.getProjects();

		assertTrue(projects.isEmpty());
	}
	
	@Override
	protected void tearDown(){
		wizard.cancel();
		super.tearDown();
	}
	
	@SuppressWarnings("unchecked")
	private void assertProjects(List<ImportProject> projects, boolean checkedProjectA, boolean checkedProjectB, boolean checkedProjectC) {
		assertThat(projects.size(), is(3));
		assertThat(projects, hasItems(
				new ImportProjectMatcher(checkedProjectA, PROJECT_A), 
				new ImportProjectMatcher(checkedProjectB, PROJECT_B), 
				new ImportProjectMatcher(checkedProjectC, PROJECT_C)));
	}
	
	class ImportProjectMatcher extends TypeSafeMatcher<ImportProject> {

		private boolean expectedChecked;
		
		private String expectedName;
		
		public ImportProjectMatcher(boolean expectedChecked, String expectedName) {
			this.expectedChecked = expectedChecked;
			this.expectedName = expectedName;
		}
		
		@Override
		public void describeTo(Description description) {
			description.appendText("ImportProject[" + expectedChecked + ", " + expectedName + "]");
		}

		@Override
		public boolean matchesSafely(ImportProject item) {
			return item.isChecked == expectedChecked && expectedName.equals(item.name);
		}
	}
}
