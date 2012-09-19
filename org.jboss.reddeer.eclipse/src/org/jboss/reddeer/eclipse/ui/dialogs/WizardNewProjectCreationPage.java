package org.jboss.reddeer.eclipse.ui.dialogs;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.jboss.reddeer.swt.exception.WidgetNotEnabledException;
import org.jboss.reddeer.swt.impl.combo.ComboWithLabel;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.util.Bot;

/**
 * First page of New General Project wizard
 * 
 * @author vpakan
 * 
 */
public class WizardNewProjectCreationPage extends WizardPage {

	private final Logger log = Logger
			.getLogger(WizardNewProjectCreationPage.class);

	public WizardNewProjectCreationPage(
			BasicNewProjectResourceWizard wizardDialog) {
		super(wizardDialog, 1);
	}

	public void setProjectName(String projectName) {
		show();
		log.debug("Set General Project name to " + projectName);
		new LabeledText("Project name:").setText(projectName);
	}

	public void setProjectLocation(String projectLocation) {
		show();
		log.debug("Set Project location to " + projectLocation);
		SWTBotCheckBox chbLocation = Bot.get().checkBox("Use default location");
		if (chbLocation.isChecked()) {
			chbLocation.click();
		}
		new LabeledText("Location:").setText(projectLocation);
	}

	public void addProjectToWorkingSet(String workingSet) {
		show();
		log.debug("Add Project to working set" + workingSet);
		SWTBotCheckBox chbAddProjectToWorkingSet = Bot.get().checkBox(
				"Add project to working sets");
		if (!chbAddProjectToWorkingSet.isChecked()) {
			chbAddProjectToWorkingSet.click();
		}
		ComboWithLabel cmbWorkingSet = new ComboWithLabel("Working sets:");
		if (cmbWorkingSet.isEnabled()) {
			cmbWorkingSet.setText(workingSet);
		} else {
			throw new WidgetNotEnabledException(
					"Combo box with Working sets is not enabled."
							+ " Probably no working set is defined");
		}
	}
}
