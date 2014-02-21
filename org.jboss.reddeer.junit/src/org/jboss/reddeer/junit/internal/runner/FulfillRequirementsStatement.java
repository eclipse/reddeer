package org.jboss.reddeer.junit.internal.runner;

import org.jboss.reddeer.junit.internal.requirement.Requirements;
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
	 * Constructor
	 * @param requirements
	 * @param statement
	 */
	public FulfillRequirementsStatement(Requirements requirements, Statement statement) {
		this.statement = statement;
		this.requirements = requirements;
	}
	
	/**
	 * Fulfills the requirements and calls {@link #evaluate()} on the provided statement. 
	 */
	@Override
	public void evaluate() throws Throwable {
		requirements.fulfill();
		statement.evaluate();
	}
}