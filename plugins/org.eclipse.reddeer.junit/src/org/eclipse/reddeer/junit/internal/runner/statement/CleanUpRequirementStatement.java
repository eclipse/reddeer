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
package org.eclipse.reddeer.junit.internal.runner.statement;

import org.eclipse.reddeer.junit.internal.requirement.Requirements;
import org.junit.runners.model.Statement;

/**
 * Calls {@link #evaluate()} on the provided statement and clean up the requirements. 
 * 
 * @author Rastislav Wagner
 *
 */
public class CleanUpRequirementStatement extends Statement{
	
	private Statement statement;
	
	private Requirements requirements;
	
	/**
	 * Instantiates a new clean up requirement statement.
	 *
	 * @param requirements the requirements
	 * @param statement the statement
	 */
	public CleanUpRequirementStatement(Requirements requirements, Statement statement) {
		this.statement = statement;
		this.requirements = requirements;
	}

	/* (non-Javadoc)
	 * @see org.junit.runners.model.Statement#evaluate()
	 */
	@Override
	public void evaluate() throws Throwable {
		statement.evaluate();
		requirements.cleanUp();
	}

}
