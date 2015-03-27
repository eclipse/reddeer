package org.jboss.reddeer.eclipse.test.ui.views.properties;

import java.util.List;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.ui.views.properties.PropertiesView;
import org.jboss.reddeer.eclipse.ui.views.properties.TabbedPropertyList;
import org.jboss.reddeer.gef.editor.GEFEditor;
import org.jboss.reddeer.gef.impl.editpart.LabeledEditPart;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.uiforms.impl.section.DefaultSection;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Andrej Podhradsky
 *
 */
@RunWith(RedDeerSuite.class)
public class TabbedPropertiesTest {

	@BeforeClass
	public static void createGeneralProjectAndXsdFile() {
		new ProjectExplorer().open();
		new PropertiesView().open();

		new ShellMenu("File", "New", "Project...").select();
		new DefaultShell("New Project");
		new DefaultTreeItem("General", "Project").select();
		new PushButton("Next >").click();
		new LabeledText("Project name:").setText("test");
		new PushButton("Finish").click();
		new WaitWhile(new ShellWithTextIsActive("New Project"));
		new WaitWhile(new JobIsRunning());

		new ProjectExplorer().getProject("test").select();

		new ShellMenu("File", "New", "Other...").select();
		new DefaultShell("New");
		new DefaultTreeItem("XML", "XML Schema File").select();
		new PushButton("Next >").click();
		new PushButton("Finish").click();
		new WaitWhile(new ShellWithTextIsActive("New"));
		new WaitWhile(new JobIsRunning());
	}

	@AfterClass
	public static void deleteProject() {
		new ProjectExplorer().getProject("test").delete(true);
	}

	@Before
	public void openXsdFile() {
		new ProjectExplorer().getProject("test").getProjectItem("NewXMLSchema.xsd").open();
	}

	@Test
	public void selectTabTest() {
		new GEFEditor("NewXMLSchema.xsd");
		new LabeledEditPart("Elements").select();

		new PropertiesView().open();
		new TabbedPropertyList().selectTab("Advanced");
		new TabbedPropertyList().selectTab("General");
		new TabbedPropertyList().selectTab("Documentation");
		new TabbedPropertyList().selectTab("Extensions");

		new DefaultSection("Extensions");
		new DefaultSection("Extension Details");
	}

	@Test
	public void getTabTest() {
		new GEFEditor("NewXMLSchema.xsd");
		new LabeledEditPart("Elements").select();

		new PropertiesView().open();
		List<String> tabs = new TabbedPropertyList().getTabs();
		Assert.assertEquals(4, tabs.size());
		Assert.assertEquals("General", tabs.get(0));
		Assert.assertEquals("Documentation", tabs.get(1));
		Assert.assertEquals("Extensions", tabs.get(2));
		Assert.assertEquals("Advanced", tabs.get(3));

	}
}
