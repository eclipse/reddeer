package org.jboss.reddeer.eclipse.ui.dialogs;

import org.apache.log4j.Logger;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * First page of New General Project wizard
 * 
 * @author vlado pakan
 * 
 */
public class WizardNewProjectCreationPage extends WizardPage {

	private final Logger log = Logger
			.getLogger(WizardNewProjectCreationPage.class);

	public void setProjectName(String projectName) {
		log.debug("Set General Project name to " + projectName);
		new LabeledText("Project name:").setText(projectName);
	}

	public void setProjectLocation(String projectLocation) {
		log.debug("Set Project location to " + projectLocation);
		new CheckBox("Use default location").toggle(false);
		new LabeledText("Location:").setText(projectLocation);
	}

	public void addProjectToWorkingSet(String workingSet) {
		log.debug("Add Project to working set" + workingSet);
		new CheckBox("Add project to working sets").toggle(true);
		DefaultCombo cmbWorkingSet = new DefaultCombo("Working sets:");
		if (cmbWorkingSet.isEnabled()) {
			cmbWorkingSet.setText(workingSet);
		} else {
			throw new EclipseLayerException(
					"Combo box with Working sets is not enabled."
							+ " Probably no working set is defined");
		}
	}
}
