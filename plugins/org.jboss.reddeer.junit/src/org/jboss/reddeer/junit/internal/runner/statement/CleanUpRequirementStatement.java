package org.jboss.reddeer.junit.internal.runner.statement;

import org.jboss.reddeer.junit.internal.requirement.Requirements;
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
	
	public CleanUpRequirementStatement(Requirements requirements, Statement statement) {
		this.statement = statement;
		this.requirements = requirements;
	}

	@Override
	public void evaluate() throws Throwable {
		statement.evaluate();
		requirements.cleanUp();
	}

}
