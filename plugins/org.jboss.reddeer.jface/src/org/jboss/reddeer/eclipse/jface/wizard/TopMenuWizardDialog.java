package org.jboss.reddeer.eclipse.jface.wizard;

import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Superclass for wizard dialogs that can be opened by the workbench top menu. 
 * It opens the wizard and selects an appropriate wizard in the dialog. 
 *   
 * @author Lucia Jelinkova
 * @since 0.5
 *
 */
public abstract class TopMenuWizardDialog extends WizardDialog {

	private String[] path;
	
	/**
	 * Constructor set path in wizard dialog for specific item.
	 * @param path to the specific wizard dialog item in dialog shell
	 * @throws IllegalArgumentException when path is empty
	 */
	public TopMenuWizardDialog(String... path) {
		if (path.length == 0){
			throw new IllegalArgumentException("Wizard path cannot be empty");
		}
		this.path = path;
	}
	
	/**
	 * Get dialog title on the given wizard dialog.
	 * @return title of wizard dialog
	 */
	protected abstract String getDialogTitle();
	
	/**
	 * Get path to the given wizard dialog from shell menu.
	 * @return path to the specific wizard dialog
	 */
	protected abstract String[] getMenuPath();
	
	/**
	 * Open wizard dialog defined by menu path and select the specific item.
	 */
	public void open(){
		log.info("Opening wizard using top menu ");
		currentPage = -1;
		new ShellMenu(getMenuPath()).select();
		new DefaultShell(getDialogTitle());
		new DefaultTreeItem(path).select();
	    next();
	}
}
