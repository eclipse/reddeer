package org.jboss.reddeer.gef.test.wizard;


import org.jboss.reddeer.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class GeneralProjectWizard extends NewWizardDialog {

	public GeneralProjectWizard() {
		super("General", "Project");
	}

	public void create(String name) {
		open();
		new LabeledText("Project name:").setText(name);
		finish();
	}
}
