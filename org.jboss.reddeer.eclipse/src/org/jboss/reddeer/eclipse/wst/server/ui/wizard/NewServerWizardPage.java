package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import org.jboss.reddeer.eclipse.jface.wizard.WizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.ShellTreeItem;

/**
 * Represents the first page displayed when invoked {@link NewServerWizardDialog}
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewServerWizardPage extends WizardPage {

	public NewServerWizardPage(WizardDialog parentDialog) {
		super(parentDialog, 0);
	}

	public void selectType(String... type){
		show();	
		TreeItem t = new ShellTreeItem(type);
		t.select();
	}

	public void setName(String name){
		new LabeledText("Server name:").setText(name);
	}

	public void setHostName(String name){
		new LabeledText("Server's host name:").setText(name);
	}
}
