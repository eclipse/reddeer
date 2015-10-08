package org.jboss.reddeer.eclipse.test.wst.xml.ui.tabletree;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.test.Activator;
import org.jboss.reddeer.eclipse.test.ui.part.MultiPageEditorTest;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.jboss.reddeer.eclipse.wst.xml.ui.tabletree.XMLMultiPageEditor;
import org.jboss.reddeer.eclipse.wst.xml.ui.tabletree.XMLSourcePage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class XMLSourcePageTest {

public static final File ZIP_FILE = new File(Activator.getTestResourcesLocation(MultiPageEditorTest.class), "xml-project.zip");
	
	protected static final String PROJECT_NAME = "xml-project";
	
	protected static final String FILE_NAME = "file.xml";
	
	protected static final String NAMESPACES_FILE_NAME = "TomcatServerRequirement.xml";
	
	private static PackageExplorer explorer;
	
	@BeforeClass
	public static void importProject(){
		ExternalProjectImportWizardDialog wizard  = new ExternalProjectImportWizardDialog();
		wizard.open();

		WizardProjectsImportPage wizardPage = new WizardProjectsImportPage();
		wizardPage.setArchiveFile(ZIP_FILE.getAbsolutePath());
		wizardPage.selectProjects(PROJECT_NAME);

		wizard.finish();
		
		explorer = new PackageExplorer();
		explorer.open();
	}
	
	@AfterClass
	public static void cleanProject(){
		explorer.open();
		explorer.getProject(PROJECT_NAME).delete(true);
	}
	
	@Test
	public void evaluateXPath(){
		explorer.getProject(PROJECT_NAME).getChild(FILE_NAME).open();
		
		XMLMultiPageEditor editor = new XMLMultiPageEditor(FILE_NAME);
		XMLSourcePage page = editor.getSourcePage();
		
		String result = page.evaluateXPath("/a/b1/c/text()");
		assertThat(result, is("content"));
	}
	
	@Test
	public void evaluateXPath_withNamespace(){
		explorer.getProject(PROJECT_NAME).getChild(NAMESPACES_FILE_NAME).openWith("XML Editor");
		
		XMLMultiPageEditor editor = new XMLMultiPageEditor(NAMESPACES_FILE_NAME);
		XMLSourcePage page = editor.getSourcePage();
		
		String result = page.evaluateXPath("/:testrun/:requirements/server:server-requirement/@name");
		assertThat(result, is("Tomcat7"));
	}
}
