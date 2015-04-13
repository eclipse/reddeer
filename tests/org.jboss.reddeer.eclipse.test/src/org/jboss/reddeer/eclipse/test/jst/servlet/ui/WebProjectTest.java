package org.jboss.reddeer.eclipse.test.jst.servlet.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jst.servlet.ui.WebProjectFirstPage;
import org.jboss.reddeer.eclipse.jst.servlet.ui.WebProjectSecondPage;
import org.jboss.reddeer.eclipse.jst.servlet.ui.WebProjectThirdPage;
import org.jboss.reddeer.eclipse.jst.servlet.ui.WebProjectWizard;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaEEPerspective;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@OpenPerspective(JavaEEPerspective.class)
@RunWith(RedDeerSuite.class)
public class WebProjectTest {
	
	private PackageExplorer packageExplorer;
	private String projectName = "web project";
	
	@Before
	public void openPackageExplorer() {
		packageExplorer = new PackageExplorer();
		packageExplorer.open();
	}
	
	@After
	public void deleteProject() {
		DeleteUtils.forceProjectDeletion(packageExplorer.getProject(projectName), true);
	}
	
	@Test
	public void createWebProject(){
		WebProjectWizard ww = new WebProjectWizard();
		ww.open();
		WebProjectFirstPage fp = new WebProjectFirstPage();
		fp.setProjectName(projectName);
		ww.finish();
		assertTrue(packageExplorer.containsProject(projectName));
	}
	
	@Test
	public void createWebProject1(){
		WebProjectWizard ww = new WebProjectWizard();
		ww.open();
		WebProjectFirstPage fp = new WebProjectFirstPage();
		fp.setProjectName(projectName);
		assertEquals(projectName, fp.getProjectName());
		ww.next();
		WebProjectSecondPage sp = new WebProjectSecondPage();
		sp.addSourceFoldersOnBuildPath("source");
		sp.removeSourceFoldersOnBuildPath("src");
		assertEquals(1,sp.getSourceFolders().size());
		assertEquals("source", sp.getSourceFolders().get(0));
		ww.next();
		WebProjectThirdPage tp = new WebProjectThirdPage();
		tp.setGenerateWebXmlDeploymentDescriptor(true);
		ww.finish();
		assertTrue(packageExplorer.containsProject(projectName));
		assertTrue(packageExplorer.getProject(projectName).containsItem("source"));
		assertFalse(packageExplorer.getProject(projectName).containsItem("src"));
		assertTrue(packageExplorer.getProject(projectName).containsItem("WebContent","WEB-INF","web.xml"));
	}

}
