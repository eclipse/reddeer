/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.wst.common.project.facet.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.table.DefaultTableItem;
import org.eclipse.reddeer.swt.matcher.CheckedTableItemMatcher;

/**
 * "Targeted runtimes" property page. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RuntimesPropertyPage extends PropertyPage {

	public static final String NAME = "Targeted Runtimes"; 

	private static final Logger log = Logger.getLogger(RuntimesPropertyPage.class);
	
	/**
	 * Constructs a new Runtimes property page.
	 */
	public RuntimesPropertyPage() {
		super(NAME);
	}
	
	/**
	 * Selects the given runtime. 
	 *
	 * @param runtimeName the runtime name
	 */
	public void selectRuntime(String runtimeName){
		log.info("Select runtime '" + runtimeName + "'");
		new DefaultTableItem(runtimeName).setChecked(true);
	}
	
	/**
	 * Returns names of all selected runtimes.
	 *
	 * @return the selected runtimes
	 */
	public List<String> getSelectedRuntimes(){
		List<String> runtimes = new ArrayList<String>();
		for (TableItem tableItem : new DefaultTable().getItems(new CheckedTableItemMatcher())){
			runtimes.add(tableItem.getText());
		}
		return runtimes;
	}
}
