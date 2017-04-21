/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.internal.runner.statement;

import org.eclipse.reddeer.junit.internal.requirement.Requirements;
import org.junit.runners.model.Statement;

/**
 * Fulfills the requirements and calls {@link #evaluate()} on the provided statement. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class FulfillRequirementsStatement extends Statement {
	
	private Statement statement;
	
	private Requirements requirements;
	
	/**
	 * Instantiates a new fulfill requirements statement.
	 *
	 * @param requirements the requirements
	 * @param statement the statement
	 */
	public FulfillRequirementsStatement(Requirements requirements, Statement statement) {
		this.statement = statement;
		this.requirements = requirements;
	}
	
	/* (non-Javadoc)
	 * @see org.junit.runners.model.Statement#evaluate()
	 */
	@Override
	public void evaluate() throws Throwable {
		requirements.fulfill();
		statement.evaluate();
	}
}