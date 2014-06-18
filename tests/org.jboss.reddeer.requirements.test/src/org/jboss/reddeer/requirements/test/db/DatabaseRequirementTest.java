package org.jboss.reddeer.requirements.test.db;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.db.DatabaseConfiguration;
import org.jboss.reddeer.requirements.db.DatabaseRequirement;
import org.jboss.reddeer.requirements.db.DatabaseRequirement.Database;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Database configuration test {@link DatabaseConfiguration}.
 * @author Jiri Peterka
 *
 */
@Database(name = "mydbtest")
@RunWith(RedDeerSuite.class)
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
		assertEquals("My H2 Driver", conf.getDriverName());
		assertEquals("1.0", conf.getDriverTypeVersion());
		assertEquals("/opt/sakila-db/h2-1.3.161.jar", conf.getDriverPath());
		assertEquals("org.h2.Driver", conf.getDriverClass());
		assertEquals("Generic JDBC", conf.getDriverVendor());
		assertEquals("dbProfile", conf.getProfileName());
		assertEquals("jdbc:h2:db://localhost/sakila", conf.getJdbcString());
		assertEquals("sa", conf.getUsername());
		assertEquals("", conf.getPassword());
	}
	
}
