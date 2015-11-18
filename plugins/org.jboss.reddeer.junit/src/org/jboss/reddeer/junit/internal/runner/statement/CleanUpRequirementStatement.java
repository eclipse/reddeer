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
