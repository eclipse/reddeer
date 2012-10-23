package org.jboss.reddeer.eclipse.ui.ide;

import org.jboss.reddeer.eclipse.jface.wizard.WizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * Represents first page of new file creation wizard dialog
 * 
 * @author jjankovi
 *
 */
public class NewFileCreationWizardPage extends WizardPage {

	protected NewFileCreationWizardPage(WizardDialog wizardDialog) {
		super(wizardDialog, 0);
	}
	
	public void setFileName(String fileName) {
		new LabeledText("File name:").setText(fileName);
	}
	
	public void setFolderPath(String... folderPath) {
		StringBuilder builder = new StringBuilder();
		for (String pathElement : folderPath) {
			builder.append(pathElement);
			builder.append("/");
		}
		new LabeledText("Enter or select the parent folder:").setText(builder.toString());
	}
	
}
