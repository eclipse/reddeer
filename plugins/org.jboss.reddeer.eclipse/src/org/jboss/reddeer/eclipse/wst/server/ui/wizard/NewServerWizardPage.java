package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Represents the first page displayed when invoked {@link NewServerWizardDialog}
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewServerWizardPage extends WizardPage {

	public void selectType(String... type){
		TreeItem t = new DefaultTreeItem(type);
		t.select();
	}

	public void setName(String name){
		new LabeledText("Server name:").setText(name);
	}

	public void setHostName(String name){
		new LabeledText("Server's host name:").setText(name);
	}
}
