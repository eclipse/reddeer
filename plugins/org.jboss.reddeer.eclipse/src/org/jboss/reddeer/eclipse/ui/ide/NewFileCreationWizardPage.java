package org.jboss.reddeer.eclipse.ui.ide;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * Represents first page of new file creation wizard dialog
 * 
 * @author jjankovi
 *
 */
public class NewFileCreationWizardPage extends WizardPage {

	public NewFileCreationWizardPage() {
		super();
	}
	
	/**
	 * Sets a given file name.
	 * 
	 * @param fileName File name
	 */
	public void setFileName(String fileName) {
		new LabeledText("File name:").setText(fileName);
	}
	
	/**
	 * Sets a given folder path.
	 * 
	 * @param folderPath Folder path
	 */
	public void setFolderPath(String... folderPath) {
		StringBuilder builder = new StringBuilder();
		for (String pathElement : folderPath) {
			builder.append(pathElement);
			builder.append("/");
		}
		new LabeledText("Enter or select the parent folder:").setText(builder.toString());
	}
	
}
