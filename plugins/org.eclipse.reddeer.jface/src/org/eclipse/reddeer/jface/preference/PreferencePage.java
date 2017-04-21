/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.jface.preference;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.clabel.DefaultCLabel;

/**
 * Represents a general preference page.
 * 
 * @author Lucia Jelinkova
 * @since 0.6
 */
public abstract class PreferencePage {

	private final Logger log = Logger.getLogger(PreferencePage.class);

	private String[] path;
	
	/**
	 * Constructor sets path to specific preference item.
	 * @param path path in preference shell tree to specific preference
	 */
	public PreferencePage(String... path) {
		if (path == null) {
			throw new IllegalArgumentException("path can't be null");
		}
		if (path.length == 0) {
			throw new IllegalArgumentException("path can't be empty");
		}
		this.path = path;
	}

	/**
	 * Get name of the given preference page.
	 * 
	 * @return name of preference page
	 */
	public String getName() {
		DefaultCLabel cl = new DefaultCLabel();
		return cl.getText();
	}
	
	/**
	 * Returns path of the preference item.
	 *
	 * @return the path
	 */
	public String[] getPath() {
		return path.clone();
	}

	/**
	 * Apply preference page changes.
	 */
	public void apply() {
		Button b = new PushButton("Apply");
		log.info("Apply changes in Preferences dialog");
		b.click();
	}

	/**
	 * Restore default preference page settings.
	 */
	public void restoreDefaults() {
		Button b = new PushButton("Restore Defaults");
		log.info("Restore default values in Preferences dialog");
		b.click();
	}
}
