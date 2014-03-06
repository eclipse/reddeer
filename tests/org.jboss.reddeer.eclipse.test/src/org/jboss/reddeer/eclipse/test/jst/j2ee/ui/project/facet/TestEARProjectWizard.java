package org.jboss.reddeer.eclipse.test.jst.j2ee.ui.project.facet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet.EarProjectFirstPage;
import org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet.EarProjectInstallPage;
import org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet.EarProjectWizard;
import org.jboss.reddeer.eclipse.jst.j2ee.wizard.NewJ2EEComponentSelectionPage;
import org.jboss.reddeer.eclipse.jst.servlet.ui.WebProjectFirstPage;
import org.jboss.reddeer.eclipse.jst.servlet.ui.WebProjectWizard;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaEEPerspective;
import org.junit.Test;

@OpenPerspective(JavaEEPerspective.class)
public class TestEARProjectWizard extends RedDeerTest{
	
	@Test
	public void createProject(){
		EarProjectWizard ear = new EarProjectWizard();
		ear.open();
		EarProjectFirstPage fp = (EarProjectFirstPage)ear.getWizardPage(0);
		fp.setProjectName("EARProject");
		EarProjectInstallPage ip = (EarProjectInstallPage)ear.getWizardPage(1);
		assertFalse(ip.isGenerateApplicationXML());
		ear.finish();
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		assertTrue(pe.containsProject("EARProject"));
	}
	
	@Test
	public void createProjectWithDefaultModules(){
		EarProjectWizard ear = new EarProjectWizard();
		ear.open();
		EarProjectFirstPage fp = (EarProjectFirstPage)ear.getWizardPage(0);
		fp.setProjectName("EARProject");
		EarProjectInstallPage ip = (EarProjectInstallPage)ear.getWizardPage(1);
		assertFalse(ip.isGenerateApplicationXML());
		NewJ2EEComponentSelectionPage jee=ip.newModule();
		String ejb = jee.getEJBModuleName();
		String conn = jee.getConnectorModuleName();
		String web = jee.getWebModuleName();
		String appClient = jee.getApplicationClientModuleName();
		jee.finish();
		ear.finish();
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		assertTrue(pe.containsProject("EARProject"));
		assertTrue(pe.containsProject(ejb));
		assertTrue(pe.containsProject(conn));
		assertTrue(pe.containsProject(web));
		assertTrue(pe.containsProject(appClient));
	}
	
	@Test
	public void createProjectWithWebModule(){
		EarProjectWizard ear = new EarProjectWizard();
		ear.open();
		EarProjectFirstPage fp = (EarProjectFirstPage)ear.getWizardPage(0);
		fp.setProjectName("EARProject");
		EarProjectInstallPage ip = (EarProjectInstallPage)ear.getWizardPage(1);
		assertFalse(ip.isGenerateApplicationXML());
		NewJ2EEComponentSelectionPage jee=ip.newModule();
		jee.toggleCreateDefaultModules(false);
		WebProjectWizard ww = jee.addWeb();
		WebProjectFirstPage wp = (WebProjectFirstPage)ww.getWizardPage(0);
		wp.setProjectName("CreatedWebProjectModule");
		ww.finish();
		ear.finish();
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		assertTrue(pe.containsProject("EARProject"));
		assertTrue(pe.containsProject("CreatedWebProjectModule"));
	}

}
