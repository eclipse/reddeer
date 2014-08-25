package org.jboss.reddeer.eclipse.test.jdt.ui.packageexplorer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.ProjectItem;
import org.jboss.reddeer.eclipse.ui.ide.NewFileCreationWizardDialog;
import org.jboss.reddeer.eclipse.ui.ide.NewFileCreationWizardPage;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.handler.WorkbenchHandler;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class ProjectItemTest {

	private static final String PROJECT_NAME = "ProjectItemTestProject";
	private static final String PROJECT_ITEM_TEXT = "src";
	private static final String DEFAULT_PACKAGE_TEXT = "(default package)";
	private PackageExplorer packageExplorer;
	private ProjectItem projectItem;
		
	@Before
	public void setUp() {
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = dialog.getFirstPage(); 
		page1.setProjectName(ProjectItemTest.PROJECT_NAME);
		dialog.finish();
		packageExplorer = new PackageExplorer();
		packageExplorer.open();
		projectItem = packageExplorer.getProject(ProjectItemTest.PROJECT_NAME)
			              .getProjectItem(ProjectItemTest.PROJECT_ITEM_TEXT);
	}

	@Test
	public void select() {
		packageExplorer.activate();
		projectItem.select();
		assertTrue("Project item " + ProjectItemTest.PROJECT_ITEM_TEXT + " is not selected" , projectItem.isSelected());
	}
	
	@Test
	public void delete() {
		packageExplorer.activate();
		projectItem.delete();
		assertFalse("Project " + ProjectItemTest.PROJECT_NAME + " contains project item " + ProjectItemTest.PROJECT_ITEM_TEXT +
				" but it should be deleted.",
			packageExplorer.getProject(ProjectItemTest.PROJECT_NAME).containsItem(ProjectItemTest.PROJECT_ITEM_TEXT));
	}
	
	@Test
	public void asyncDelete() throws Exception {
		packageExplorer.activate();
		projectItem.select();
		// Create new text file test.txt
		new NewFileCreationWizard().createFile("text.txt");
		// Edit the file outside the Eclipse IDE
		String rootPath = ResourcesPlugin.getWorkspace().getRoot().getLocationURI().getPath();
		File file = new File(rootPath + "/" + PROJECT_NAME + "/src/text.txt");
		FileWriter out = new FileWriter(file);
		out.write("Hello World");
		out.flush();
		out.close();
		// Delete the file
		projectItem.delete();
		assertFalse("Project " + ProjectItemTest.PROJECT_NAME + " contains project item " + ProjectItemTest.PROJECT_ITEM_TEXT +
				" but it should be deleted.",
			packageExplorer.getProject(ProjectItemTest.PROJECT_NAME).containsItem(ProjectItemTest.PROJECT_ITEM_TEXT));
	}
	
	@Test
	public void open() {
		packageExplorer.activate();
		packageExplorer.getProject(ProjectItemTest.PROJECT_NAME)
            .getProjectItem(ProjectItemTest.PROJECT_ITEM_TEXT)
            .select();
		
		final String javaClassName = "TestClass";
		createJavaClass(javaClassName);
		
		WorkbenchHandler.getInstance().closeAllEditors();
		final String javaClassFileName = javaClassName + ".java";
		packageExplorer.getProject(ProjectItemTest.PROJECT_NAME)
          .getProjectItem(ProjectItemTest.PROJECT_ITEM_TEXT,ProjectItemTest.DEFAULT_PACKAGE_TEXT,javaClassFileName)
          .open();
		assertTrue("Active Editor has to have title " + javaClassFileName,
			new DefaultEditor().getTitle().equals(javaClassFileName));
	}
	
	@Test
	public void getChild() {
		packageExplorer.activate();
		packageExplorer.getProject(ProjectItemTest.PROJECT_NAME)
            .getProjectItem(ProjectItemTest.PROJECT_ITEM_TEXT)
            .select();
		
		final String javaClassName = "TestClass";
		createJavaClass(javaClassName);
		
		ProjectItem piDefaultPackage = packageExplorer.getProject(ProjectItemTest.PROJECT_NAME)
          .getProjectItem(ProjectItemTest.PROJECT_ITEM_TEXT)
          .getChild(ProjectItemTest.DEFAULT_PACKAGE_TEXT);
          
		assertTrue("Found Project Item has to have text " + ProjectItemTest.DEFAULT_PACKAGE_TEXT
				  + " but is " + piDefaultPackage.getText(),
				piDefaultPackage.getText().equals(ProjectItemTest.DEFAULT_PACKAGE_TEXT));
		
		/* select default package */
		piDefaultPackage.select();
		assertTrue("Project item " + ProjectItemTest.DEFAULT_PACKAGE_TEXT + " is not selected" , piDefaultPackage.isSelected());
	}
	
	@Test
	public void getChildren() {
		packageExplorer.activate();
		packageExplorer.getProject(ProjectItemTest.PROJECT_NAME)
				.getProjectItem(ProjectItemTest.PROJECT_ITEM_TEXT).select();

		final String[] javaClassNames = new String[] { "TestClass01",
				"TestClass02" };

		createJavaClass(javaClassNames[0]);
		createJavaClass(javaClassNames[1]);

		List<ProjectItem> srcChildrens = projectItem.getChildren();

		/* src folder has to contain default package*/
		assertTrue(PROJECT_ITEM_TEXT + " has to contain only 1 Project Item"
				+ " but there are " + srcChildrens.size() + " Project Items",
				srcChildrens.size() == 1);

		ProjectItem piDefaultPackage = srcChildrens.get(0);
		assertTrue("Found Project Item has to have text "
				+ ProjectItemTest.DEFAULT_PACKAGE_TEXT + " but is "
				+ piDefaultPackage.getText(), piDefaultPackage.getText()
				.equals(DEFAULT_PACKAGE_TEXT));

		/* default package has to contain created classes */
		List<ProjectItem> defaultPackageChildrens = piDefaultPackage
				.getChildren();
		assertTrue(PROJECT_ITEM_TEXT + " has to contain "
				+ javaClassNames.length + " Project Items" + " but there are "
				+ defaultPackageChildrens.size() + " Project Items",
				defaultPackageChildrens.size() == javaClassNames.length);

		for (int i = 0; i < javaClassNames.length; i++) {
			ProjectItem pi = defaultPackageChildrens.get(i);
			String javaClassFileName = javaClassNames[i] + ".java";
			assertTrue("Found Project Item has to have text "
					+ javaClassFileName + " but is " + pi.getText(), pi
					.getText().equals(javaClassFileName));
		}
	}
	
	private void createJavaClass(final String javaClassName) {
		NewJavaClassWizardDialog newJavaClassDialog = new NewJavaClassWizardDialog();
		newJavaClassDialog.open();
		
		NewJavaClassWizardPage wizardPage = newJavaClassDialog.getFirstPage();
		wizardPage.setName(javaClassName);
		newJavaClassDialog.finish();
		
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}
	
	@After
	public void tearDown() {
		packageExplorer.close();
		packageExplorer.open();
		DeleteUtils.forceProjectDeletion(packageExplorer.getProject(ProjectItemTest.PROJECT_NAME),true);
	}
	
	private class NewFileCreationWizard extends NewFileCreationWizardPage {
		
		public void createFile(String fileName) {
			NewFileCreationWizardDialog wizard = new NewFileCreationWizardDialog();
			wizard.open();
			setFileName(fileName);
			wizard.finish();
		}
	}
}
