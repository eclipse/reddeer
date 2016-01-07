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
 * A wizard page for creating new Oracle database profile.
 * 
 * 
 * @author apodhrad
 * 
 */
public class ConnectionProfileOraclePage extends ConnectionProfileDatabasePage {

	public static final String PROFILE_NAME = "Oracle";

	public static final String LABEL_DATABASE = "SID:";
	public static final String LABEL_HOST = "Host:";
	public static final String LABEL_PORT_NUMBER = "Port number:";
	public static final String LABEL_URL = "Connection URL:";
	public static final String LABEL_USER_NAME = "User name:";
	public static final String LABEL_PASSWORD = "Password:";
	public static final String LABEL_SAVE_PASSWORD = "Save password";

	/**
	 * Instantiates a new connection profile oracle page.
	 */
	public ConnectionProfileOraclePage() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.datatools.ui.wizard.ConnectionProfileDatabasePage#setHostname(java.lang.String)
	 */
	@Override
	public void setHostname(String hostname) {
		new LabeledText(LABEL_HOST).setText(hostname);
	}

	/**
	 * Gets the hostname.
	 *
	 * @return the hostname
	 */
	public String getHostname() {
		return new LabeledText(LABEL_HOST).getText();
	}

	/**
	 * Tests the database connection. Not yet implemented!
	 */
	public void testConnection() {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.datatools.ui.wizard.ConnectionProfileDatabasePage#setDatabase(java.lang.String)
	 */
	@Override
	public void setDatabase(String database) {
		new LabeledText(LABEL_DATABASE).setText(database);
	}

	/**
	 * Gets the database.
	 *
	 * @return the database
	 */
	public void getDatabase() {
		new LabeledText(LABEL_DATABASE).getText();
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
