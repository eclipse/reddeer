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
package org.eclipse.reddeer.requirements.db;

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class contains database configuration used by Database requirement that is
 * configurable from outside via Requirement feature.
 * 
 * @author Jiri Peterka
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatabaseConfiguration implements RequirementConfiguration {

	private String driverName;
	private String driverType;
	private String driverTypeVersion;
	private String driverVendor;
	private String driverPath;
	private String driverClass;
	private String profileName;
	private String jdbcString;
	private String databaseName;
	private String portNumber;
	private String username;
	private String password;

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverType() {
		return driverType;
	}

	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}

	public String getDriverTypeVersion() {
		return driverTypeVersion;
	}

	public void setDriverTypeVersion(String driverTypeVersion) {
		this.driverTypeVersion = driverTypeVersion;
	}

	public String getDriverVendor() {
		return driverVendor;
	}

	public void setDriverVendor(String driverVendor) {
		this.driverVendor = driverVendor;
	}

	public String getDriverPath() {
		return driverPath;
	}

	public void setDriverPath(String driverPath) {
		this.driverPath = driverPath;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getJdbcString() {
		return jdbcString;
	}

	public void setJdbcString(String jdbcString) {
		this.jdbcString = jdbcString;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getId() {
		return driverName + "-" + driverType + "-" + jdbcString + "-" + databaseName;
	}
}
