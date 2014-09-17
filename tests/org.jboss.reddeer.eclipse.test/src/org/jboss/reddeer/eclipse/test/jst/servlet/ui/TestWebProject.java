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
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.Test;
import org.junit.runner.RunWith;

@OpenPerspective(JavaEEPerspective.class)
@RunWith(RedDeerSuite.class)
public class TestWebProject {
	
	@Test
	public void createWebProject(){
		WebProjectWizard ww = new WebProjectWizard();
		ww.open();
		WebProjectFirstPage fp = new WebProjectFirstPage();
		fp.setProjectName("web project");
		ww.finish();
		PackageExplorer pe = new PackageExplorer();
		pe.open();
		assertTrue(pe.containsProject("web project"));
	}
	
	@Test
	public void createWebProject1(){
		WebProjectWizard ww = new WebProjectWizard();
		ww.open();
		WebProjectFirstPage fp = new WebProjectFirstPage();
		fp.setProjectName("web project1");
		assertEquals("web project1", fp.getProjectName());
		ww.next();
		WebProjectSecondPage sp = (WebProjectSecondPage)ww.getCurrentWizardPage();
		sp.addSourceFoldersOnBuildPath("source");
		sp.removeSourceFoldersOnBuildPath("src");
		assertEquals(1,sp.getSourceFolders().size());
		assertEquals("source", sp.getSourceFolders().get(0));
		ww.next();
		WebProjectThirdPage tp = (WebProjectThirdPage)ww.getCurrentWizardPage();
		tp.setGenerateWebXmlDeploymentDescriptor(true);
		ww.finish();
		PackageExplorer pe = new PackageExplorer();
		pe.open();
		assertTrue(pe.containsProject("web project1"));
		assertTrue(pe.getProject("web project1").containsItem("source"));
		assertFalse(pe.getProject("web project1").containsItem("src"));
		assertTrue(pe.getProject("web project1").containsItem("WebContent","WEB-INF","web.xml"));
	}

}
