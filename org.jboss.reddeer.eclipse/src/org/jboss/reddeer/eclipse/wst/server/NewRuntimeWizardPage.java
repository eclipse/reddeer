package org.jboss.reddeer.eclipse.wst.server;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * A wizard page of New server runtime wizard for selected the runtime type.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewRuntimeWizardPage extends WizardPage {

	
	public void selectType(String... type){
		TreeItem t = new DefaultTreeItem(type);
		t.select();
	}
}
