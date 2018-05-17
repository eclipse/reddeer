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
package org.eclipse.reddeer.eclipse.jdt.ui.preferences.internal;

import org.eclipse.reddeer.core.matcher.WithLabelMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.text.DefaultText;

/**
 * Second page of "Add JRE" wizard accesible from Preferences &gt; Java &gt; Installed
 * JREs.
 * 
 */
public class StandardVMPage extends WizardPage {
	
	public StandardVMPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Sets JRE home field.
	 *
	 * @param path the new JRE home
	 */

	public StandardVMPage setJREHome(String path) {
		// no need to check whether file exists, Wizard will do it for us.
		new DefaultText(this, new WithLabelMatcher("JRE home:")).setText(path);
		return this;
	}

	/**
	 * Sets name field.
	 *
	 * @param name the new name
	 */

	public StandardVMPage setName(String name) {
		new DefaultText(this, new WithLabelMatcher("JRE name:")).setText(name);
		return this;
	}

	/**
	 * Obtains error/warning message from this wizard page.
	 * 
	 * @return error/warning message.
	 */

	public String getErrorMessage() {
		return new DefaultText(this, new WithLabelMatcher("JRE Definition")).getText();
	}
}
