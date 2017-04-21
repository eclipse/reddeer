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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class contains database configuration used by Database requirement
 * that is configurable from outside via Requirement feature. 
 * @author Jiri Peterka
 *
 */
@XmlRootElement(name="database-requirement", namespace="http://www.jboss.org/NS/db-schema")
public class DatabaseConfiguration {

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

	/**
	 * Gets database Name.
	 * @return database name
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * Sets database name.
	 * @param databaseName database name
	 */
	@XmlElement(namespace = "http://www.jboss.org/NS/db-schema")
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * Gets database port number.
	 * @return database port number
	 */
	public String getPortNumber() {
		return portNumber;
	}

	/**
	 * Sets database port number.
	 * @param portNumber given port number
	 */
	@XmlElement(namespace="http://www.jboss.org/NS/db-schema")
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	/**
	 * Gets database driver vendor.
	 * @return database driver vendor
	 */
    public String getDriverVendor() {
		return driverVendor;
	}

    /**
     * Sets database driver vendor.
     *
     * @param driverVendor the new driver vendor
     */
    @XmlElement(namespace = "http://www.jboss.org/NS/db-schema")
	public void setDriverVendor(String driverVendor) {
		this.driverVendor = driverVendor;
	}
	
	/**
	 * Gets database driver profile name.
	 * @return database driver profile name
	 */
    public String getDriverName() {
		return driverName;
	}

    /**
     * Sets database driver profile name.
     * @param driverName database driver profile name
     */
    @XmlElement(namespace="http://www.jboss.org/NS/db-schema")
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * Get database driverType.
	 *
	 * @return database driver type
	 */
	public String getDriverType() {
		return driverType;
	}

	/**
	 * Sets database driver type.
	 * @param driverType database driver type
	 */
	@XmlElement(namespace = "http://www.jboss.org/NS/db-schema")
	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}

	/**
	 * Gets database driver profile version.
	 * @return database driver profile version 
	 */
	public String getDriverTypeVersion() {
		return driverTypeVersion;
	}

	/**
	 * Sets database driver profile version.
	 *
	 * @param driverVersion the new driver type version
	 */
	@XmlElement(namespace="http://www.jboss.org/NS/db-schema")
	public void setDriverTypeVersion(String driverVersion) {
		this.driverTypeVersion = driverVersion;
	}

	/**
	 * Gets database profile name .
	 *
	 * @return database profile name
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * Sets database profile name.
	 * @param profileName database profile name
	 */
	@XmlElement(namespace = "http://www.jboss.org/NS/db-schema")
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * Return driver type.
	 *
	 * @return driver type
	 */
	public String getType() {
		return driverType;
	}

	/**
	 * Set driver type.
	 * @param type driver type
	 */
	@XmlElement(namespace = "http://www.jboss.org/NS/db-schema")
	public void setType(String type) {
		this.driverType = type;
	}

	/**
	 * Return driver type version.
	 * @return driver type version
	 */
	public String getVersion() {
		return driverTypeVersion;
	}


	/**
	 * Set driver type version.
	 * @param version driver type version 
	 */
	@XmlElement(namespace = "http://www.jboss.org/NS/db-schema")
	public void setVersion(String version) {
		this.driverTypeVersion = version;
	}

	/**
	 * Returns JDBC driver path.
	 * @return file path
	 */
	public String getDriverPath() {
		return driverPath;
	}

	/**
	 * Sets driver file path .
	 *
	 * @param driverPath drive filepath
	 */
	@XmlElement(namespace = "http://www.jboss.org/NS/db-schema")
	public void setDriverPath(String driverPath) {
		this.driverPath = driverPath;
	}

	/**
	 * Gets database driver class.
	 * @return database driver class
	 */
	public String getDriverClass() {
		return driverClass;
	}


	/**
	 * Sets database Driver Class.
	 * @param driverClass database driver class
	 */
	@XmlElement(namespace = "http://www.jboss.org/NS/db-schema")
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}


	/**
	 * Database JDBC string.
	 * @return Database JDBC string
	 */
	public String getJdbcString() {
		return jdbcString;
	}

	/**
	 * Set database JDBC string.
	 * @param jdbc JDBC string
	 */
	@XmlElement(namespace = "http://www.jboss.org/NS/db-schema")
	public void setJdbcString(String jdbc) {
		this.jdbcString = jdbc;
	}

	/**
	 * Gets database username.
	 * @return database username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets database username.
	 * @param username database username
	 */
	@XmlElement(namespace = "http://www.jboss.org/NS/db-schema")
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get database password.
	 * @return database password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set database password.
	 * @param password database password
	 */
	@XmlElement(namespace = "http://www.jboss.org/NS/db-schema")
	public void setPassword(String password) {
		this.password = password;
	}
}
