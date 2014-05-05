package org.jboss.reddeer.eclipse.datatools.ui.wizard;

import java.util.HashMap;
import java.util.Map;

import org.jboss.reddeer.eclipse.datatools.ui.DatabaseProfile;
import org.jboss.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.jboss.reddeer.eclipse.datatools.ui.FlatFileProfile;
import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;

/**
 * A wizard dialog for creating a connection profile.
 * 
 * Connections Profiles -> Connection Profile
 * 
 * @author apodhrad
 * 
 */
public class ConnectionProfileWizard extends NewWizardDialog {

	private String connectionProfile;
	protected Map<String, WizardPage> wizardMap;

	public ConnectionProfileWizard() {
		super("Connection Profiles", "Connection Profile");
		wizardMap = new HashMap<String, WizardPage>();
		wizardMap.put("Oracle", new ConnectionProfileOraclePage(this, 2));
		wizardMap.put("SQL Server", new ConnectionProfileSQLServerPage(this, 2));
		wizardMap.put("Flat File Data Source", new ConnectionProfileFlatFilePage(this, 2));
	}

	@Override
	/**
	 * Return a wizard page for selecting a connection profile.
	 */
	public ConnectionProfileSelectPage getFirstPage() {
		return new ConnectionProfileSelectPage();
	}

	/**
	 * Returns a wizard page for a selected connection profile. Note that If the
	 * connection profile wasn't selected or it is not supported it returns
	 * null.
	 * 
	 * @return a wizard page for a selected connection profile
	 */
	public WizardPage getSecondPage() {
		return wizardMap.get(connectionProfile);
	}

	protected String getConnectionProfile() {
		return connectionProfile;
	}

	protected void setConnectionProfile(String connectionProfile) {
		this.connectionProfile = connectionProfile;
	}

	/**
	 * Create a given database profile.
	 * 
	 * @param dbProfile
	 */
	public void createDatabaseProfile(DatabaseProfile dbProfile) {
		ConnectionProfileSelectPage selectPage = getFirstPage();
		selectPage.setConnectionProfile(dbProfile.getVendor());
		selectPage.setName(dbProfile.getName());

		next();

		ConnectionProfileDatabasePage dbPage = (ConnectionProfileDatabasePage) getSecondPage();
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
	 * @param dbProfile
	 */
	public void createFlatFileProfile(FlatFileProfile flatProfile) {
		ConnectionProfileSelectPage selectPage = getFirstPage();
		selectPage.setConnectionProfile("Flat File Data Source");
		selectPage.setName(flatProfile.getName());

		next();

		ConnectionProfileFlatFilePage flatPage = (ConnectionProfileFlatFilePage) getSecondPage();
		flatPage.setHomeFolder(flatProfile.getFolder());
		flatPage.setCharset(flatProfile.getCharset());
		flatPage.setStyle(flatProfile.getStyle());
		
		finish();
	}
}
