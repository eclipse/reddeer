package org.jboss.reddeer.snippet.preference;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.dialogs.ExplorerItemPropertyDialog;
import org.jboss.reddeer.eclipse.ui.dialogs.PropertyDialog;
import org.jboss.reddeer.eclipse.wst.common.project.facet.ui.RuntimesPropertyPage;
import org.junit.Test;

public class RuntimesPropertyPageTest {

	private static final String PROJECT = "test-project";
	
	private static final String SERVER = "test-server";

	@Test
	public void selectRuntime() {
		// Create instances of dialog and page
		PropertyDialog dialog = new ExplorerItemPropertyDialog(getProject());
		RuntimesPropertyPage propertyPage = new RuntimesPropertyPage();

		// open the dialog using context menu of the project
		dialog.open();
		// select page in the left menu
		dialog.select(propertyPage);
		
		// work with the page
		propertyPage.selectRuntime(SERVER);
		assertTrue(propertyPage.getSelectedRuntimes().contains(SERVER));
		
		// press OK button to store the values and close the dialog
		dialog.ok();
	}

	private Project getProject() {
		PackageExplorer packageExplorer = new PackageExplorer();
		packageExplorer.open();
		return packageExplorer.getProject(PROJECT);
	}
}
