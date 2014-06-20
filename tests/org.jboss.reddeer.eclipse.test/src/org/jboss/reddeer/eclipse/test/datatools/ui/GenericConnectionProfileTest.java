package org.jboss.reddeer.eclipse.test.datatools.ui;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

import org.jboss.reddeer.eclipse.datatools.ui.DatabaseProfile;
import org.jboss.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.jboss.reddeer.eclipse.datatools.ui.DriverTemplate;
import org.jboss.reddeer.eclipse.datatools.ui.preference.DriverDefinitionPreferencePage;
import org.jboss.reddeer.eclipse.datatools.ui.view.DataSourceExplorer;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.ConnectionProfileWizard;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.DriverDefinitionPage;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.DriverDefinitionWizard;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class GenericConnectionProfileTest {

	private String fileName = "h2-1.4.178.jar";
	
	@Test
	public void genericConnectionProfileTest() {
		final String DRIVER_NAME = "Test H2 Driver";
		try {
			downloadDriver();
		} catch (IOException e) {
			fail("Cannot download jdbc driver necessary for test: " + e.getMessage());
		}
		File drvFile = new File("target" + File.separator + fileName);
		
		DriverTemplate dt = new DriverTemplate("Generic JDBC Driver", "1.0");
		
		DriverDefinition dd = new DriverDefinition();
		dd.setDriverClass("org.h2.Driver");
		dd.setDriverLibrary(drvFile.getAbsolutePath());
		dd.setDriverName(DRIVER_NAME);
		dd.setDriverTemplate(dt);

	
		// DriverDefinition
		DriverDefinitionPreferencePage preferencePage = new DriverDefinitionPreferencePage();
		preferencePage.open();
		DriverDefinitionWizard wizard = preferencePage.addDriverDefinition();
		DriverDefinitionPage page = new DriverDefinitionPage();
		page.selectDriverTemplate("Generic JDBC Driver", "1.0");
		page.setName(DRIVER_NAME);
		page.addDriverLibrary(drvFile.getAbsolutePath());
		page.setDriverClass("org.h2.Driver");
		wizard.finish();
		preferencePage.ok();

		
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
	
	private void downloadDriver() throws IOException {
		URL website = new URL("http://repo2.maven.org/maven2/com/h2database/h2/1.4.178/" + fileName);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream("target" + File.separator + fileName);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}

}