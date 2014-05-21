package org.jboss.reddeer.eclipse.datatools.ui.wizard;

import java.util.HashMap;
import java.util.Map;

import org.jboss.reddeer.eclipse.datatools.ui.DatabaseProfile;
import org.jboss.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.jboss.reddeer.eclipse.datatools.ui.FlatFileProfile;
import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;

/**
 * A wizard dialog for creating a connection profile.
 * 
 * Connections Profiles -> Connection Profile
 * 
 * @author apodhrad
 * 
 */
public class ConnectionProfileWizard extends NewWizardDialog {

	protected Map<String, ConnectionProfileDatabasePage> wizardMap;

	public ConnectionProfileWizard() {
		super("Connection Profiles", "Connection Profile");

		wizardMap = new HashMap<String, ConnectionProfileDatabasePage>();
		wizardMap.put("Oracle", new ConnectionProfileOraclePage());
		wizardMap.put("SQL Server", new ConnectionProfileSQLServerPage());
	}

	/**
	 * Create a given database profile.
	 * 
	 * @param dbProfile
	 */
	public void createDatabaseProfile(DatabaseProfile dbProfile) {
		ConnectionProfileSelectPage selectPage = new ConnectionProfileSelectPage();
		selectPage.setConnectionProfile(dbProfile.getVendor());
		selectPage.setName(dbProfile.getName());

		next();

		ConnectionProfileDatabasePage dbPage = wizardMap.get(dbProfile.getVendor());
		DriverDefinition drvDef = dbProfile.getDriverDefinition();
		dbPage.setDriver(drvDef.getDriverName());
		dbPage.setDatabase(dbProfile.getDatabase());
		dbPage.setHostname(dbProfile.getHostname());
		dbPage.setPort(dbProfile.getPort());
		dbPage.setUsername(dbProfile.getUsername());
		dbPage.setPassword(dbProfile.getPassword());

		finish();
	}

	/**
	 * Create a given flat file profile.
	 * 
	 * @param flatProfile
	 *            Flat file profile
	 */
	public void createFlatFileProfile(FlatFileProfile flatProfile) {
		ConnectionProfileSelectPage selectPage = new ConnectionProfileSelectPage();
		selectPage.setConnectionProfile("Flat File Data Source");
		selectPage.setName(flatProfile.getName());

		next();

		ConnectionProfileFlatFilePage flatPage = new ConnectionProfileFlatFilePage();
		flatPage.setHomeFolder(flatProfile.getFolder());
		flatPage.setCharset(flatProfile.getCharset());
		flatPage.setStyle(flatProfile.getStyle());

		finish();
	}
}
