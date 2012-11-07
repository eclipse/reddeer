package org.jboss.reddeer.eclipse.jface.wizard;

import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.ShellTreeItem;

/**
 * Superclass for wizard dialogs that can be opened by the workbench top menu. 
 * It opens the wizard and selects an appropriate wizard in the dialog. 
 *   
 * @author Lucia Jelinkova
 *
 */
public abstract class TopMenuWizardDialog extends WizardDialog {

	private String[] path;
	
	public TopMenuWizardDialog(String... path) {
		if (path.length == 0){
			throw new IllegalArgumentException("Wizard path cannot be empty");
		}
		this.path = path;
	}
	
	protected abstract String getDialogTitle();
	
	protected abstract String[] getMenuPath();
	
	public void open(){
		log.info("Opening wizard using top menu ");
		currentPage = 0;
		new ShellMenu(getMenuPath()).select();
		new DefaultShell(getDialogTitle());
		new ShellTreeItem(path).select();
	    next();
	}
}
