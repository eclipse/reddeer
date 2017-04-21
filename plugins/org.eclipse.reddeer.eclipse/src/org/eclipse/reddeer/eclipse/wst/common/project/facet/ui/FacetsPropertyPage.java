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

import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.menu.ContextMenu;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * "Project Facets" property page. 
 * @author Pavol Srna
 * 
 */
public class FacetsPropertyPage extends PropertyPage {
	
	public static final String NAME = "Project Facets"; 
	
	/**
	 * Constructs a new Facets property page.
	 */
	public FacetsPropertyPage() {
		super(NAME);
	}
	
	/**
	 * Selects the given facet. 
	 *
	 * @param facetName the facet name
	 */
	public void selectFacet(String facetName){
		new DefaultTreeItem(new DefaultTree(1), facetName).setChecked(true);
	}
	
	/**
	 * Returns selected facet version.
	 *
	 * @param facetName the facet name
	 * @return version
	 */
	public String getSelectedVersion(String facetName){
		return new DefaultTreeItem(new DefaultTree(1), facetName).getCell(1);
	}
	
	/**
	 * Selects facet version.
	 *
	 * @param facetName the facet name
	 * @param version the version
	 */
	public void selectVersion(String facetName, String version){
		TreeItem facet = new DefaultTreeItem(new DefaultTree(1), facetName);
		facet.select();
		new ContextMenu("Change Version...").select();
		new DefaultShell("Change Version");
		new LabeledCombo("Version:").setSelection(version);
		new OkButton().click();
	}
	
	/**
	 * Returns names of all selected facets.
	 *
	 * @return the selected facets
	 */
	public List<TreeItem> getSelectedFacets(){
		List<TreeItem> facets = new ArrayList<TreeItem>();
		for(TreeItem i : new DefaultTree(1).getItems()){
			if(i.isChecked())
				facets.add(i);
		}
		return facets;
	}
	
	/**
	 * Apply preference page changes.
	 */
	public void apply() {
		super.apply();
		new WaitWhile(new ShellIsAvailable("Progress Information"));
	}
	
	
}
