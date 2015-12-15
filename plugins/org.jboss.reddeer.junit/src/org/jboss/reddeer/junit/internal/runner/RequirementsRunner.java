package org.jboss.reddeer.junit.internal.runner;

import java.util.List;

import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.execution.annotation.RunIf;
import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.inject.RequirementsInjector;
import org.jboss.reddeer.junit.internal.runner.statement.CleanUpRequirementStatement;
import org.jboss.reddeer.junit.internal.runner.statement.FulfillRequirementsStatement;
import org.jboss.reddeer.junit.internal.runner.statement.RunAfters;
import org.jboss.reddeer.junit.internal.runner.statement.RunBefores;
import org.jboss.reddeer.junit.internal.runner.statement.RunIAfterClassExtensions;
import org.jboss.reddeer.junit.internal.runner.statement.RunIAfterTestExtensions;
import org.jboss.reddeer.junit.internal.runner.statement.RunIBeforeClassExtensions;
import org.jboss.reddeer.junit.internal.runner.statement.RunIBeforeTestExtensions;
import org.jboss.reddeer.junit.internal.runner.statement.RunTestMethod;
import org.jboss.reddeer.junit.internal.screenrecorder.ScreenCastingRunListener;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * Takes care of running tests + additional features like
 * <ul>
 * 	<li> running requiremnets
 * 	<li> cleanup requirements
 * 	<li> running before/after test extensions
 * 	<li> support for {@link RunIf} annotation
 * </ul>
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
	 * @param clazz the clazz
	 * @throws InitializationError the initialization error
	 */
	protected RequirementsRunner(Class<?> clazz) throws InitializationError{
		super(clazz);
	}
	
	/**
	 * Instantiates a new requirements runner.
	 *
	 * @param clazz the clazz
	 * @param requirements the requirements
	 * @param configId the config id
	 * @param runListeners the run listeners
	 * @param beforeTestExtensions the before test extensions
	 * @throws InitializationError the initialization error
	 */
	public RequirementsRunner(Class<?> clazz, Requirements requirements, String configId, RunListener[] runListeners,List<IBeforeTest> beforeTestExtensions) throws InitializationError {
		this(clazz, requirements, configId, runListeners, beforeTestExtensions, null);
	}

	/**
	 * Instantiates a new requirements runner.
	 *
	 * @param clazz the clazz
	 * @param requirements the requirements
	 * @param configId the config id
	 * @param runListeners the run listeners
	 * @param beforeTestExtensions the before test extensions
	 * @param afterTestExtensions the after test extensions
	 * @throws InitializationError the initialization error
	 */
	public RequirementsRunner(Class<?> clazz, Requirements requirements, String configId, RunListener[] runListeners,List<IBeforeTest> beforeTestExtensions, List<IAfterTest> afterTestExtensions) throws InitializationError {
		super(clazz);
		this.requirements = requirements;
		this.configId = configId;
		this.runListeners = runListeners;
		this.beforeTestExtensions = beforeTestExtensions;
		this.afterTestExtensions = afterTestExtensions;
	}
	
	/**
	 * Instantiates a new requirements runner.
	 *
	 * @param clazz the clazz
	 * @param requirements the requirements
	 * @param configId the config id
	 * @throws InitializationError the initialization error
	 */
	public RequirementsRunner(Class<?> clazz, Requirements requirements, String configId) throws InitializationError {
		this(clazz,requirements,configId,null,null);
	}
	
	/* (non-Javadoc)
	 * @see org.junit.runners.BlockJUnit4ClassRunner#createTest()
	 */
	@Override
	public Object createTest() throws Exception {
		Object testInstance = super.createTest();
		log.debug("Injecting fulfilled requirements into test instance");
		requirementsInjector.inject(testInstance, requirements);
		return testInstance;
	}
	
	/* (non-Javadoc)
	 * @see org.junit.runners.ParentRunner#run(org.junit.runner.notification.RunNotifier)
	 */
	@Override
	public void run(RunNotifier runNotifier) {
		LoggingRunListener loggingRunListener = new LoggingRunListener();
		ScreenCastingRunListener screenCastingRunListener = new ScreenCastingRunListener();
		runNotifier.addListener(loggingRunListener);
		runNotifier.addListener(screenCastingRunListener);
		if (runListeners != null){
			for (RunListener listener : runListeners){
				runNotifier.addListener(listener);
			}
		}
		super.run(runNotifier);
		if (runListeners != null){
			for (RunListener listener : runListeners){
				runNotifier.removeListener(listener);
			}
		}
		runNotifier.removeListener(screenCastingRunListener);
		runNotifier.removeListener(loggingRunListener);
	}
	
	 /* (non-Javadoc)
 	 * @see org.junit.runners.BlockJUnit4ClassRunner#runChild(org.junit.runners.model.FrameworkMethod, org.junit.runner.notification.RunNotifier)
 	 */
 	@Override
	 protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		 Description description = describeChild(method);
		 if (isIgnored(method)) {
			 notifier.fireTestIgnored(description);
		 } else {
			 runLeaf(methodBlock(method), description, notifier);
		 }
	 }
	
	/* (non-Javadoc)
	 * @see org.junit.runners.BlockJUnit4ClassRunner#methodInvoker(org.junit.runners.model.FrameworkMethod, java.lang.Object)
	 */
	@Override
	protected Statement methodInvoker(FrameworkMethod method, Object test) {
	    return new RunTestMethod(configId, getTestClass(), method, test);
	}
	
	/* (non-Javadoc)
	 * @see org.junit.runners.BlockJUnit4ClassRunner#withAfters(org.junit.runners.model.FrameworkMethod, java.lang.Object, org.junit.runners.model.Statement)
	 */
	@Override
	protected Statement withAfters(FrameworkMethod method, Object target,
            Statement statement) {
        
		List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(After.class);

        Statement runAfters = new RunAfters(configId, statement, getTestClass(), method, target, afters);
        Statement runAftersExtensions = new RunIAfterTestExtensions(configId, runAfters, getTestClass(), method, target, afterTestExtensions);
		return runAftersExtensions;
    }
		
	/* (non-Javadoc)
	 * @see org.junit.runners.BlockJUnit4ClassRunner#withBefores(org.junit.runners.model.FrameworkMethod, java.lang.Object, org.junit.runners.model.Statement)
	 */
	@Override
	protected Statement withBefores(FrameworkMethod method, Object target,
            Statement statement) {
        
		List<FrameworkMethod> beforeMethods = getTestClass().getAnnotatedMethods(
                Before.class);
        
        Statement runBefores = new RunBefores(configId, statement, getTestClass(), method, target, beforeMethods);
        Statement runBeforesExtensions = new RunIBeforeTestExtensions(configId, runBefores, getTestClass(), method, target, beforeTestExtensions);
		return runBeforesExtensions;
    }
	
	/* (non-Javadoc)
	 * @see org.junit.runners.ParentRunner#withAfterClasses(org.junit.runners.model.Statement)
	 */
	@Override
	 protected Statement withAfterClasses(Statement statement) {
        List<FrameworkMethod> afterClassMethods = getTestClass().getAnnotatedMethods(AfterClass.class);

        Statement runAfterClass = new RunAfters(configId, statement, getTestClass(), afterClassMethods);
        Statement runRequirements = new CleanUpRequirementStatement(requirements, runAfterClass);
        Statement runAfterClassExtensions = new RunIAfterClassExtensions(configId, runRequirements, getTestClass(), afterTestExtensions);
		return runAfterClassExtensions;
    }
	
	/* (non-Javadoc)
	 * @see org.junit.runners.ParentRunner#withBeforeClasses(org.junit.runners.model.Statement)
	 */
	@Override
	protected Statement withBeforeClasses(Statement statement) {
		log.debug("Injecting fulfilled requirements into static fields of test class");
		requirementsInjector.inject(getTestClass().getJavaClass(), requirements);
		
        List<FrameworkMethod> beforeClassMethods = getTestClass().getAnnotatedMethods(BeforeClass.class);
        
        Statement runBeforeClass = new RunBefores(configId, statement, getTestClass(), beforeClassMethods);
        Statement runRequirements = new FulfillRequirementsStatement(requirements, runBeforeClass);
        Statement runBeforeClassExtensions = new RunIBeforeClassExtensions(configId, runRequirements, getTestClass(), beforeTestExtensions);
		return runBeforeClassExtensions;
	}
	
	/* (non-Javadoc)
	 * @see org.junit.runners.BlockJUnit4ClassRunner#testName(org.junit.runners.model.FrameworkMethod)
	 */
	@Override
	protected String testName(FrameworkMethod method) {
		return method.getName()+" "+configId;
	}
	
	/* (non-Javadoc)
	 * @see org.junit.runners.ParentRunner#getName()
	 */
	@Override
	protected String getName() {
		return super.getName() + " " + configId;
	}
	
	 /* (non-Javadoc)
 	 * @see org.junit.runners.BlockJUnit4ClassRunner#isIgnored(org.junit.runners.model.FrameworkMethod)
 	 */
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
 	
 	@Override
 	protected void validateConstructor(List<Throwable> errors) {
        validateOnlyOneConstructor(errors);
        if (fieldsAreAnnotated()) {
            validateZeroArgConstructor(errors);
        }
 	}
 	
 	 private List<FrameworkField> getAnnotatedFieldsByParameter() {
         return getTestClass().getAnnotatedFields(Parameter.class);
     }

     private boolean fieldsAreAnnotated() {
         return !getAnnotatedFieldsByParameter().isEmpty();
     }
	
	/**
	 * Sets the requirements injector.
	 *
	 * @param requirementsInjector the new requirements injector
	 */
	public void setRequirementsInjector(RequirementsInjector requirementsInjector) {
		this.requirementsInjector = requirementsInjector;
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
}
