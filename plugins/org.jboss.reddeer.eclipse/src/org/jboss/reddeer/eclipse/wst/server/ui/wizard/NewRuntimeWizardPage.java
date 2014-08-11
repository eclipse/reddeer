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
	 * A wizard page should not know on which page index it is displayed. The
	 * wizard page can also exist outside WizardDialog. Use no-argument
	 * constructor instead.
	 * 
	 * @param wizardDialog
	 * @param pageIndex
	 */
	@Deprecated
	public NewRuntimeWizardPage(NewRuntimeWizardDialog wizardDialog){
		super(wizardDialog,0);
	}
	
	public NewRuntimeWizardPage(){
		super();
	}
	
	public void selectType(String... type){
		TreeItem t = new DefaultTreeItem(type);
		t.select();
	}
}
