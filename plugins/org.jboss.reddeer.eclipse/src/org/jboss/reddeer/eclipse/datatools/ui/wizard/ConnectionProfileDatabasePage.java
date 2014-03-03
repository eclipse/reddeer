package org.jboss.reddeer.eclipse.datatools.ui.wizard;

import org.jboss.reddeer.eclipse.jface.wizard.WizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;

/**
 * An abstract wizard page for database connection profile.
 * 
 * IMPORTANT: It is assumed that the appropriate driver definition has been
 * already created.
 * 
 * @author apodhrad
 * 
 */
public abstract class ConnectionProfileDatabasePage extends WizardPage {

	public static final String LABEL_DRIVER = "Drivers:";

	protected ConnectionProfileDatabasePage(WizardDialog wizardDialog, int pageIndex) {
		super(wizardDialog, pageIndex);
	}

	public String getDriver() {
		return new LabeledCombo(LABEL_DRIVER).getText();
	}

	public void setDriver(String driver) {
		new LabeledCombo(LABEL_DRIVER).setSelection(driver);
	}

	public abstract void setDatabase(String database);

	public abstract void setHostname(String hostname);

	public abstract void setPort(String port);

	public abstract void setUsername(String username);

	public abstract void setPassword(String password);
}
