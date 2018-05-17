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
package org.eclipse.reddeer.requirements.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.requirement.AbstractConfigurableRequirement;
import org.eclipse.reddeer.requirements.db.DatabaseRequirement.Database;

/**
 * Requirement for work with specific database
 * @author Jiri Peterka
 *
 */
public class DatabaseRequirement extends AbstractConfigurableRequirement<DatabaseConfiguration, Database> {
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface Database {
		
	}
	
	private Logger log = Logger.getLogger(DatabaseRequirement.class);

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
	 * Fulfills database requirement
	 * It doesn't nothing for database requirement intentionally.
	 */
	@Override
	public void fulfill() {
		log.trace("Database requirement performed");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.requirement.Requirement#cleanUp()
	 */
	@Override
	public void cleanUp() {

	}
}
