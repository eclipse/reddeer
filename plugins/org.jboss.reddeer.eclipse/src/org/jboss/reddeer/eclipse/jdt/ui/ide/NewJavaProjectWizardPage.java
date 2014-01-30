package org.jboss.reddeer.eclipse.jdt.ui.ide;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * Represents first page of New Java Project Wizard
 * 
 * @author rhopp
 *
 */

public class NewJavaProjectWizardPage extends WizardPage {
	
	public void setProjectName (String projectName){
	    log.debug("Set General Project name to " + projectName);
	    new LabeledText("Project name:").setText(projectName);
	}
	
	public void useDefaultLocation(boolean check){
		CheckBox box = new CheckBox("Use default location");
		log.debug("Setting default location to "+check);
		box.toggle(check);
	}
	
	public void setLocation(String location){
		log.debug("Setting Location to "+location);
		LabeledText text = new LabeledText("Location:");
		text.setText(location);
	}

}
