package org.jboss.reddeer.eclipse.test.ui.views;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.core.resources.ProjectItem;
import org.jboss.reddeer.eclipse.ui.views.navigator.ResourceNavigator;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.handler.WorkbenchHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Testing ResourceNavigator
 * 
 * @author rrabara
 */
@RunWith(RedDeerSuite.class)
public class ResourceNavigatorTest {
	
	private static final String PROJECT_NAME = "ResourceNavigatorTestProject";
	private static final String SOURCE_FOLDER = "src";
	private static final String PACKAGE_NAME = "org.jboss.reddeer.eclipse.jdt.navigator";
	private static final String CLASS_NAME = "Test";
	
	private ResourceNavigator navigator;
	
	@Before
	public void setUp() {
		navigator = new ResourceNavigator();
	}
	
	@Test
	public void open() {
		
		navigator.open();
		String activeViewTitle = WorkbenchHandler.getInstance().getActiveViewTitle();
		assertTrue("Active view have to be Navigator but is"
				+activeViewTitle, activeViewTitle.equals("Navigator"));
	}
	
	/**
	 * This test case is verifying that package
	 * is represented as hierarchy of directories.
	 */
	@Test
	public void navigation() {
		createProject(PROJECT_NAME);
		createClass(PROJECT_NAME, PACKAGE_NAME, CLASS_NAME);
		
		Project project = navigator.getProject(PROJECT_NAME);
		ProjectItem item = project.getProjectItem(SOURCE_FOLDER);
		for(String name : PACKAGE_NAME.split("[.]")) {
			item = item.getChild(name);
		}
		item = item.getChild(CLASS_NAME+".java");
	}

	/**
	 * Create class in specified project and package
	 * 
	 * @param projectName
	 * @param packageName
	 * @param className
	 */
	private void createClass(String projectName, String packageName, String className) {
		NewJavaClassWizardDialog classDialog = new NewJavaClassWizardDialog();
		classDialog.open();
		
		NewJavaClassWizardPage page = classDialog.getFirstPage();
		page.setName(className);
		page.setPackage(packageName);
		page.setSourceFolder(projectName+"/"+SOURCE_FOLDER);
		
		classDialog.finish();
	}

	/**
	 * Create project with given name
	 * @param projectName
	 */
	private void createProject(String projectName) {
		NewJavaProjectWizardDialog dialog = new NewJavaProjectWizardDialog();
		dialog.open();
		dialog.getFirstPage().setProjectName(projectName);
		dialog.finish();
	}
	
	@After
	public void tearDown() {
		if (navigator != null){
			if(navigator.containsProject(PROJECT_NAME)) {
				DeleteUtils.forceProjectDeletion(navigator.getProject(PROJECT_NAME),true);
			}
		}
	}
}
