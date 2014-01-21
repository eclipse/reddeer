package org.jboss.reddeer.eclipse.wst.server.ui.wizard;


import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Wizard page for adding and removing modules on the server. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ModifyModulesPage extends WizardPage {

	public void add(String... projectNames){
		for (String project : projectNames){
			new DefaultTreeItem(0, project).select();
			new PushButton("Add >").click();
		}
	}

	public void addAll(){
		new PushButton("Add All >>").click();
	}

	public void remove(String... projectNames){
		for (String project : projectNames){
			new DefaultTreeItem(1, project).select();
			new PushButton("< Remove").click();
		}
	}

	public void removeAll(){
		new PushButton("<< Remove All").click();
	}
}
