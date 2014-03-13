package org.jboss.reddeer.junit.internal.runner;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.inject.RequirementsInjector;
import org.jboss.reddeer.junit.internal.screenrecorder.ScreenRecorderExt;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshot;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshotException;
import org.jboss.reddeer.junit.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * Fulfills the requirements before {@link BeforeClass} is called and
 * injects requirements to proper injection points 
 * 
 * @author Lucia Jelinkova, Vlado Pakan
 *
 */
public class RequirementsRunner extends BlockJUnit4ClassRunner {
	
	private static final Logger log = Logger.getLogger(RequirementsRunner.class);
	
	private static ScreenRecorderExt screenRecorderExt = null;
	
	private Requirements requirements;
	
	private RunListener[] runListeners;

	private RequirementsInjector requirementsInjector = new RequirementsInjector();
	
	private String configId;
	
	private List<IBeforeTest> beforeTestExtensions;
	
	private List<IAfterTest> afterTestExtensions;

	private static boolean SAVE_SCREENCAST = System.getProperty("recordScreenCast","false").equalsIgnoreCase("true");
	
	public RequirementsRunner(Class<?> clazz, Requirements requirements, String configId, RunListener[] runListeners,List<IBeforeTest> beforeTestExtensions) throws InitializationError {
		this(clazz, requirements, configId, runListeners, beforeTestExtensions, null);
	}

	public RequirementsRunner(Class<?> clazz, Requirements requirements, String configId, RunListener[] runListeners,List<IBeforeTest> beforeTestExtensions, List<IAfterTest> afterTestExtensions) throws InitializationError {
		super(clazz);
		this.requirements = requirements;
		this.configId = configId;
		this.runListeners = runListeners;
		this.beforeTestExtensions = beforeTestExtensions;
		this.afterTestExtensions = afterTestExtensions;
	}
	
	public RequirementsRunner(Class<?> clazz, Requirements requirements, String configId) throws InitializationError {
		this(clazz,requirements,configId,null,null);
	}

	@Override
	protected Statement withBeforeClasses(Statement statement) {
        List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(BeforeClass.class);
        Statement s = befores.isEmpty() ? statement : new RunBefores(statement, befores, null);
		runBeforeTest();
		return new FulfillRequirementsStatement(requirements, s);
	}
	
	@Override
	protected Object createTest() throws Exception {
		Object testInstance = super.createTest();
		log.debug("Injecting fulfilled requirements into test instance");
		requirementsInjector.inject(testInstance, requirements);
		return testInstance;
	}
	
	@Override
	protected String testName(FrameworkMethod method) {
		return method.getName()+" "+configId;
	}
	
	@Override
	public void run(RunNotifier arg0) {
		LoggingRunListener loggingRunListener = new LoggingRunListener();
		ScreenCastingRunListener screenCastingRunListener = new ScreenCastingRunListener();
		arg0.addListener(loggingRunListener);
		arg0.addListener(screenCastingRunListener);
		if (runListeners != null){
			for (RunListener listener : runListeners){
				arg0.addListener(listener);
			}
		}
		super.run(arg0);
		if (runListeners != null){
			for (RunListener listener : runListeners){
				arg0.removeListener(listener);
			}
		}
		arg0.removeListener(screenCastingRunListener);
		arg0.removeListener(loggingRunListener);
	}
	public void setRequirementsInjector(RequirementsInjector requirementsInjector) {
		this.requirementsInjector = requirementsInjector;
	}

	/**
	 * Starts Screen Recorder
	 */
	private static File startScreenRecorder(String className) {
		File outputVideoFile = null;
		if (screenRecorderExt == null) {
			try {
				screenRecorderExt = new ScreenRecorderExt();
			} catch (IOException ioe) {
				throw new RuntimeException(
						"Unable to initialize Screen Recorder.", ioe);
			} catch (AWTException awte) {
				throw new RuntimeException(
						"Unable to initialize Screen Recorder.", awte);
			}
		}
		if (screenRecorderExt != null) {
			if (screenRecorderExt.isState(ScreenRecorderExt.STATE_DONE)) {
				try {
					File screenCastDir = new File("screencasts");
					if (!screenCastDir.exists()) {
						screenCastDir.mkdir();
					}
					final String fileName = "screencasts" + File.separator
							+ className
							+ ".mov";
					log.info("Starting Screen Recorder. Saving Screen Cast to file: "
							+ fileName);
					screenRecorderExt.start(fileName);
					outputVideoFile = new File(fileName);
				} catch (IOException ioe) {
					throw new RuntimeException(
							"Unable to start Screen Recorder.", ioe);
				}
			} else {
				throw new RuntimeException(
						"Unable to start Screen Recorder.\nScreen Recorder is not in state DONE.");
			}
		} else {
			log.error("Screen Recorder was not properly initilized");
		}
		return outputVideoFile;
	}

	/**
	 * Stops Screen Recorder
	 */
	private static void stopScreenRecorder() {
		if (screenRecorderExt != null) {
			if (screenRecorderExt.isState(ScreenRecorderExt.STATE_RECORDING)) {
				try {
					screenRecorderExt.stop();
					log.info("Screen Recorder stopped.");
				} catch (IOException ioe) {
					throw new RuntimeException(
							"Unable to stop Screen Recorder.", ioe);
				}
			} else {
				throw new RuntimeException(
						"Unable to stop Screen Recorder.\nScreen Recorder is not in state RECORDING.");
			}
		} else {
			throw new RuntimeException(
					"Unable to stop Screen Recorder.\nScreen Recorder was not properly initilized");
		}
	}
	
	private class LoggingRunListener extends RunListener {
		@Override
		public void testFailure(Failure failure) throws Exception {
			Throwable throwable = failure.getException();
			// it's test failure
			if (throwable instanceof AssertionError){
				log.error("Failed test: " + failure.getDescription(),throwable);
			}
			// it's Exception
			else {
				log.error("Exception in test: " + failure.getDescription(),throwable);
			}
			super.testFailure(failure);
		}
		@Override
		public void testFinished(Description description) throws Exception {
			log.info("Finished test: " + description);
			super.testFinished(description);
		}
		@Override
		public void testIgnored(Description description) throws Exception {
			log.info("Ignored test: " + description);
			super.testIgnored(description);
		}
		@Override
		public void testStarted(Description description) throws Exception {
			log.info("Started test: " + description);
			super.testStarted(description);
		}
	}
	
	private class ScreenCastingRunListener extends RunListener {
		private File outputVideoFile = null;
		private boolean wasFailure = false;
		@Override
		public void testFailure(Failure failure) throws Exception {
			wasFailure = true;
			Throwable throwable = failure.getException();
			// it's test failure
			if (throwable instanceof AssertionError){
				log.error("Failed test: " + failure.getDescription(),throwable);
			}
			// it's Exception
			else {
				log.error("Exception in test: " + failure.getDescription(),throwable);
			}
			if (RequirementsRunner.SAVE_SCREENCAST){
				RequirementsRunner.stopScreenRecorder();
			}
			super.testFailure(failure);
		}
		@Override
		public void testFinished(Description description) throws Exception {
			log.info("Finished test: " + description);
			if (RequirementsRunner.SAVE_SCREENCAST && !wasFailure){
				RequirementsRunner.stopScreenRecorder();
				log.info("Deleting test screencast file: " + outputVideoFile.getAbsolutePath()); 
				outputVideoFile.delete();
			}
			super.testFinished(description);
		}
		@Override
		public void testIgnored(Description description) throws Exception {
			log.info("Ignored test: " + description);
			super.testIgnored(description);
		}
		@Override
		public void testStarted(Description description) throws Exception {
			log.info("Started test: " + description);
			wasFailure = false;
			if (RequirementsRunner.SAVE_SCREENCAST){
				outputVideoFile = RequirementsRunner.startScreenRecorder(description.toString());
			}
			super.testStarted(description);
		}
	}

	/**
	 * Method is called before test is run.
	 * Manages org.jbossreddeer.junit.before.test extensions
	 */
	private void runBeforeTest() {
		for (IBeforeTest beforeTestExtension : beforeTestExtensions){
			if (beforeTestExtension.hasToRun()){
				log.debug("Run method runBeforeTest() of class " + beforeTestExtension.getClass().getCanonicalName());
				beforeTestExtension.runBeforeTest();
			}
		}
	}
	
	/**
	 * Method is called after test is run.
	 * Manages org.jbossreddeer.junit.after.test extensions
	 */
	public void runAfterTest() {
		if(afterTestExtensions == null) {
			return;
		}
		for (IAfterTest afterTestExtension : afterTestExtensions){
			if (afterTestExtension.hasToRun()){
				log.debug("Run method runAfterTest() of class " + afterTestExtension.getClass().getCanonicalName());
				afterTestExtension.runAfterTest();
			}
		}
	}
	
	private class InvokeMethodWithException extends Statement {
	    private final FrameworkMethod fTestMethod;
	    private Object fTarget;

	    public InvokeMethodWithException(FrameworkMethod testMethod, Object target) {
	        fTestMethod = testMethod;
	        fTarget = target;
	    }

	    @Override
	    public void evaluate() throws Throwable {
	    	try{
	    		fTestMethod.invokeExplosively(fTarget);	
	    	} catch (Throwable t){
	    		Test annotation = (Test) fTestMethod.getAnnotations()[0];
	    		if (annotation.expected().getName().equals("org.junit.Test$None") ||
	    			!annotation.expected().getName().equals(t.getClass().getName())) {
		    			CaptureScreenshot screenshot = new CaptureScreenshot();
		    			try {
		    				screenshot.captureScreenshot(fTarget.getClass().getCanonicalName() + "-" + fTestMethod.getName());	    			
		    			} catch (CaptureScreenshotException ex) {
		    				ex.printInfo(log);
		    			}
	    		}	
	    		throw t;
	    	}
	        
	    }
	}
	
	@Override
	protected Statement methodInvoker(FrameworkMethod method, Object test) {
	    return new InvokeMethodWithException(method, test);
	}
	
	@Override
	protected Statement withAfters(FrameworkMethod method, Object target,
            Statement statement) {
        List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(After.class);
        return afters.isEmpty() && afterTestExtensions.isEmpty() ? statement :
            new RunAfters(statement, afters, target, afterTestExtensions);
    }
		
	@Override
	protected Statement withBefores(FrameworkMethod method, Object target,
            Statement statement) {
        List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(
                Before.class);
        return befores.isEmpty() ? statement : new RunBefores(statement,
                befores, target);
    }
	
	@Override
	 protected Statement withAfterClasses(Statement statement) {
        List<FrameworkMethod> afters = getTestClass()
                .getAnnotatedMethods(AfterClass.class);
        return afters.isEmpty() && afterTestExtensions.isEmpty() ? statement :
                new RunAfters(statement, afters, null);
    }

}
