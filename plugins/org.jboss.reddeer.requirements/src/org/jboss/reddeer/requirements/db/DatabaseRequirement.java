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
package org.jboss.reddeer.requirements.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.requirements.db.DatabaseRequirement.Database;

/**
 * Requirement for work with specific database
 * @author Jiri Peterka
 *
 */
public class DatabaseRequirement implements Requirement<Database>, CustomConfiguration<DatabaseConfiguration> {
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface Database {
		
		/**
		 * Name.
		 *
		 * @return the string
		 */
		String name();
	}
	
	private Logger log = Logger.getLogger(DatabaseRequirement.class);
	private Database database;
	private DatabaseConfiguration configuration;

	/**
	 * Gets database configuration class.
	 *
	 * @return the configuration class
	 */
	@Override
	public Class<DatabaseConfiguration> getConfigurationClass() {
		return DatabaseConfiguration.class;
	}

	/**
	 * Sets database configuration.
	 *
	 * @param config the new configuration
	 */
	@Override
	public void setConfiguration(DatabaseConfiguration config) {
		this.configuration = config;
		
	}

	/**
	 * Returns true when database requirement can be fullfilled 
	 * Always returns true for Database Requirement.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean canFulfill() {
		log.trace("Database requirement canFullfill performed");
		log.debug("JDBC:" + configuration.getJdbcString());
		log.debug("Requirement name:" + database.name());
		return true;
	}

	/**
	 * Fulfills database requirement
	 * It doesn't nothing for database requirement intentionally.
	 */
	@Override
	public void fulfill() {
		log.trace("Database requirement performed");
	}

	/**
	 * Sets database declaration.
	 *
	 * @param declaration the new declaration
	 */
	@Override
	public void setDeclaration(Database declaration) {
		this.database = declaration;
		
	}
	
	/**
	 * Gets database configuration for further use in tests.
	 *
	 * @return database configuration
	 */
	public DatabaseConfiguration getConfiguration() {
		return configuration;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.requirement.Requirement#cleanUp()
	 */
	@Override
	public void cleanUp() {

	}
}
