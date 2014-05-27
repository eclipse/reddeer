package org.jboss.reddeer.requirements.db;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class contains database configuration used by Database requirement
 * that is configurable from outside via Requirement feature 
 * @author Jiri Peterka
 *
 */
@XmlRootElement(name="database-requirement", namespace="http://www.jboss.org/NS/db-schema")
public class DatabaseConfiguration {

	private String driverName;
    private String driverType;
    private String driverVersion; 
	private String driverPath;
	private String driverClass;
	private String profileName;
	private String jdbc;	
	private String username;
	private String password;	
	
	/**
	 * Gets database driver profile name
	 * @return database driver profile name
	 */
    public String getDriverName() {
		return driverName;
	}

    /**
     * Sets database driver profile name
     * @param driverName database driver profile name
     */
    @XmlElement(namespace="http://www.jboss.org/NS/db-schema")
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * Get database driverType
	 * @return database driver type
	 */
	public String getDriverType() {
		return driverType;
	}

	/**
	 * Sets database driver type
	 * @param driverType database driver type
	 */
	@XmlElement(namespace="http://www.jboss.org/NS/db-schema")
	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}

	/**
	 * Gets database driver profile version
	 * @return database driver profile version 
	 */
	public String getDriverVersion() {
		return driverVersion;
	}

	/**
	 * Sets database driver profile version
	 * @param database driver profile version
	 */
	@XmlElement(namespace="http://www.jboss.org/NS/db-schema")
	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}

	/**
	 *Gets database profile name 
	 * @return database profile name
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * Sets database profile name
	 * @param profileName database profile name
	 */
	@XmlElement(namespace="http://www.jboss.org/NS/db-schema")
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * Return driver type
	 * @return driver type
	 */
	public String getType() {
		return driverType;
	}

	/**
	 * Set driver type 
	 * @param type driver type
	 */
	@XmlElement(namespace="http://www.jboss.org/NS/db-schema")
	public void setType(String type) {
		this.driverType = type;
	}

	/**
	 * Return driver type version
	 * @return driver type version
	 */
	public String getVersion() {
		return driverVersion;
	}

	
	/**
	 * Set driver type version
	 * @param version driver type version 
	 */
	@XmlElement(namespace="http://www.jboss.org/NS/db-schema")
	public void setVersion(String version) {
		this.driverVersion = version;
	}

	/**
	 * Returns JDBC driver path
	 * @return file path
	 */
	public String getDriverPath() {
		return driverPath;
	}

	/**
	 * Sets driver file path 
	 * @param driverPath drive filepath
	 */
	public void setDriverPath(String driverPath) {
		this.driverPath = driverPath;
	}

	/**
	 * Gets database driver class
	 * @return database driver class
	 */
	public String getDriverClass() {
		return driverClass;
	}

	
	/**
	 * Sets database Driver Class
	 * @param driverClass database driver class
	 */
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	
	/**
	 * Database JDBC string
	 * @return Database JDBC string
	 */
	public String getJdbc() {
		return jdbc;
	}
	
	/**
	 * Set database JDBC string
	 * @param jdbc JDBC string
	 */
	@XmlElement(namespace="http://www.jboss.org/NS/db-schema")
	public void setJdbc(String jdbc) {
		this.jdbc = jdbc;
	}
	
	/**
	 * Gets database username
	 * @return database username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets database username
	 * @param username database username
	 */
	@XmlElement(namespace="http://www.jboss.org/NS/db-schema")
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Get database password
	 * @return database password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Set database password
	 * @param password database password
	 */
	@XmlElement(namespace="http://www.jboss.org/NS/db-schema")
	public void setPassword(String password) {
		this.password = password;
	}
}
