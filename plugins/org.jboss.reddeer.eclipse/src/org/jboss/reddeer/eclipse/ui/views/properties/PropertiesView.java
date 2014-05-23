package org.jboss.reddeer.eclipse.ui.views.properties;

import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
/**
 * Manages Properties View
 * @author Vlado Pakan
 *
 */
public class PropertiesView extends WorkbenchView{
	
	/**
	 * Constructs the view with "Properties".
	 */
	public PropertiesView() {
		super("Properties");
	}

	/**
	 * Returns list of Properties
	 * 
	 * @return list of Properties
	 */
	public List<PropertiesViewProperty> getProperties(){
		open();
		LinkedList<PropertiesViewProperty> properties = new LinkedList<PropertiesViewProperty>();
		for (TreeItem treeItem : new DefaultTree().getAllItems()){
			properties.add(new PropertiesViewProperty(treeItem));
		}
		return properties;
	}
	
	/**
	 * Returns ViewProperty with propertyName
	 * 
	 * @param propertyName Property name
	 * @return ViewProperty with propertyName
	 */
	public PropertiesViewProperty getProperty(String... propertyNamePath){
		open();
		return new PropertiesViewProperty(new DefaultTreeItem(propertyNamePath));
	}
	
	/**
	 * Sets whether to show categories.
	 * 
	 * @param toggle Indicates whether to show categories
	 */
	public void toggleShowCategories(boolean toggle){
		open();
		new DefaultToolItem("Show Categories")
			.toggle(toggle);
	}


	/**
	 * Sets whether to show advanced properties.
	 * 
	 * @param toggle Indicates whether to show advanced properties
	 */
	public void toggleShowAdvancedProperties(boolean toggle){
		open();
		new DefaultToolItem("Show Advanced Properties")
			.toggle(toggle);
	}
}
