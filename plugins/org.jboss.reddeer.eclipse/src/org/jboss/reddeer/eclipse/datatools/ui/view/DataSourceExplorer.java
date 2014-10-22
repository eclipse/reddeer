package org.jboss.reddeer.eclipse.datatools.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Data Source Explorer.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class DataSourceExplorer extends WorkbenchView {

	public static final String TITLE = "Data Source Explorer";

	/**
	 * Constructs Data Source Explorer view with title {@value #TITLE}.
	 */
	public DataSourceExplorer() {
		super(TITLE);
	}

	/**
	 * Returns list of database connections.
	 * 
	 * @return List of database connections
	 */
	public List<String> getDatabaseConnections() {
		return getItems("Database Connections");
	}

	/**
	 * Returns list of flat file data sources.
	 * 
	 * @return List of flat file data sources
	 */
	public List<String> getFlatFileDataSources() {
		return getItems("ODA Data Sources", "Flat File Data Source");
	}

	/**
	 * Returns list of web service data sources.
	 * 
	 * @return List of web service data sources
	 */
	public List<String> getWebServiceDataSources() {
		return getItems("ODA Data Sources", "Web Service Data Source");
	}

	/**
	 * Returns list of xml data sources.
	 * 
	 * @return List of xml data sources
	 */
	public List<String> getXmlDataSources() {
		return getItems("ODA Data Sources", "XML Data Source");
	}

	protected List<String> getItems(String... path) {
		activate();
		TreeItem root = new DefaultTreeItem(path);
		List<String> list = new ArrayList<String>();
		for (TreeItem treeItem : root.getItems()) {
			list.add(treeItem.getText());
		}
		return list;
	}

}
