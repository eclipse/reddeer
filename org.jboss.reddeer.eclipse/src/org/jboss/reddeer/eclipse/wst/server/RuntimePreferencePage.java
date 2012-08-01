package org.jboss.reddeer.eclipse.wst.server;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.jface.preference.PreferencePage;
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
	
	public RuntimePreferencePage() {
		super("Server", PAGE_NAME);
	}
	
	public List<Runtime> getServerRuntimes(){
		List<Runtime> runtimes = new ArrayList<Runtime>();
		
		Table table = new DefaultTable();
		
		for (int i = 0; i < table.rowCount(); i++){
			Runtime runtime = new Runtime();
			runtime.setName(table.cell(i, 0));
			runtime.setType(table.cell(i, 1));
			runtimes.add(runtime);
		}
		return runtimes;
	}
	
	public void removeRuntime(Runtime runtime){
		selectRuntime(runtime.getName());
		new PushButton("Remove").click();
	}
	
	public void removeAllRuntimes(){
		for (Runtime runtime : getServerRuntimes()){
			removeRuntime(runtime);
		}
	}
	
	private void selectRuntime(String name){
		
		Table table = new DefaultTable();
		
		for (int i = 0; i < table.rowCount(); i++){
			if (table.cell(i, 0).equals(name)){
				table.select(i);
			}
		}
	}
	
	public NewRuntimeWizardPage addRuntime(){
		new PushButton("Add...").click();
		return new NewRuntimeWizardPage();
	}
}
