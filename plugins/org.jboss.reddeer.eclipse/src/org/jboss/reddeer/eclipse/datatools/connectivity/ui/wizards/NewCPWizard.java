/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.datatools.connectivity.ui.wizards;

import java.util.HashMap;
import java.util.Map;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.eclipse.datatools.connectivity.db.generic.ui.GenericJDBCDBProfileDetailsWizardPage;
import org.jboss.reddeer.eclipse.datatools.connectivity.oda.flatfile.ui.wizards.FolderSelectionWizardPage;
import org.jboss.reddeer.eclipse.datatools.enablement.msft.sqlserver.ui.connection.SQLServerDBProfileDetailsWizardPage;
import org.jboss.reddeer.eclipse.datatools.enablement.oracle.ui.OracleDBProfileDetailsWizardPage;
import org.jboss.reddeer.eclipse.datatools.ui.DatabaseProfile;
import org.jboss.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.jboss.reddeer.eclipse.datatools.ui.FlatFileProfile;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.topmenu.NewMenuWizard;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

/**
 * A wizard dialog for creating a connection profile.
 * 
 * Connections Profiles &gt; Connection Profile
 * 
 * @author apodhrad, jpeterka
 * 
 */
public class NewCPWizard extends NewMenuWizard {

	protected Map<String, ExtensibleProfileDetailsWizardPage> wizardMap;

	/**
	 * Instantiates a new connection profile wizard.
	 */
	public NewCPWizard() {
		super("New Connection Profile","Connection Profiles", "Connection Profile");

		wizardMap = new HashMap<String, ExtensibleProfileDetailsWizardPage>();
		wizardMap.put("Oracle", new OracleDBProfileDetailsWizardPage());
		wizardMap.put("SQL Server", new SQLServerDBProfileDetailsWizardPage());
		wizardMap.put("Generic JDBC", new GenericJDBCDBProfileDetailsWizardPage());
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
		CPWizardSelectionPage selectPage = new CPWizardSelectionPage();
		selectPage.setConnectionProfile(dbProfile.getVendor());
		selectPage.setName(dbProfile.getName());

		next();

		ExtensibleProfileDetailsWizardPage dbPage = wizardMap.get(dbProfile.getVendor());
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
			new WaitUntil(new ShellWithTextIsAvailable(success), TimePeriod.NORMAL, false);
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
		CPWizardSelectionPage selectPage = new CPWizardSelectionPage();
		selectPage.setConnectionProfile("Flat File Data Source");
		selectPage.setName(flatProfile.getName());

		next();

		FolderSelectionWizardPage flatPage = new FolderSelectionWizardPage();
		flatPage.setHomeFolder(flatProfile.getFolder());
		flatPage.setCharset(flatProfile.getCharset());
		flatPage.setStyle(flatProfile.getStyle());

		finish();
	}
}
