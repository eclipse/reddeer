package org.jboss.reddeer.eclipse.wst.server;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;

/**
 * A wizard page of New server runtime wizard for selected the runtime type.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewRuntimeWizardPage extends WizardPage {

	
	public void selectType(String... type){
		Tree t = new DefaultTree();
		t.select(type);
	}
}
