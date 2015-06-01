package org.jboss.reddeer.eclipse.wst.server.ui;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardDialog;
import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.table.DefaultTable;

/**
 * 
 * Preference page for managing server runtime environments.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class RuntimePreferencePage extends PreferencePage {

	public static final String PAGE_NAME = "Runtime Environments";
	
	public static final String EDIT_BUTTON_NAME = "Edit...";

	private static final Logger log = Logger.getLogger(RuntimePreferencePage.class);
	
	/**
	 * Constructs the preference page with "Server" > {@value #PAGE_NAME}.
	 */
	public RuntimePreferencePage() {
		super(new String[] {"Server", PAGE_NAME});
	}
	
	/**
	 * Return list of server runtimes.
	 * 
	 * @return List of server runtimes
	 */
	public List<Runtime> getServerRuntimes(){
		List<Runtime> runtimes = new ArrayList<Runtime>();
		
		Table table = new DefaultTable();
		int rows = table.rowCount();
		
		for (int i = 0; i < rows; i++){
			Runtime runtime = new Runtime();
			runtime.setName(table.getItem(i).getText());
			runtime.setType(table.getItem(i).getText(1));
			runtimes.add(runtime);
		}
		return runtimes;
	}
	
	/**
	 * Removes a given runtime.
	 * 
	 * @param runtime Runtime
	 */
	public void removeRuntime(Runtime runtime){
		log.info("Removing runtime '" + runtime + "'");
		selectRuntime(runtime.getName());
		new PushButton("Remove").click();
		if(new ShellWithTextIsActive("Server").test()){
			new PushButton("OK").click();
		}
	}
	
	/**
	 * Removes all runtimes.
	 */
	public void removeAllRuntimes(){
		log.info("Removing all runtimes");
		for (Runtime runtime : getServerRuntimes()){
			removeRuntime(runtime);
		}
	}
	
	private void selectRuntime(String name){
		
		Table table = new DefaultTable();
		log.debug("Selecting runtime '" + name + "'");
		for (int i = 0; i < table.rowCount(); i++){
			String runtimeName = table.getItem(i).getText();
			log.debug("'" + runtimeName + "' was found");
			if (runtimeName.equals(name)){
				log.debug("'" + runtimeName + "' matched '" + name + "'! Selecting...");
				table.select(i);
				return;
			}
		}
		throw new EclipseLayerException("Unable to find runtime "+name);
	}
	
	/**
	 * Opens a wizard for adding new runtime.
	 * 
	 * @return Wizard for adding new runtime
	 */
	public NewRuntimeWizardDialog addRuntime(){
		log.info("Adding new runtime");
		new PushButton("Add...").click();
		return new NewRuntimeWizardDialog();
	}
	
	/**
	 * Opens tie runtime's edit dialog. Since the dialog is specific for every runtime type
	 * it is upon user to instantiate the concrete Red Deer dialog. 
	 * @param name
	 */
	public void editRuntime(String name){
		selectRuntime(name);
		new PushButton(EDIT_BUTTON_NAME).click();
	}
}
