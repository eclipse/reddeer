/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.condition;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Platform;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.swt.api.Browser;
import org.eclipse.reddeer.swt.condition.PageIsLoaded;
import org.eclipse.reddeer.swt.impl.browser.InternalBrowser;

/**
 * Wait condition which returns true if a given browser has the specified URL and specific text.
 */
public class BrowserContainsText extends AbstractWaitCondition {

	private Browser browser;
	private String text;
	
	public BrowserContainsText(Browser browser, String text) {
		this.browser = browser;
		this.text = text;
	}

	public BrowserContainsText(String text) {
		this(new InternalBrowser(), text);
	}

	@Override
	public boolean test() {
		new WaitUntil(new PageIsLoaded(browser));

		if (Platform.getOS().startsWith(Platform.OS_WIN32)) {
			return browser.getText().contains(text);
		} else {
			// Workaround for webkit issues with method browser.getText(), e.g.
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=514719
			String pageHTML = "";
			if (!StringUtils.isEmpty(browser.getURL())) {
				pageHTML = (String) browser.evaluate("return document.documentElement.innerHTML;");
			}
			return pageHTML.contains(text);
		}

	}

	@Override
	public String description() {
		return "browser contains text: " + this.text;
	}
}
