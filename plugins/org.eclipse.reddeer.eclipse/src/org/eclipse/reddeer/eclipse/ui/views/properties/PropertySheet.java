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
package org.eclipse.reddeer.eclipse.ui.views.properties;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;
/**
 * Manages Properties View
 * 
 * Use this class if you change properties of many elements
 * and you need to be sure, that properties tabs of focused 
 * element are rendered yet.
 * 
 * If rendered tabs doesn't change for 10 seconds,
 * class assume that is still focused the same element and it is ok.
 * 
 * @author Vlado Pakan, jomarko@redhat.com
 *
 */
public class PropertySheet extends WorkbenchView{
	
	/**
	 * Constructs the view with "Properties".
	 */
	public PropertySheet() {
		super("Properties");
	}

	/**
	 * Returns list of Properties.
	 *
	 * @return list of Properties
	 */
	public List<PropertySheetProperty> getProperties(){
		activate();
		LinkedList<PropertySheetProperty> properties = new LinkedList<PropertySheetProperty>();
		for (TreeItem treeItem : new DefaultTree(cTabItem).getAllItems()){
			properties.add(new PropertySheetProperty(treeItem));
		}
		return properties;
	}
	
	/**
	 * Returns ViewProperty with propertyName.
	 *
	 * @param propertyNamePath the property name path
	 * @return ViewProperty with propertyName
	 */
	public PropertySheetProperty getProperty(String... propertyNamePath){
		activate();
		return new PropertySheetProperty(new DefaultTreeItem(new DefaultTree(cTabItem), propertyNamePath));
	}
	
	/**
	 * Sets whether to show categories.
	 * 
	 * @param toggle Indicates whether to show categories
	 */
	public void toggleShowCategories(boolean toggle){
		activate();
		new DefaultToolItem(cTabItem.getFolder(), "Show Categories")
			.toggle(toggle);
	}


	/**
	 * Sets whether to show advanced properties.
	 * 
	 * @param toggle Indicates whether to show advanced properties
	 */
	public void toggleShowAdvancedProperties(boolean toggle){
		activate();
		new DefaultToolItem(cTabItem.getFolder(), "Show Advanced Properties")
			.toggle(toggle);
	}
	
	/**
	 * Selects tab with a given label.
	 * 
	 * @param label Label
	 */
	public void selectTab(String label) {
		activate();
		List<String> old = new ArrayList<String>();
		
		try{
			old = new TabbedPropertyList(cTabItem, 0).getTabs();
		}catch(Exception ex) {
			//probably not rendered yet
		}
		if (!old.contains(label)) {
			new WaitUntil(new AnotherTabsRendered(old), TimePeriod.DEFAULT, false);	
		}
		
		new TabbedPropertyList(cTabItem, 0).selectTab(label);
	}
	
	private class AnotherTabsRendered extends AbstractWaitCondition {

		private List<String> old;
		
		public AnotherTabsRendered(List<String> old) {
			this.old = old;
		}
		
		@Override
		public boolean test() {
			List<String> actual = new ArrayList<String>();
			
			try{
				actual = new TabbedPropertyList(cTabItem, 0).getTabs();
			}catch(Exception ex) {
				//probably not rendered yet
			}
			
			return !actual.equals(old);
		}

		@Override
		public String description() {
			return "Wait for tabs of focused element to be rendered";
		}
		
	}
}
