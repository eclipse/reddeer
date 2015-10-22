package org.jboss.reddeer.junit.internal.runner.statement;

import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * 
 * Statement which runs {@link IBeforeTest#runBeforeTestClass()} methods of
 * defined extensions. Upon failure a screenshot is captured.
 * 
 * @author Lucia Jelinkova
 *
 */
public class RunIBeforeClassExtensions extends AbstractStatementWithScreenshot {

	private static final Logger log = Logger.getLogger(RunIBeforeClassExtensions.class);

	private final List<IBeforeTest> befores;

	public RunIBeforeClassExtensions(String config, Statement next, TestClass testClass, 
			List<IBeforeTest> befores) {
		super(config, next, testClass, null, null);
		this.befores = befores;
	}

	@Override
	public void evaluate() throws Throwable {
		IBeforeTest before = null;

		log.debug("Run before class extensions for test class " + testClass.getJavaClass().getName());
		try {
			for (IBeforeTest bfr : befores) {
				before = bfr;
				if (before.hasToRun()){
					log.debug("Run method runBeforeTestClass() of class " + before.getClass().getCanonicalName());
					before.runBeforeTestClass(config, testClass);
				}
			}
		} catch (Throwable e) {
			log.error("Run method runBeforeTestClass() of class " + before.getClass().getCanonicalName() + " failed", e);
			createScreenshot("BeforeClassExt", before.getClass());
			throw e;
		}

		nextStatement.evaluate();
	}
}