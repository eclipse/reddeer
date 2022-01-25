/*******************************************************************************
 * Copyright (c) 2022 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package org.eclipse.reddeer.requirements.browser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.direct.preferences.Preferences;
import org.eclipse.reddeer.junit.requirement.AbstractRequirement;
import org.eclipse.reddeer.requirements.browser.InternalBrowserRequirement.UseInternalBrowser;

/**
 * RedDeer Requirement that allows to setup an Internal Browser option as default 
 * in Preferences: General -> Web Browser.
 * 
 * @author Ondrej Dockal
 *
 */
public class InternalBrowserRequirement extends AbstractRequirement<UseInternalBrowser>{
	
	private static String BROWSER_PLUGIN = "org.eclipse.ui.browser";
	private static String BROWSER_KEY = "browser-choice";
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface UseInternalBrowser {
		
		/**
		 * String param dedicated to Internal Browser, change to "1" of necessary
		 * @return 0 option to denote Internal browser, 1 for external
		 */
		String browserChoice() default "0";
		
		boolean cleanup() default false;
	}

	@Override
	public void fulfill() {
		Preferences.set(BROWSER_PLUGIN, BROWSER_KEY, annotation.browserChoice());
	}

	@Override
	public void cleanUp() {
		if (annotation.cleanup()) {
			Preferences.setDefault(BROWSER_PLUGIN, BROWSER_KEY);
		}
	}

}
