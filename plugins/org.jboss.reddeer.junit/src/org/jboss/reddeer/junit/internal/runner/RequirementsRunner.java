package org.jboss.reddeer.junit.internal.runner;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.inject.RequirementsInjector;
import org.jboss.reddeer.junit.internal.screenrecorder.ScreenRecorderExt;
import org.junit.BeforeClass;
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

	private static boolean SAVE_SCREENCAST = System.getProperty("recordScreenCast","false").equalsIgnoreCase("true");
	
	public RequirementsRunner(Class<?> clazz, Requirements requirements, String configId, RunListener[] runListeners) throws InitializationError {
		super(clazz);
		this.requirements = requirements;
		this.configId = configId;
		this.runListeners = runListeners;
	}

	public RequirementsRunner(Class<?> clazz, Requirements requirements, String configId) throws InitializationError {
		this(clazz,requirements,configId,null);
	}

	@Override
	protected Statement withBeforeClasses(Statement statement) {
		Statement s = super.withBeforeClasses(statement);
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
		final String beforeTestExtensionID = "org.jboss.reddeer.junit.before.test";
		IConfigurationElement[] configElements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(beforeTestExtensionID);
		try {
			log.debug("Number of found extensions for extension point " + beforeTestExtensionID + "="
				+ configElements.length);
			for (IConfigurationElement e : configElements) {
				final Object o = e.createExecutableExtension("class");
				if (o instanceof IBeforeTest) {
					log.debug("Run method runBeforeTest() of class" + o.getClass().getCanonicalName());
					((IBeforeTest) o).runBeforeTest();
				}
				else{
					log.warn("Invalid class used for extension point " + beforeTestExtensionID
						+ ":" + o.getClass());
				}
			}
		} catch (CoreException ex) {
			log.error(
					"Error when processing extension for org.jbossreddeer.junit.before.test",
					ex.getMessage());
		}
	}
}
