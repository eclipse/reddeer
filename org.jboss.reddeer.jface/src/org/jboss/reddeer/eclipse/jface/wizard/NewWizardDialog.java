package org.jboss.reddeer.eclipse.jface.wizard;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Abstract class to manage new wizard dialog
 * @author vpakan
 *
 */
public abstract class NewWizardDialog extends WizardDialog{
	public static final String DIALOG_TITLE = "New";
	private String[] path;
	protected final Logger log = Logger.getLogger(this.getClass());
	/**
	 * @param path - path to new object to be created within tree widget 
	 * inside New wizard dialog
	 */
	public NewWizardDialog(String... path) {
		this.path = path;
	}
	/**
	 * Opens wizard for new object to be created
	 */
	public void open(){
		log.info("Open New Wizard");
		super.open();
		Menu menu = new ShellMenu("File","New","Other...");
		menu.select();
		new ActiveShell(DIALOG_TITLE);
		if (path.length > 0){
		  new DefaultTreeItem(path).select();
	    next();
		}
		
	}
}
