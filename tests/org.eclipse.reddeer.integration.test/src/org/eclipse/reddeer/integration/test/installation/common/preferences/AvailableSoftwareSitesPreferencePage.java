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
package org.eclipse.reddeer.integration.test.installation.common.preferences;

import java.util.List;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;

/**
 * Represents a Install/Update -> Available Update Sites preference page.
 * 
 * @author Ondrej Dockal
 */
public class AvailableSoftwareSitesPreferencePage extends PreferencePage {

	public AvailableSoftwareSitesPreferencePage(ReferencedComposite referencedComposite) {
		super(referencedComposite, new String[] {"Install/Update", "Available Software Sites"});
	}
	
	public List<TableItem> getItems() {
		return new DefaultTable().getItems();
	}
	
	public TableItem getItem(String name) {
		for(TableItem item : getItems()) {
			if(item.getText(0).equals(name)) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * If alternate search is set true, the method will search in Address column instead of Name column
	 * @param url
	 * @param alternateSearch
	 * @return specified TableItem
	 */
	public TableItem getItem(String url, boolean alternateSearch) {
		if(!alternateSearch) {
			return getItem(url);
		} else {
			for(TableItem item : getItems()) {
				if(item.getText(1).equals(url)) {
					return item;
					}
				}
			}
		return null;
	}
	
	public void toggleAllItems(boolean toggle) {
		for(TableItem item : getItems()) {
			item.setChecked(toggle);
		}
	}
	
	public void toggleItem(String name, boolean toggle) {
		getItem(name).setChecked(toggle);
	}
	
	/**
	 * If alternateSearch is set true, the method will search in Address column instead of Name column
	 * @param url
	 * @param toggle
	 * @param alternateSearch
	 */
	public void toggleItem(String url, boolean toggle, boolean alternateSearch) {
		getItem(url, alternateSearch).setChecked(toggle);
	}
	
	public void selectItem(String name) {
		getItem(name).click();
	}
	
	/**
	 * If alternateSearch is set true, the method will search in Address column instead of Name column
	 * @param url
	 * @param toggle
	 * @param alternateSearch
	 */
	public void selectItem(String url, boolean alternateSearch) {
		getItem(url, alternateSearch).click();
	}
	
	public boolean isItemEnabled(String name) {
		return getItem(name).getText(2).equals("Enabled");
	}
	
	/**
	 * If alternateSearch is set true, the method will search in Address column instead of Name column
	 * @param url
	 * @param alternateSearch
	 * @return True if item in table is enabled
	 */
	public boolean isItemEnabled(String url, boolean alternateSearch) {
		return getItem(url, alternateSearch).getText(2).equals("Enabled");
	}
	
	public boolean isButtonEnabled(String buttonName) {
		return new PushButton(buttonName).isEnabled();
	}
	
	public void clickAdd() {
		new PushButton("Add...").click();
	}
	
	public void clickEdit() {
		new PushButton("Edit").click();
	}
	
	public void clickRemove() {
		new PushButton("Remove").click();
	}
	
	public void clickReload() {
		new PushButton("Reload").click();
	}
	
	public void clickEnable() {
		new PushButton("Enable").click();
	}
	
	public void clickDisable() {
		new PushButton("Disable").click();
	}
	
	public void clickImport() {
		new PushButton("Import...").click();
	}
	
	public void clickExport() {
		new PushButton("Export...").click();
	}
	
}
