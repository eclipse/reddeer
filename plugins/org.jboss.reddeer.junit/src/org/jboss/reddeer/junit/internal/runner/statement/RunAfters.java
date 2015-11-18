package org.jboss.reddeer.junit.internal.runner.statement;

import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Statement which run after tests or classes. Upon failure a screenshot is captured.
 * 
 * @author mlabuda@redhat.com
 * @author ljelinko
 *
 */
public class RunAfters extends AbstractStatementWithScreenshot {

	private final List<FrameworkMethod> fAfters;

	/**
	 * Instantiates a new run afters.
	 *
	 * @param config the config
	 * @param next the next
	 * @param testClass the test class
	 * @param afters the afters
	 */
	public RunAfters(String config, Statement next, TestClass testClass, List<FrameworkMethod> afters) {
		this(config, next, testClass, null, null, afters);
	}

	/**
	 * Instantiates a new run afters.
	 *
	 * @param config the config
	 * @param next the next
	 * @param testClass the test class
	 * @param method the method
	 * @param target the target
	 * @param afters the afters
	 */
	public RunAfters(String config, Statement next, TestClass testClass, 
			FrameworkMethod method, Object target, List<FrameworkMethod> afters) {
		super(config, next, testClass, method, target);
		fAfters = afters;
	}

	/* (non-Javadoc)
	 * @see org.junit.runners.model.Statement#evaluate()
	 */
	@Override
	public void evaluate() throws Throwable {
		List<Throwable> errors = new ArrayList<Throwable>();

		try {
			nextStatement.evaluate();
		} catch (Throwable e) {
			errors.add(e);
		} 
		
		for (FrameworkMethod each : fAfters) {
			try {
				frameworkMethod = each; 
				frameworkMethod.invokeExplosively(target);
			} catch (Throwable e) {
				if (isClassLevel()){
					createScreenshot("AfterClass");
				} else {
					createScreenshot("After");				
				}
				errors.add(e);
			}
		}
		
		MultipleFailureException.assertEmpty(errors);
	}
}
