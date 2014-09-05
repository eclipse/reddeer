package org.jboss.reddeer.junit.internal.runner;

import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshot;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshotException;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * 
 * Statement which run before tests or classes. Upon failure a screenshot is captured.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class RunBefores extends Statement {
	private final Statement fNext;

	private final Object fTarget;

	private final List<FrameworkMethod> fBefores;
	
	private final FrameworkMethod fMethod;
	
	private TestClass testClass;
	
	private String config;

	private static final Logger log = Logger.getLogger(RequirementsRunner.class);
	
	public RunBefores(String config, FrameworkMethod method, Statement next, List<FrameworkMethod> befores,
			Object target, TestClass testClass) {
		fNext = next;
		fBefores = befores;
		fTarget = target;
		fMethod = method;
		this.testClass = testClass;
		this.config = config;
	}

	@Override
	public void evaluate() throws Throwable {
		FrameworkMethod before = null;
		try {
			for (FrameworkMethod bfr : fBefores) {
				before = bfr;
				before.invokeExplosively(fTarget);
			}
		} catch (Throwable throwable) {
						
			CaptureScreenshot capturer = new CaptureScreenshot();
			try {
				String methodName = "";
        		if (fMethod != null) {
        			methodName = "#" + fMethod.getName();
        		}
        		String fileName;
        		if (fTarget == null) {
        			fileName = testClass.getJavaClass().getSimpleName()
        					+ "@BeforeClass#" + before.getName() + "["
        					+ testClass.getJavaClass().getPackage().getName() + "]";
        		} else {
        			fileName = fTarget.getClass().getSimpleName() 
        				+ methodName + "@Before#" + before.getName()
						+ "[" + fTarget.getClass().getPackage().getName() + "]";
        		}
				capturer.captureScreenshot(config, fileName);
				
				
			} catch (CaptureScreenshotException ex) {
				ex.printInfo(log);
			}
			throw throwable;
		}
		fNext.evaluate();
	}
}