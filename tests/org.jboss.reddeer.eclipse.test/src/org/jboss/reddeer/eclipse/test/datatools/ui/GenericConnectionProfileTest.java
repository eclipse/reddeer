package org.jboss.reddeer.eclipse.test.datatools.ui;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.jboss.reddeer.eclipse.datatools.ui.DatabaseProfile;
import org.jboss.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.jboss.reddeer.eclipse.datatools.ui.DriverTemplate;
import org.jboss.reddeer.eclipse.datatools.ui.preference.DriverDefinitionPreferencePage;
import org.jboss.reddeer.eclipse.datatools.ui.view.DataSourceExplorer;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.ConnectionProfileWizard;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.DriverDefinitionPage;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.DriverDefinitionWizard;
import org.jboss.reddeer.eclipse.jdt.ui.WorkbenchPreferenceDialog;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class GenericConnectionProfileTest {

	private String fileName = "h2-1.4.178.jar";
	
	@Test
	public void genericConnectionProfileTest() {
		final String DRIVER_NAME = "Test H2 Driver";
		File drvFile = new File("target" + File.separator + fileName);
		
		DriverTemplate dt = new DriverTemplate("Generic JDBC Driver", "1.0");
		
		DriverDefinition dd = new DriverDefinition();
		dd.setDriverClass("org.h2.Driver");
		dd.setDriverLibrary(drvFile.getAbsolutePath());
		dd.setDriverName(DRIVER_NAME);
		dd.setDriverTemplate(dt);

	
		// DriverDefinition
		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		dialog.open();
		
		DriverDefinitionPreferencePage preferencePage = new DriverDefinitionPreferencePage();
		dialog.select(preferencePage);
		
		DriverDefinitionWizard wizard = preferencePage.addDriverDefinition();
		DriverDefinitionPage page = new DriverDefinitionPage();
		page.selectDriverTemplate("Generic JDBC Driver", "1.0");
		page.setName(DRIVER_NAME);
		page.addDriverLibrary(drvFile.getAbsolutePath());
		page.setDriverClass("org.h2.Driver");
		wizard.finish();
		dialog.ok();

		
		String profile = "myDBProfile";
		DatabaseProfile dbProfile = new DatabaseProfile();
		dbProfile.setDatabase("sakila");
		dbProfile.setDatabase(profile);
		dbProfile.setDriverDefinition(dd);
		dbProfile.setHostname("jdbc:h2:tcp://localhost/sakila");	// URL 
		dbProfile.setName("myDBProfile");
		dbProfile.setPassword("");
		dbProfile.setPort("8082");
		dbProfile.setUsername("sa");
		dbProfile.setVendor("Generic JDBC");
		
		// Create connection profile
		ConnectionProfileWizard w = new ConnectionProfileWizard();		
		w.open();
		w.createDatabaseProfile(dbProfile);

		List<String> dbSources = new DataSourceExplorer().getDatabaseConnections();
		assertTrue("Profile '" + profile + "' isn't available", dbSources.contains(profile));
	}
}