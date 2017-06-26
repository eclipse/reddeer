/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.datatools.connectivity.db.generic.ui;

import org.eclipse.reddeer.eclipse.datatools.connectivity.ui.wizards.ExtensibleProfileDetailsWizardPage;
import org.eclipse.reddeer.jface.wizard.WizardDialog;

/**
 * A wizard page for creating new Generic JDBC (H2,..) profile.
 * 
 * 
 * @author Jiri Peterka, mlabuda@redhat.com
 * 
 */
public class GenericJDBCDBProfileDetailsWizardPage extends ExtensibleProfileDetailsWizardPage {

	public static final String PROFILE_NAME = "Generic JDBC";

	private static final String LABEL_DATABASE = "Database:";
	private static final String LABEL_HOSTNAME = "URL:";
	private static final String LABEL_URL = "URL:";
	private static final String LABEL_USER_NAME = "User name:";
	private static final String LABEL_PASSWORD = "Password:";
	private static final String LABEL_SAVE_PASSWORD = "Save password";

	/**
	 * Initialize connection profile for generic type.
	 */
	public GenericJDBCDBProfileDetailsWizardPage(WizardDialog dialog) {
		super(dialog);
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
		return null;
	}

	/**
	 * Generic JDBC DB Profile does not have port
	 * @param port do not use this method
	 */
	@Override
	public void setPort(String port) {
		// do nothing, there are no ports for generic profile
	}
	
	/**
	 * Generic JDBC DB Profile does not have port.
	 * 
	 * @return null
	 */
	@Override
	public String getPort() {
		return null;
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
