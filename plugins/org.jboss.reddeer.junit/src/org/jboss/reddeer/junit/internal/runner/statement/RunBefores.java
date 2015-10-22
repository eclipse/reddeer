package org.jboss.reddeer.junit.internal.runner.statement;

import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * 
 * Statement which run before tests. Upon failure a screenshot is captured.
 * 
 * @author mlabuda@redhat.com
 * @author ljelinko
 *
 */
public class RunBefores extends AbstractStatementWithScreenshot {

	private final List<FrameworkMethod> befores;

	public RunBefores(String config, Statement next, TestClass testClass, List<FrameworkMethod> befores) {
		this(config, next, testClass, null, null, befores);
	}
	
	public RunBefores(String config, Statement next, TestClass testClass, FrameworkMethod method, Object target,
			List<FrameworkMethod> befores) {
		super(config, next, testClass, method, target);
		this.befores = befores;
	}

	@Override
	public void evaluate() throws Throwable {
		FrameworkMethod before = null;
		try {
			for (FrameworkMethod bfr : befores) {
				before = bfr;
				before.invokeExplosively(target);
			}
		} catch (Throwable throwable) {
			if (isClassLevel()){
				frameworkMethod = before;
				createScreenshot("BeforeClass");
			} else {
				createScreenshot("Before_" + before.getName());				
			}
			throw throwable;
		}
		nextStatement.evaluate();
	}
}