/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.requirements.test.db;

import static org.junit.Assert.assertEquals;

import org.eclipse.reddeer.junit.requirement.inject.InjectRequirement;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.db.DatabaseConfiguration;
import org.eclipse.reddeer.requirements.db.DatabaseRequirement;
import org.eclipse.reddeer.requirements.db.DatabaseRequirement.Database;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Database configuration test {@link DatabaseConfiguration}.
 * @author Jiri Peterka
 *
 */
@Database
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
		assertEquals(null, conf.getPassword());
	}
	
}
