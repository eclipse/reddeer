package org.jboss.reddeer.eclipse.datatools.ui.wizard;

import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * A wizard page for creating new SQL Server database profile.
 * 
 * @author apodhrad
 * 
 */
public class ConnectionProfileSQLServerPage extends ConnectionProfileDatabasePage {

	public static final String PROFILE_NAME = "SQL Server";

	public static final String LABEL_DATABASE = "Database:";
	public static final String LABEL_HOST = "Host:";
	public static final String LABEL_PORT_NUMBER = "Port number:";
	public static final String LABEL_URL = "Connection URL:";
	public static final String LABEL_USER_NAME = "User name:";
	public static final String LABEL_PASSWORD = "Password:";
	public static final String LABEL_SAVE_PASSWORD = "Save password";

	/**
	 * Instantiates a new connection profile sql server page.
	 */
	public ConnectionProfileSQLServerPage() {
		super();
	}

	/**
	 * Gets the database.
	 *
	 * @return the database
	 */
	public String getDatabase() {
		return new LabeledText(LABEL_DATABASE).getText();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.datatools.ui.wizard.ConnectionProfileDatabasePage#setDatabase(java.lang.String)
	 */
	@Override
	public void setDatabase(String database) {
		new LabeledText(LABEL_DATABASE).setText(database);
	}

	/**
	 * Gets the hostname.
	 *
	 * @return the hostname
	 */
	public String getHostname() {
		return new LabeledText(LABEL_HOST).getText();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.datatools.ui.wizard.ConnectionProfileDatabasePage#setHostname(java.lang.String)
	 */
	@Override
	public void setHostname(String driver) {
		new LabeledText(LABEL_HOST).setText(driver);
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return new LabeledText(LABEL_URL).getText();
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		new LabeledText(LABEL_URL).setText(url);
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return new LabeledText(LABEL_USER_NAME).getText();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.datatools.ui.wizard.ConnectionProfileDatabasePage#setUsername(java.lang.String)
	 */
	@Override
	public void setUsername(String userName) {
		new LabeledText(LABEL_USER_NAME).setText(userName);
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return new LabeledText(LABEL_PASSWORD).getText();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.datatools.ui.wizard.ConnectionProfileDatabasePage#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password) {
		new LabeledText(LABEL_PASSWORD).setText(password);
		new CheckBox(LABEL_SAVE_PASSWORD).click();
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public String getPort() {
		return new LabeledText(LABEL_PORT_NUMBER).getText();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.datatools.ui.wizard.ConnectionProfileDatabasePage#setPort(java.lang.String)
	 */
	@Override
	public void setPort(String port) {
		new LabeledText(LABEL_PORT_NUMBER).setText(port);
	}

}
