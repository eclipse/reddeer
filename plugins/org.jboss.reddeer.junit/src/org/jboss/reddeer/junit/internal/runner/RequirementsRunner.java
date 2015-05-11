package org.jboss.reddeer.junit.internal.runner;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.junit.execution.annotation.RunIf;
import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.inject.RequirementsInjector;
import org.jboss.reddeer.junit.internal.screenrecorder.ScreenRecorderExt;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshot;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshotException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
 * @author Lucia Jelinkova, Vlado Pakan, mlabuda@redhat.com
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

	private static boolean SAVE_SCREENCAST = RedDeerProperties.RECORD_SCREENCAST.getBooleanValue();
	
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
		this.requirementsInjector.inject(clazz, requirements);
	}
	
	public RequirementsRunner(Class<?> clazz, Requirements requirements, String configId) throws InitializationError {
		this(clazz,requirements,configId,null,null);
	}
	
	@Override
	public Object createTest() throws Exception {
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
	protected String getName() {
		return super.getName() + " " + configId;
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
	
	 @Override
	 protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		 Description description = describeChild(method);
		 if (isIgnored(method)) {
			 notifier.fireTestIgnored(description);
		 } else {
			 runLeaf(methodBlock(method), description, notifier);
		 }
	 }
	
	 protected boolean isIgnored(FrameworkMethod child) {
		 RunIf runIfAnnotation = child.getAnnotation(RunIf.class);
		 String testIsIgnoredTemplate = "Test method " + child.getName() + " is ignored because ";
		 boolean ignoreAnnotationIsPresented = child.getAnnotation(Ignore.class) != null;
		 if (runIfAnnotation != null) {
			 try {
				if (runIfAnnotation.conditionClass().newInstance().shouldRun(child)) {
					if (ignoreAnnotationIsPresented) {
						log.info(testIsIgnoredTemplate + " @Ignore annotation is presented.");
						return true;
					} else {
						return false;
					}
				 } else {
					log.info(testIsIgnoredTemplate + " shouldRun method of RunIf conditional run is not met.");
					return true;
				 }
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RedDeerException("Cannot instantiate class of conditional running. Be sure that class has default "
						+ "constructor (is not hidden by another constructor) and is in its own file, not as nested class.", e);
			}
			
	 	}
		 if (ignoreAnnotationIsPresented) {
			 log.info(testIsIgnoredTemplate + " @Ignore annotation is presented.");
			 return true;
		 } else {
			 return false;
		 }
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
				log.error("Unable to initialize Screen Recorder.", ioe);
			} catch (AWTException awte) {
				log.error("Unable to initialize Screen Recorder.", awte);
			}
		}
		if (screenRecorderExt != null) {
			if (!screenRecorderExt.isState(ScreenRecorderExt.STATE_DONE)){
				// try to reset Screen Recorder
				stopScreenRecorder();
			}
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
					log.error("Unable to start Screen Recorder.", ioe);
				}
			} else {
				log.error("Unable to start Screen Recorder.\nScreen Recorder is not in state DONE.\nCurrent status: "
						+ screenRecorderExt.getPublicState());
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
			try {
				if (!screenRecorderExt.isState(ScreenRecorderExt.STATE_RECORDING)) {
					log.error("Stopping Screen Recorder.\nScreen Recorder is not in state RECORDING.\nCurrent status: "
							+ screenRecorderExt.getPublicState());
				}
				screenRecorderExt.stop();
				log.info("Screen Recorder stopped.");
			} catch (IOException ioe) {
				log.error("Unable to stop Screen Recorder.", ioe);
			}
		} else {
			log.error("Unable to stop Screen Recorder.\nScreen Recorder was not properly initilized");
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
			super.testFailure(failure);
		}
		@Override
		public void testFinished(Description description) throws Exception {
			if (RequirementsRunner.SAVE_SCREENCAST){
				RequirementsRunner.stopScreenRecorder();
				if (!wasFailure){
					log.info("Deleting test screencast file: " + outputVideoFile.getAbsolutePath()); 
					outputVideoFile.delete();
				}
			}
			super.testFinished(description);
		}
		@Override
		public void testStarted(Description description) throws Exception {
			wasFailure = false;
			if (RequirementsRunner.SAVE_SCREENCAST){
				outputVideoFile = RequirementsRunner.startScreenRecorder(description.toString());
			}
			super.testStarted(description);
		}
	}

	/**
	 * Method is called before test is run.
	 * Manages org.jboss.reddeer.junit.extensionpoint.IBeforeTest extensions
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
	 * Manages org.jboss.reddeer.junit.extensionpoint.IAfterTest extensions
	 */
	private void runAfterTest() {
		for (IAfterTest afterTestExtension : afterTestExtensions){
			if (afterTestExtension.hasToRun()){
				log.debug("Run method runAfterTest() of class " + afterTestExtension.getClass().getCanonicalName());
				afterTestExtension.runAfterTest(getTestClass());
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
	    			!annotation.expected().isAssignableFrom(t.getClass())) {
	    				log.error("Test " + fTarget.getClass().getName() 
	    					+ "." + fTestMethod.getName()
	    					+ " throws exception: ",t);
		    			CaptureScreenshot screenshot = new CaptureScreenshot();
		    			try {
		    				String fileName = CaptureScreenshot.getScreenshotFileName(
		    					fTarget.getClass(),
		    					fTestMethod.getName(),
		    					null);
		    				screenshot.captureScreenshot(configId, fileName);	    			
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
            new RunAfters(configId, method, statement, afters, target, afterTestExtensions, getTestClass());
    }
		
	@Override
	protected Statement withBefores(FrameworkMethod method, Object target,
            Statement statement) {
        List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(
                Before.class);
        return befores.isEmpty() ? statement : new RunBefores(configId, method, statement,
                befores, target, getTestClass());
    }
	
	@Override
	 protected Statement withAfterClasses(Statement statement) {
        List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(AfterClass.class);
        Statement s = afters.isEmpty() ? statement : new RunAfters(configId, null, statement, afters,
        		null,getTestClass());
        runAfterTest();
        return new CleanUpRequirementStatement(requirements, s);
    }
	
	@Override
	protected Statement withBeforeClasses(Statement statement) {
        List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(BeforeClass.class);
        Statement s = befores.isEmpty() ? statement : new RunBefores(configId, null, statement, befores, 
        		null, getTestClass());
		runBeforeTest();
		return new FulfillRequirementsStatement(requirements, s);
	}

}
