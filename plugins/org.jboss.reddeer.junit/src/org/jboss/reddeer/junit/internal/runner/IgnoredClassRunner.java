/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
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
 	 * @param testClass the test class
 	 */
	 public IgnoredClassRunner(Class<?> testClass) {
		 clazz = testClass;
	 }
	 
	 /* (non-Javadoc)
 	 * @see org.junit.runner.Runner#run(org.junit.runner.notification.RunNotifier)
 	 */
 	@Override
	 public void run(RunNotifier notifier) {
		 notifier.fireTestIgnored(getDescription());
	 }
	 
	 /* (non-Javadoc)
 	 * @see org.junit.runner.Runner#getDescription()
 	 */
 	@Override
	 public Description getDescription() {
		 return Description.createSuiteDescription(clazz);
	 }
}
