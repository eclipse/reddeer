package org.jboss.reddeer.eclipse.wst.server.ui;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.jface.preference.WindowPreferencePage;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewRuntimeWizardDialog;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.table.DefaultTable;

/**
 * 
 * Preference page for managing server runtime environments.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class RuntimePreferencePage extends WindowPreferencePage {

	public static final String PAGE_NAME = "Runtime Environments";

	private static final Logger log = Logger.getLogger(RuntimePreferencePage.class);
	
	public RuntimePreferencePage() {
		super("Server", PAGE_NAME);
	}
	
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
	
	public void removeRuntime(Runtime runtime){
		log.info("Removing runtime " + runtime);
		selectRuntime(runtime.getName());
		new PushButton("Remove").click();
		if(new ShellWithTextIsActive("Server").test()){
			new PushButton("OK").click();
		}
	}
	
	public void removeAllRuntimes(){
		log.info("Removing all runtimes");
		for (Runtime runtime : getServerRuntimes()){
			removeRuntime(runtime);
		}
	}
	
	private void selectRuntime(String name){
		
		Table table = new DefaultTable();
		
		for (int i = 0; i < table.rowCount(); i++){
			if (table.getItem(i).getText().equals(name)){
				table.select(i);
			}
		}
	}
	
	public NewRuntimeWizardDialog addRuntime(){
		log.info("Adding new runtime");
		new PushButton("Add...").click();
		return new NewRuntimeWizardDialog();
	}
}
