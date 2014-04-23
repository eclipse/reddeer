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
	private String jdbc;
	private String username;
	private String password;
	
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
	@XmlElement(namespace="http://www.jboss.org/NS/db-schema")
	public void setPassword(String password) {
		this.password = password;
	}
}
