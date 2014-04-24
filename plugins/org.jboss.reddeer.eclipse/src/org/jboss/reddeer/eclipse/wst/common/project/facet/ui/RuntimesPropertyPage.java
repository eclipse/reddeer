package org.jboss.reddeer.eclipse.wst.common.project.facet.ui;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.Project;
import org.jboss.reddeer.eclipse.ui.dialogs.ProjectPropertyPage;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.table.DefaultTableItem;
import org.jboss.reddeer.swt.matcher.CheckedTableItemMatcher;

/**
 * "Targeted runtimes" property page. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RuntimesPropertyPage extends ProjectPropertyPage {

	public static final String NAME = "Targeted Runtimes"; 
	
	public RuntimesPropertyPage(Project project) {
		super(project, NAME);
	}
	
	/**
	 * Selects the given runtime. 
	 * 
	 * @param runtimeName
	 */
	public void selectRuntime(String runtimeName){
		new DefaultTableItem(runtimeName).setChecked(true);
	}
	
	/**
	 * Returns names of all selected runtimes
	 * @return
	 */
	public List<String> getSelectedRuntimes(){
		List<String> runtimes = new ArrayList<String>();
		for (TableItem tableItem : new DefaultTable().getItems(new CheckedTableItemMatcher())){
			runtimes.add(tableItem.getText());
		}
		return runtimes;
	}
}
