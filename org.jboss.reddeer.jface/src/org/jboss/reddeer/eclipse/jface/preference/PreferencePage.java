package org.jboss.reddeer.eclipse.jface.preference;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.swt.impl.menu.DefaultMenu;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Represents a general preference page in the Preferences dialog. Subclasses should represent the concrete preference page. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class PreferencePage {

	public static final String DIALOG_TITLE = "Preferences";
	
	private String[] path;
	protected final Logger log = Logger.getLogger(this.getClass());
	
	public PreferencePage(String... path) {
		this.path = path;
	}
	
	public void open(){

		// if preferences dialog is not open, open it
		log.info("Open Preferences dialog");	
		try {
			new ActiveShell(DIALOG_TITLE);
			log.debug("Preferences dialog was already opened.");
		} catch (WidgetNotAvailableException e) {
			log.debug("Preferences dialog was not already opened. Opening via menu.");
			Menu menu = new DefaultMenu("Window","Preferences");
			menu.select();
			new ActiveShell(DIALOG_TITLE);
		}
		
		if (log.isDebugEnabled()){
			  StringBuffer sbPath = new StringBuffer("");
			  for (String pathItem : path){
			    if (sbPath.length() > 0 ){
			      sbPath.append(" > ");
			    }
			    sbPath.append(pathItem);
			  }
	        log.debug("Select Preferences dialog treeitem: " + sbPath.toString());
		}   
		TreeItem t = new DefaultTreeItem(path);
		t.select();
		
	}
	
	public String getName(){
		DefaultCLabel cl = new DefaultCLabel();
		return cl.getText();
	}
	
	public void ok(){		
		Button b = new PushButton("OK");
		log.info("Close Preferences dialog");
		b.click();		
	}

	public void cancel(){
		Button b = new PushButton("Cancel");
		log.info("Cancel Preferences dialog");
		b.click();		
	}
	
	public void apply(){
		Button b = new PushButton("Apply");
		log.debug("Apply changes in Preferences dialog");
		b.click();		
	}
	
	public void restoreDefaults(){
		Button b = new PushButton("Restore Defaults");
		log.debug("Restore default values in Preferences dialog");
		b.click();
	}
}

