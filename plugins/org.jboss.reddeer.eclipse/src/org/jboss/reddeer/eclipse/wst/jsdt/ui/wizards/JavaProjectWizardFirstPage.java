package org.jboss.reddeer.eclipse.wst.jsdt.ui.wizards;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * Represents the first page displayed when invoked {@link JavaProjectWizardDialog}
 * 
 * @author Pavol Srna
 *
 */
public class JavaProjectWizardFirstPage  extends WizardPage {

	/**
	 * Sets project name.
	 *
	 * @param name the new name
	 */
	public void setName(String name){
		new LabeledText("Project name:").setText(name);
	}
}
