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

	public void setJREHome(String path) {
		// no need to check whether file exists, Wizard will do it for us.
		new DefaultText(referencedComposite, new WithLabelMatcher("JRE home:")).setText(path);
	}

	/**
	 * Sets name field.
	 *
	 * @param name the new name
	 */

	public void setName(String name) {
		new DefaultText(referencedComposite, new WithLabelMatcher("JRE name:")).setText(name);
	}

	/**
	 * Obtains error/warning message from this wizard page.
	 * 
	 * @return error/warning message.
	 */

	public String getErrorMessage() {
		return new DefaultText(referencedComposite, new WithLabelMatcher("JRE Definition")).getText();
	}
}
