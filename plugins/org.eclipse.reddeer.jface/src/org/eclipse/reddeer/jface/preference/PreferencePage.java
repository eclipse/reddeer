/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.jface.preference;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.clabel.DefaultCLabel;
import org.eclipse.swt.widgets.Control;

/**
 * Represents a general preference page.
 * 
 * @author Lucia Jelinkova
 * @since 0.6
 */
public class PreferencePage implements ReferencedComposite{

	private final Logger log = Logger.getLogger(PreferencePage.class);

	private String[] path;
	protected ReferencedComposite referencedComposite;
	
	/**
	 * Constructor sets path to specific preference item.
	 * @param path path in preference shell tree to specific preference
	 */
	public PreferencePage(ReferencedComposite referencedComposite, String... path) {
		if (path == null) {
			throw new IllegalArgumentException("path can't be null");
		}
		if (path.length == 0) {
			throw new IllegalArgumentException("path can't be empty");
		}
		if(referencedComposite == null) {
			throw new IllegalArgumentException("referencedComposite can't be null");
		}
		if(referencedComposite.getControl() != null && referencedComposite.getControl().isDisposed()) {
			throw new IllegalArgumentException("referencedComposite is disposed");
		}
		this.referencedComposite = referencedComposite;
		this.path = path;
	}

	/**
	 * Get name of the given preference page.
	 * 
	 * @return name of preference page
	 */
	public String getName() {
		DefaultCLabel cl = new DefaultCLabel(referencedComposite);
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
	public PreferencePage apply() {
		Button b = new PushButton(referencedComposite, "Apply");
		log.info("Apply changes in Preferences dialog");
		b.click();
		return this;
	}

	/**
	 * Restore default preference page settings.
	 */
	public PreferencePage restoreDefaults() {
		Button b = new PushButton(referencedComposite, "Restore Defaults");
		log.info("Restore default values in Preferences dialog");
		b.click();
		return this;
	}

	@Override
	public Control getControl() {
		return referencedComposite.getControl();
	}
}
