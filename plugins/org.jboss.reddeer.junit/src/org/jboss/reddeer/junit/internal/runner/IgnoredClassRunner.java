package org.jboss.reddeer.junit.internal.runner;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

/**
 * Runner for ignored classes - those where Ignore annotation is present.
 * 
 * @author mlabuda@redhat.com
 * @since 0.8.0
 */
public class IgnoredClassRunner extends Runner {
	
	 private final Class<?> clazz;
	 
	 /**
	  * Creates new IgnoredClassRunner runner.
	  * 
	  * @param testClass
	  */
	 public IgnoredClassRunner(Class<?> testClass) {
		 clazz = testClass;
	 }
	 
	 @Override
	 public void run(RunNotifier notifier) {
		 notifier.fireTestIgnored(getDescription());
	 }
	 
	 @Override
	 public Description getDescription() {
		 return Description.createSuiteDescription(clazz);
	 }
}
