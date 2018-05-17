/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.datatools.connectivity.ui.dse.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Data Source Explorer.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class DataSourceExplorerView extends WorkbenchView {

	public static final String TITLE = "Data Source Explorer";

	/**
	 * Constructs Data Source Explorer view with title {@value #TITLE}.
	 */
	public DataSourceExplorerView() {
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

	/**
	 * Gets the items.
	 *
	 * @param path the path
	 * @return the items
	 */
	protected List<String> getItems(String... path) {
		activate();
		TreeItem root = new DefaultTreeItem(new DefaultTree(cTabItem),path);
		List<String> list = new ArrayList<String>();
		for (TreeItem treeItem : root.getItems()) {
			list.add(treeItem.getText());
		}
		return list;
	}

}
