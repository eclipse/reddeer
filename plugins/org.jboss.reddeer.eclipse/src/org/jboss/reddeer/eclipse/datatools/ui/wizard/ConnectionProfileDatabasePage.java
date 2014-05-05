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

	/**
	 * A wizard page should not know on which page index it is displayed. The
	 * wizard page can also exist outside WizardDialog. Use no-argument
	 * constructor instead.
	 * 
	 * @param wizardDialog
	 * @param pageIndex
	 */
	@Deprecated
	protected ConnectionProfileDatabasePage(WizardDialog wizardDialog, int pageIndex) {
		super(wizardDialog, pageIndex);
	}

	protected ConnectionProfileDatabasePage() {
		super();
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
