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

	public ConnectionProfileSQLServerPage() {
		super();
	}

	public String getDatabase() {
		return new LabeledText(LABEL_DATABASE).getText();
	}

	@Override
	public void setDatabase(String database) {
		new LabeledText(LABEL_DATABASE).setText(database);
	}

	public String getHostname() {
		return new LabeledText(LABEL_HOST).getText();
	}

	@Override
	public void setHostname(String driver) {
		new LabeledText(LABEL_HOST).setText(driver);
	}

	public String getUrl() {
		return new LabeledText(LABEL_URL).getText();
	}

	public void setUrl(String url) {
		new LabeledText(LABEL_URL).setText(url);
	}

	public String getUsername() {
		return new LabeledText(LABEL_USER_NAME).getText();
	}

	@Override
	public void setUsername(String userName) {
		new LabeledText(LABEL_USER_NAME).setText(userName);
	}

	public String getPassword() {
		return new LabeledText(LABEL_PASSWORD).getText();
	}

	@Override
	public void setPassword(String password) {
		new LabeledText(LABEL_PASSWORD).setText(password);
		new CheckBox(LABEL_SAVE_PASSWORD).click();
	}

	public String getPort() {
		return new LabeledText(LABEL_PORT_NUMBER).getText();
	}

	@Override
	public void setPort(String port) {
		new LabeledText(LABEL_PORT_NUMBER).setText(port);
	}

}
