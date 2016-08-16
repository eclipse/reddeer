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
package org.jboss.reddeer.junit.extension.before.test.impl;

import org.eclipse.ui.IViewReference;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.core.lookup.WorkbenchPartLookup;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.junit.extension.ExtensionPriority;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;
/**
 * Extension for Extension point org.jboss.reddeer.junit.before.test
 * Closes Welcome screen prior test is run
 * Use this system property to enable/disable it:
 * 
 * - rd.closeWelcomeScreen=[true|false] (default=true)
 * 
 * @author vlado pakan
 *
 */
public class CloseWelcomeScreenExt implements IBeforeTest {
	
	private static final Logger log = Logger.getLogger(CloseWelcomeScreenExt.class);
	
	private static final boolean CLOSE_WELCOME_SCREEN = RedDeerProperties.CLOSE_WELCOME_SCREEN.getBooleanValue();

	@Override
	public void runBeforeTestClass(String config, TestClass testClass) {
		closeWelcomeScreen();		
	}
	
	/**
	 * See {@link IBeforeTest}
	 */
	@Override
	public void runBeforeTest(String config, Object target, FrameworkMethod method) {
		// do not run before each test
	}
	/**
	 * Closes welcome screen
	 */
	private void closeWelcomeScreen() {
		log.debug("Trying to close Welcome Screen");
		for (IViewReference viewReference : WorkbenchPartLookup.getInstance().findAllViewReferences()) {
			if (viewReference.getPartName().equals("Welcome")) {
				final IViewReference iViewReference = viewReference;
				Display.syncExec(new Runnable() {
					@Override
					public void run() {
						iViewReference.getPage().hideView(iViewReference);
					}
				});
				log.debug("Welcome Screen closed");
				break;
			}
		}
	}
	/**
	 * See {@link IBeforeTest}
	 */
	@Override
	public boolean hasToRun() {
		return CloseWelcomeScreenExt.CLOSE_WELCOME_SCREEN;
	}

	@Override
	public long getPriority() {
		return ExtensionPriority.CLOSE_WELCOME_SCREEN_PRIORITY;
	}

}
