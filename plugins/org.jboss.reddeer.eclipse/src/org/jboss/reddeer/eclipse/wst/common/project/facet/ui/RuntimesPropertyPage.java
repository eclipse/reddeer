package org.jboss.reddeer.eclipse.wst.common.project.facet.ui;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.eclipse.core.resources.Project;
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

	private static final Logger log = Logger.getLogger(RuntimesPropertyPage.class);
	
	/**
	 * Constructs the property page with a given project and {@value #NAME}.
	 * @deprecated Please use {@link ExplorerItemPropertyDialog}
	 * @param project Project name
	 */
	public RuntimesPropertyPage(Project project) {
		super(project, NAME);
	}
	
	public RuntimesPropertyPage() {
		super(NAME);
	}
	
	/**
	 * Selects the given runtime. 
	 * 
	 * @param runtimeName
	 */
	public void selectRuntime(String runtimeName){
		log.info("Select runtime '" + runtimeName + "'");
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
