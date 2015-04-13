package org.jboss.reddeer.eclipse.test.jst.j2ee.ui.project.facet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet.EarProjectFirstPage;
import org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet.EarProjectInstallPage;
import org.jboss.reddeer.eclipse.jst.j2ee.ui.project.facet.EarProjectWizard;
import org.jboss.reddeer.eclipse.jst.j2ee.wizard.NewJ2EEComponentSelectionPage;
import org.jboss.reddeer.eclipse.jst.servlet.ui.WebProjectFirstPage;
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
public class EARProjectWizardTest {
	
	private String projectName = "EARProject";
	private ProjectExplorer explorer;
	private boolean modulesAreCreated;
	private boolean webModuleIsCreated;
	
	@Before
	public void openProjectExplorer() {
		explorer = new ProjectExplorer();
		explorer.open();
		modulesAreCreated = false;
		webModuleIsCreated = false;
	}
	
	@After
	public void deleteProject() {
		DeleteUtils.forceProjectDeletion(explorer.getProject(projectName), true);
		if (modulesAreCreated) {
			DeleteUtils.forceProjectDeletion(explorer.getProject(projectName + "Client"), true);
			DeleteUtils.forceProjectDeletion(explorer.getProject(projectName + "Connector"), true);
			DeleteUtils.forceProjectDeletion(explorer.getProject(projectName + "EJB"), true);
			DeleteUtils.forceProjectDeletion(explorer.getProject(projectName + "Web"), true);
		}
		if (webModuleIsCreated) {
			DeleteUtils.forceProjectDeletion(explorer.getProject("CreatedWebProjectModule"), true);
		}
	}
	
	@Test
	public void createProject(){
		EarProjectWizard ear = new EarProjectWizard();
		ear.open();
		EarProjectFirstPage fp = new EarProjectFirstPage();
		fp.setProjectName(projectName);
		ear.next();
		EarProjectInstallPage ip = new EarProjectInstallPage();
		assertFalse(ip.isGenerateApplicationXML());
		ear.finish();
		assertTrue(explorer.containsProject(projectName));
	}
	
	@Test
	public void createProjectWithDefaultModules(){
		EarProjectWizard ear = new EarProjectWizard();
		ear.open();
		EarProjectFirstPage fp = new EarProjectFirstPage();
		fp.setProjectName(projectName);
		ear.next();
		EarProjectInstallPage ip = new EarProjectInstallPage();
		assertFalse(ip.isGenerateApplicationXML());
		NewJ2EEComponentSelectionPage jee=ip.newModule();
		String ejb = jee.getEJBModuleName();
		String conn = jee.getConnectorModuleName();
		String web = jee.getWebModuleName();
		String appClient = jee.getApplicationClientModuleName();
		jee.finish();
		ear.finish();
		assertTrue(explorer.containsProject(projectName));
		assertTrue(explorer.containsProject(ejb));
		assertTrue(explorer.containsProject(conn));
		assertTrue(explorer.containsProject(web));
		assertTrue(explorer.containsProject(appClient));
		modulesAreCreated = true;
	}
	
	@Test
	public void createProjectWithWebModule(){
		EarProjectWizard ear = new EarProjectWizard();
		ear.open();
		EarProjectFirstPage fp = new EarProjectFirstPage();
		fp.setProjectName(projectName);
		ear.next();
		EarProjectInstallPage ip = new EarProjectInstallPage();
		assertFalse(ip.isGenerateApplicationXML());
		NewJ2EEComponentSelectionPage jee=ip.newModule();
		jee.toggleCreateDefaultModules(false);
		WebProjectWizard ww = jee.addWeb();
		WebProjectFirstPage wp = new WebProjectFirstPage();
		wp.setProjectName("CreatedWebProjectModule");
		ww.finish();
		ear.finish();
		assertTrue(explorer.containsProject(projectName));
		webModuleIsCreated = true;
		assertTrue(explorer.containsProject("CreatedWebProjectModule"));
	}

}
