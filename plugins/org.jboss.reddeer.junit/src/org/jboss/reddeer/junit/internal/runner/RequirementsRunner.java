package org.jboss.reddeer.junit.internal.runner;

import java.util.List;

import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.execution.annotation.RunIf;
import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.inject.RequirementsInjector;
import org.jboss.reddeer.junit.internal.screenrecorder.ScreenCastingRunListener;
import org.jboss.reddeer.junit.screenshot.CaptureScreenshotException;
import org.jboss.reddeer.junit.screenshot.ScreenshotCapturer;
import org.jboss.reddeer.junit.tracking.issue.IssueLinkResolver;
import org.jboss.reddeer.junit.tracking.issue.KnownIssues;
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
	
	protected Requirements requirements;
	
	protected RunListener[] runListeners;

	protected RequirementsInjector requirementsInjector = new RequirementsInjector();
	
	protected String configId;
	
	protected List<IBeforeTest> beforeTestExtensions;
	
	protected List<IAfterTest> afterTestExtensions;
	
	
	/**
	 * Runner used by subclasses.
	 * 
	 * @param clazz
	 * @throws InitializationError
	 */
	protected RequirementsRunner(Class<?> clazz) throws InitializationError{
		super(clazz);
	}
	
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
		    			ScreenshotCapturer screenshot = ScreenshotCapturer.getInstance();
		    			try {
		    				String fileName = ScreenshotCapturer.getScreenshotFileName(
		    					fTarget.getClass(),
		    					fTestMethod.getName(),
		    					null);
		    				screenshot.captureScreenshotOnFailure(null, fileName);	    			
		    			} catch (CaptureScreenshotException ex) {
		    				ex.printInfo(log);
		    			}
	    		}
	    		String knownIssues = getKnownIssues(fTestMethod);
	    		if (knownIssues != null) {
	    			throw new Throwable(knownIssues, t);
	    		}
	    		throw t;
	    	}
	    }
	}
	
	private String getKnownIssues(FrameworkMethod method) {
		KnownIssues issues = method.getAnnotation(KnownIssues.class);
		if (issues != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("There are known issues for this test:" + "\n");
			for (String issue: issues.value()) {
				sb.append(" " + issue);
				String link = IssueLinkResolver.getIssueLink(issue);
				if (link != null) {
					sb.append(" (" + link + ")");
				}
				sb.append("\n");
			}
			return sb.toString();
		}
		return null;
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
		log.debug("Injecting fulfilled requirements into static fields of test class");
		requirementsInjector.inject(getTestClass().getJavaClass(), requirements);
		
        List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(BeforeClass.class);
        Statement s = befores.isEmpty() ? statement : new RunBefores(configId, null, statement, befores, 
        		null, getTestClass());
		runBeforeTest();
		return new FulfillRequirementsStatement(requirements, s);
	}

}
