package org.jboss.reddeer.junit.internal.runner;

import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.junit.BeforeClass;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * Fulfills the requirements before {@link BeforeClass} is called.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class RequirementsRunner extends BlockJUnit4ClassRunner {
	
	private Requirements requirements;

	public RequirementsRunner(Class<?> clazz, Requirements requirements) throws InitializationError {
		super(clazz);
		this.requirements = requirements;
	}

	@Override
	protected Statement withBeforeClasses(Statement statement) {
		Statement s = super.withBeforeClasses(statement);
		return new FulfillRequirementsStatement(requirements, s);
	}
}
