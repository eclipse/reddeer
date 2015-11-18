package org.jboss.reddeer.eclipse.datatools.ui.wizard;

import java.util.HashMap;
import java.util.Map;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.eclipse.datatools.ui.DatabaseProfile;
import org.jboss.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.jboss.reddeer.eclipse.datatools.ui.FlatFileProfile;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

/**
 * A wizard dialog for creating a connection profile.
 * 
 * Connections Profiles -> Connection Profile
 * 
 * @author apodhrad, jpeterka
 * 
 */
public class ConnectionProfileWizard extends NewWizardDialog {

	protected Map<String, ConnectionProfileDatabasePage> wizardMap;

	/**
	 * Instantiates a new connection profile wizard.
	 */
	public ConnectionProfileWizard() {
		super("Connection Profiles", "Connection Profile");

		wizardMap = new HashMap<String, ConnectionProfileDatabasePage>();
		wizardMap.put("Oracle", new ConnectionProfileOraclePage());
		wizardMap.put("SQL Server", new ConnectionProfileSQLServerPage());
		wizardMap.put("Generic JDBC", new ConnectionProfileGenericPage());
	}

	/**
	 * Create a given database profile.
	 *
	 * @param dbProfile the db profile
	 */
	public void createDatabaseProfile(DatabaseProfile dbProfile) {
		createDatabaseProfile(dbProfile, false);
	}

	/**
	 * Create a given database profile.
	 * @param dbProfile given database profile
	 * @param test set to true if ping needs to be done
	 */
	public void createDatabaseProfile(DatabaseProfile dbProfile, boolean test) {
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
		
		if (test) {
			String success = "Success";
			new PushButton("Test Connection").click();
			new WaitUntil(new ShellWithTextIsActive(success), TimePeriod.NORMAL, false);
			String text = new DefaultShell().getText();
			new OkButton().click();
			if (!text.equals(success)) {
				throw new EclipseLayerException("Connection ping failed!");
			}
		}

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
