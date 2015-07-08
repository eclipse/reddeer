package org.jboss.reddeer.junit.internal.runner;

import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.screenshot.CaptureScreenshotException;
import org.jboss.reddeer.junit.screenshot.ScreenshotCapturer;
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
			String methodName = fMethod != null ? fMethod.getName() : null;
    		Class<?> testObjectClass = fTarget != null ? fTarget.getClass() : testClass.getJavaClass();						
			ScreenshotCapturer capturer = ScreenshotCapturer.getInstance();
			try {
        		String fileName;
        		if (fTarget == null) {
        			fileName = ScreenshotCapturer.getScreenshotFileName(
        					testObjectClass,
        					methodName,
        					"BeforeClass_" + before.getName());
        		} else {
        			fileName = ScreenshotCapturer.getScreenshotFileName(
        				testObjectClass,
        				methodName,
        				"Before_" + before.getName());
        		}
        		log.error("Test " + testObjectClass.getName()  
    					+ "." + methodName
    					+ " throws exception: ",throwable);
				capturer.captureScreenshotOnFailure(config, fileName);
				
			} catch (CaptureScreenshotException ex) {
				ex.printInfo(log);
			}
			throw throwable;
		}
		fNext.evaluate();
	}
}