package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Represents the first page displayed when invoked {@link NewRuntimeWizardDialog}
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewRuntimeWizardPage extends WizardPage {
  
	/**
	 * Instantiates a new new runtime wizard page.
	 */
	public NewRuntimeWizardPage(){
		super();
	}
	
	/**
	 * Select type.
	 *
	 * @param type the type
	 */
	public void selectType(String... type){
		TreeItem t = new DefaultTreeItem(type);
		t.select();
	}
}
