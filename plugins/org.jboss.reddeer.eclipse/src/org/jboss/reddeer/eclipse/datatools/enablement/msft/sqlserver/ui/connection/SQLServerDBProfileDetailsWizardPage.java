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
package org.jboss.reddeer.eclipse.datatools.enablement.msft.sqlserver.ui.connection;

import org.jboss.reddeer.eclipse.datatools.connectivity.ui.wizards.ExtensibleProfileDetailsWizardPage;

/**
 * A wizard page for creating new SQL Server database profile.
 * 
 * @author apodhrad, mlabuda@redhat.com
 * 
 */
public class SQLServerDBProfileDetailsWizardPage extends ExtensibleProfileDetailsWizardPage {

	public static final String PROFILE_NAME = "SQL Server";

	private static final String LABEL_DATABASE = "Database:";
	private static final String LABEL_HOSTNAME = "Host:";
	private static final String LABEL_URL = "Connection URL:";
	private static final String LABEL_USER_NAME = "User name:";
	private static final String LABEL_PORT_NUMBER = "Port number:";
	private static final String LABEL_PASSWORD = "Password:";
	private static final String LABEL_SAVE_PASSWORD = "Save password";

	/**
	 * Instantiates a new connection profile sql server page.
	 */
	public SQLServerDBProfileDetailsWizardPage() {
		super();
	}
	
	@Override
	protected String getHostnameLabel() {
		return LABEL_HOSTNAME;
	}
	
	@Override
	protected String getDatabaseLabel() {
		return LABEL_DATABASE;
	}
	
	@Override
	protected String getURLLabel() {
		return LABEL_URL;
	}
	
	@Override
	protected String getUsernameLabel() {
		return LABEL_USER_NAME;
	}
	
	@Override
	protected String getPortLabel() {
		return LABEL_PORT_NUMBER;
	}
	
	@Override
	protected String getPasswordLabel() {
		return LABEL_PASSWORD;
	}
	
	@Override
	protected String getSavePasswordLabel() {
		return LABEL_SAVE_PASSWORD;
	}
}
