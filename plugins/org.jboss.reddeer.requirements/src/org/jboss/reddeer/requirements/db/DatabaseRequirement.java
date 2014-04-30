package org.jboss.reddeer.requirements.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.logging.Logger;
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
		String name();
	}
	
	private Logger log = Logger.getLogger(DatabaseRequirement.class);
	private Database database;
	private DatabaseConfiguration configuration;

	/**
	 * Gets database configuration class
	 */
	@Override
	public Class<DatabaseConfiguration> getConfigurationClass() {
		return DatabaseConfiguration.class;
	}

	/**
	 * Sets database configuration
	 */
	@Override
	public void setConfiguration(DatabaseConfiguration config) {
		this.configuration = config;
		
	}

	/**
	 * Returns true when database requirement can be fullfilled 
	 * Always returns true for Database Requirement
	 */
	@Override
	public boolean canFulfill() {
		log.trace("Database requirement canFullfill performed");
		log.debug("JDBC:" + configuration.getJdbc());
		log.debug("Requirement name:" + database.name());
		return true;
	}

	/**
	 * Fulfills database requirement
	 * It doesn't nothing for database requirement intentionally
	 */
	@Override
	public void fulfill() {
		log.trace("Database requirement performed");
	}

	/**
	 * Sets database declaration 
	 */
	@Override
	public void setDeclaration(Database declaration) {
		this.database = declaration;
		
	}
	
	/**
	 * Gets database configuration for further use in tests
	 * @return database configuration
	 */
	public DatabaseConfiguration getConfiguration() {
		return configuration;
	}
}
