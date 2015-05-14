package org.jboss.reddeer.snippet.test;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.db.DatabaseConfiguration;
import org.jboss.reddeer.requirements.db.DatabaseRequirement;
import org.jboss.reddeer.requirements.db.DatabaseRequirement.Database;
import org.junit.Test;
import org.junit.runner.RunWith;

@Database(name = "dbtest")
@RunWith(RedDeerSuite.class)
public class DatabaseTest {

	@InjectRequirement
	DatabaseRequirement dbRequirement;

	@Test
	public void testMyH2Database() {
		DatabaseConfiguration cfg = dbRequirement.getConfiguration();
		String jdbc = cfg.getJdbcString();
		// Here goes further logic
	}
}