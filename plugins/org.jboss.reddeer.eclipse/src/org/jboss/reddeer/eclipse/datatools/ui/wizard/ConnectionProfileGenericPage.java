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
package org.jboss.reddeer.eclipse.datatools.ui.wizard;

import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * A wizard page for creating new Generic JDBC (H2,..) profile.
 * 
 * 
 * @author Jiri Peterka
 * 
 */
public class ConnectionProfileGenericPage extends ConnectionProfileDatabasePage {

	public static final String PROFILE_NAME = "Generic JDBC";

	public static final String LABEL_DATABASE = "Database:";
	public static final String LABEL_HOST = "URL:";
	public static final String LABEL_PORT_NUMBER = "Port number:";
	public static final String LABEL_URL = "URL:";
	public static final String LABEL_USER_NAME = "User name:";
	public static final String LABEL_PASSWORD = "Password:";
	public static final String LABEL_SAVE_PASSWORD = "Save password";

	/**
	 * Initialize connection profile for generic type.
	 */
	public ConnectionProfileGenericPage() {
		super();
	}

	@Override
	public void setHostname(String hostname) {
		new LabeledText(LABEL_HOST).setText(hostname);
	}

	/**
	 * Return hostname (URL).
	 *
	 * @return hostname/url
	 */
	public String getHostname() {
		return new LabeledText(LABEL_HOST).getText();
	}

	/**
	 * Tests connection (unsupported).
	 */
	public void testConnection() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDatabase(String database) {
		new LabeledText(LABEL_DATABASE).setText(database);
	}

	/**
	 * Returns database for this profile.
	 *
	 * @return the database
	 */
	public String getDatabase() {
		return new LabeledText(LABEL_DATABASE).getText();
	}

	/**
	 * Not used for this profile.
	 *
	 * @return empty string
	 */
	public String getUrl() {
		return "";
	}

	/**
	 * return username for generic profile.
	 *
	 * @return username
	 */
	public String getUsername() {
		return new LabeledText(LABEL_USER_NAME).getText();
	}

	@Override
	public void setUsername(String userName) {
		new LabeledText(LABEL_USER_NAME).setText(userName);
	}

	/**
	 * Returns password for generic profile.
	 *
	 * @return password
	 */
	public String getPassword() {
		return new LabeledText(LABEL_PASSWORD).getText();
	}

	/**
	 * Sets password for generic profile.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		new LabeledText(LABEL_PASSWORD).setText(password);
		new CheckBox(LABEL_SAVE_PASSWORD).click();
	}

	/**
	 * Returns port - empty string for this profile.
	 *
	 * @return empty string for this profile
	 */
	public String getPort() {
		return "";
	}

	@Override
	public void setPort(String port) {
		// does nothing for this profile
		return;
	}

}
