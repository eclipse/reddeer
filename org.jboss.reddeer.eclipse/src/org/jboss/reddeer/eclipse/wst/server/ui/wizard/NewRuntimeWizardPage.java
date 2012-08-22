package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Represents the first page displayed when invoked {@link NewRuntimeWizard}
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewRuntimeWizardPage extends WizardPage {
  
	public NewRuntimeWizardPage(NewRuntimeWizard wizardDialog){
		super(wizardDialog,0);
	}
	
	public void selectType(String... type){
		show();
		TreeItem t = new DefaultTreeItem(type);
		t.select();
	}
}
