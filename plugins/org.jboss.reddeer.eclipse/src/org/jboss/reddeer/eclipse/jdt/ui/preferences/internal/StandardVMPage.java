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
package org.jboss.reddeer.eclipse.jdt.ui.preferences.internal;

import org.jboss.reddeer.core.matcher.WithLabelMatcher;
import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.text.DefaultText;

/**
 * Second page of "Add JRE" wizard accesible from Preferences->Java->Installed
 * JREs.
 * 
 */

public class StandardVMPage extends WizardPage {

	/**
	 * Sets JRE home field.
	 *
	 * @param path the new JRE home
	 */

	public void setJREHome(String path) {
		// no need to check whether file exists, Wizard will do it for us.
		new DefaultText(new WithLabelMatcher("JRE home:")).setText(path);
	}

	/**
	 * Sets name field.
	 *
	 * @param name the new name
	 */

	public void setName(String name) {
		new DefaultText(new WithLabelMatcher("JRE name:")).setText(name);
	}

	/**
	 * Obtains error/warning message from this wizard page.
	 * 
	 * @return error/warning message.
	 */

	public String getErrorMessage() {
		return new DefaultText(new WithLabelMatcher("JRE Definition")).getText();
	}
}
