package org.jboss.reddeer.requirements.test.db;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.requirements.db.DatabaseConfiguration;
import org.jboss.reddeer.requirements.db.DatabaseRequirement;
import org.jboss.reddeer.requirements.db.DatabaseRequirement.Database;
import org.junit.Test;

/**
 * Database configuration test {@link DatabaseConfiguration}.
 * @author Jiri Peterka
 *
 */
@Database(name = "mydbtest")
public class DatabaseRequirementTest {
	/**
	 * Injected database requirement. 
	 */
	@InjectRequirement 
	private DatabaseRequirement dbRequirement;

	/**
	 * Check if database requirement configuration works.
	 */
	@Test
	public final void testDatabaseConfiguration() {
		DatabaseConfiguration conf = dbRequirement.getConfiguration();
		assertEquals(conf.getDriverName(), "My H2 Driver");
		assertEquals(conf.getDriverVersion(), "1.0");
		assertEquals(conf.getDriverPath(), "/opt/sakila-db/h2-1.3.161.jar");
		assertEquals(conf.getDriverClass(), "org.h2.Driver");
		assertEquals(conf.getProfileName(), "dbProfile");
		assertEquals(conf.getJdbcString(), "jdbc:h2:db://localhost/sakila");
		assertEquals(conf.getUsername(), "sa");
		assertEquals(conf.getPassword(), "");
	}
	
}
