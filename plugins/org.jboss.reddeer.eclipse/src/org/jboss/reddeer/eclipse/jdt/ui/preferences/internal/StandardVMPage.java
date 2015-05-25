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
	 * @param path
	 */

	public void setJREHome(String path) {
		// no need to check whether file exists, Wizard will do it for us.
		new DefaultText(new WithLabelMatcher("JRE home:")).setText(path);
	}

	/**
	 * Sets name field.
	 * 
	 * @param name
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
