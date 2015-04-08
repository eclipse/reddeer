package org.jboss.reddeer.eclipse.test.jdt.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.jdt.ui.AbstractExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.core.resources.ProjectItem;
import org.jboss.reddeer.eclipse.ui.ide.NewFileCreationWizardDialog;
import org.jboss.reddeer.eclipse.ui.ide.NewFileCreationWizardPage;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.core.handler.WorkbenchPartHandler;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public abstract class AbstractExplorerItemTest {

	public static final String PROJECT_NAME = "ProjectItemTestProject";
	public static final String PROJECT_ITEM_TEXT = "src";
	public static final String DEFAULT_PACKAGE_TEXT = "(default package)";
	protected static final String JAVA_CLASS_NAME = "TestClass";
	protected static final String JAVA_CLASS_FILE_NAME = "TestClass.java";
	protected AbstractExplorer explorer;
	protected ProjectItem projectItem;
		
	public AbstractExplorerItemTest(AbstractExplorer explorer) {
		this.explorer=explorer;
	}
	
	@Before
	public void setUp() {
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		NewJavaProjectWizardPage page1 = dialog.getFirstPage(); 
		page1.setProjectName(PROJECT_NAME);
		dialog.finish();
		explorer.open();
		projectItem = explorer.getProject(PROJECT_NAME).getProjectItem(PROJECT_ITEM_TEXT);
	}

	@Test
	public void select() {
		explorer.activate();
		projectItem.select();
		projectItem.getTreeItem().collapse();
		assertTrue("Project item " + PROJECT_ITEM_TEXT + " is not selected" , projectItem.isSelected());
	}
	
	protected void open(String... projectItemPath) {
		explorer.activate();
		explorer.getProject(PROJECT_NAME).getProjectItem(PROJECT_ITEM_TEXT).select();
		
		createJavaClass(JAVA_CLASS_NAME);
		
		WorkbenchPartHandler.getInstance().closeAllEditors();
		explorer.getProject(PROJECT_NAME).getProjectItem(projectItemPath).open();
		assertTrue("Active Editor has to have title " + JAVA_CLASS_FILE_NAME,
			new DefaultEditor().getTitle().equals(JAVA_CLASS_FILE_NAME));
	}
	
	protected void selectNonVisibleItem(String... projectItemPath) {
		explorer.activate();
		Project project = explorer.getProject(PROJECT_NAME);
		project.getProjectItem(PROJECT_ITEM_TEXT).select();
		
		createJavaClass(JAVA_CLASS_NAME);
		WorkbenchPartHandler.getInstance().closeAllEditors();
		explorer.activate();
		
		ProjectItem projectItem = project.getProjectItem(projectItemPath);
		project.collapse();
		project.select();
		projectItem.select();
		
		assertTrue("Project item is not selected.", projectItem.isSelected());
	}
	
	@Test
	public void delete() {
		explorer.activate();
		projectItem.delete();
		assertFalse("Project " + PROJECT_NAME + " contains project item " + PROJECT_ITEM_TEXT +
				" but it should be deleted.",
			explorer.getProject(PROJECT_NAME).containsItem(PROJECT_ITEM_TEXT));
	}
	
	@Test
	public void asyncDelete() throws Exception {
		explorer.activate();
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
		explorer.activate();
		projectItem.delete();
		assertFalse("Project " + PROJECT_NAME + " contains project item " + PROJECT_ITEM_TEXT +
				" but it should be deleted.",
			explorer.getProject(PROJECT_NAME).containsItem(PROJECT_ITEM_TEXT));
	}
	
	protected void getChild(String... pathToItem) {
		explorer.activate();
		explorer.getProject(PROJECT_NAME).getProjectItem(PROJECT_ITEM_TEXT).select();
		
		createJavaClass(JAVA_CLASS_NAME);
		
		try {
			explorer.getProject(PROJECT_NAME).getProjectItem(pathToItem)
					.getChild(JAVA_CLASS_FILE_NAME);
		} catch (EclipseLayerException ex) {
			fail("Child item " + JAVA_CLASS_FILE_NAME + " has not been found.");
		}
	}
	
	protected void getChildren(String... pathToItem) {
		explorer.activate();
		explorer.getProject(PROJECT_NAME).getProjectItem(PROJECT_ITEM_TEXT).select();

		final String[] javaClassNames = new String[] { "TestClass01",
				"TestClass02" };

		createJavaClass(javaClassNames[0]);
		createJavaClass(javaClassNames[1]);

		List<ProjectItem> srcChildren = explorer.getProject(PROJECT_NAME).getProjectItem(pathToItem)
				.getChildren();
		
		assertTrue("There have to be 2 items presented, but number of items is " + srcChildren.size() + ".",
				srcChildren.size() == 2);
	}
	
	protected void createJavaClass(final String javaClassName) {
		NewJavaClassWizardDialog newJavaClassDialog = new NewJavaClassWizardDialog();
		newJavaClassDialog.open();
		
		NewJavaClassWizardPage wizardPage = newJavaClassDialog.getFirstPage();
		wizardPage.setName(javaClassName);
		newJavaClassDialog.finish();
		
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}
	
	@After
	public void tearDown() {
		explorer.close();
		explorer.open();
		DeleteUtils.forceProjectDeletion(explorer.getProject(PROJECT_NAME),true);
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
