package org.jboss.reddeer.junit.internal.runner.statement;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.screenshot.CaptureScreenshotException;
import org.jboss.reddeer.junit.screenshot.ScreenshotCapturer;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Common ancestor for statements that need to save screenshot. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractStatementWithScreenshot extends Statement {

	private static final Logger log = Logger.getLogger(AbstractStatementWithScreenshot.class);

	protected String config;

	protected Statement nextStatement;

	protected TestClass testClass;

	protected FrameworkMethod frameworkMethod;

	protected Object target;

	public AbstractStatementWithScreenshot(String config, Statement next, TestClass testClass, FrameworkMethod method, Object target) {
		this.config = config;
		this.nextStatement = next;
		this.testClass = testClass;
		this.frameworkMethod = method;
		this.target = target;
	}

	protected boolean isClassLevel(){
		return target == null;
	}
	
	protected void createScreenshot() {
		try {
			ScreenshotCapturer capturer = ScreenshotCapturer.getInstance();
			capturer.captureScreenshotOnFailure(config, getScreenshotFilename());
		} catch (CaptureScreenshotException ex) {
			ex.printInfo(log);
		}
	}
	
	protected void createScreenshot(String description) {
		try {
			ScreenshotCapturer capturer = ScreenshotCapturer.getInstance();
			capturer.captureScreenshotOnFailure(config, getScreenshotFilename(description));
		} catch (CaptureScreenshotException ex) {
			ex.printInfo(log);
		}
	}
	
	protected void createScreenshot(String description, Class<?> extensionClass) {
		try {
			ScreenshotCapturer capturer = ScreenshotCapturer.getInstance();
			capturer.captureScreenshotOnFailure(config, getScreenshotFilename(description, extensionClass));
		} catch (CaptureScreenshotException ex) {
			ex.printInfo(log);
		}
	}
	
	private String getScreenshotFilename(String description, Class<?> extraClass){
		String methodName = frameworkMethod != null ? frameworkMethod.getName() : null;
		return ScreenshotCapturer.getScreenshotFileName(
				testClass.getJavaClass(),
				methodName,
				description + "_" + extraClass.getSimpleName());
	}
	
	private String getScreenshotFilename(String description){
		String methodName = frameworkMethod != null ? frameworkMethod.getName() : null;
		return ScreenshotCapturer.getScreenshotFileName(
				testClass.getJavaClass(),
				methodName,
				description);
	}
	
	private String getScreenshotFilename(){
		String methodName = frameworkMethod != null ? frameworkMethod.getName() : null;
		return ScreenshotCapturer.getScreenshotFileName(
				testClass.getJavaClass(),
				methodName,
				null);
	}
}
