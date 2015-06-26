package org.jboss.reddeer.eclipse.test.wst.common.project.facet.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.test.wst.server.ui.view.ServersViewTestCase;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.jboss.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.eclipse.wst.common.project.facet.ui.FacetsPropertyPage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class FacetsPropertyPageTest {

	private static final String PROJECT = "server-project";
	private static final String FACET1 = "Java";
	private static final String FACET1_VERSION = "1.7";

	private FacetsPropertyPage propertyPage;

	@Before
	public void createProject() {
		ExternalProjectImportWizardDialog wizard = new ExternalProjectImportWizardDialog();
		wizard.open();

		WizardProjectsImportPage wizardPage = new WizardProjectsImportPage();
		wizardPage.setArchiveFile(ServersViewTestCase.ZIP_FILE
				.getAbsolutePath());
		wizardPage.selectProjects(PROJECT);

		wizard.finish();

		PackageExplorer packageExplorer = new PackageExplorer();
		packageExplorer.open();
		propertyPage = new FacetsPropertyPage(
				packageExplorer.getProject(PROJECT));
	}

	@After
	public void cleanup() {
		Shell shell = null;
		try {
			shell = new DefaultShell(propertyPage.getPageTitle());
			shell.close();
		} catch (SWTLayerException e) {
			// not found, no action needed
		}
		PackageExplorer explorer = new PackageExplorer();
		explorer.open();
		DeleteUtils.forceProjectDeletion(explorer.getProject(PROJECT), true);
	}

	@Test
	public void selectFacet() {
		propertyPage.open();
		propertyPage.selectFacet(FACET1);
		final String parentShellText = WidgetHandler.getInstance().getText(
				ShellHandler.getInstance().getParentShell(
						new DefaultShell(propertyPage.getPageTitle())
								.getSWTWidget()));
		new OkButton().click();
		new WaitWhile(new ShellWithTextIsAvailable(propertyPage.getPageTitle()));
		new WaitUntil(new ShellWithTextIsActive(parentShellText));

		propertyPage.open();
		assertThat(propertyPage.getSelectedFacets().get(0).getText(),
				is(FACET1));

		new OkButton().click();
	}

	@Test
	public void selectVersion() {
		propertyPage.open();
		propertyPage.selectFacet(FACET1);
		propertyPage.selectVersion(FACET1, FACET1_VERSION);
		final String parentShellText = WidgetHandler.getInstance().getText(
				ShellHandler.getInstance().getParentShell(
						new DefaultShell(propertyPage.getPageTitle())
								.getSWTWidget()));
		new OkButton().click();
		new WaitWhile(new ShellWithTextIsAvailable(propertyPage.getPageTitle()));
		new WaitUntil(new ShellWithTextIsActive(parentShellText));

		propertyPage.open();
		assertThat(propertyPage.getSelectedVersion(FACET1), is(FACET1_VERSION));

		new OkButton().click();
	}
}
