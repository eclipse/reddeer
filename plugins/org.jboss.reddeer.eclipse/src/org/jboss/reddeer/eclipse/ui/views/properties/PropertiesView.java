package org.jboss.reddeer.eclipse.ui.views.properties;

import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.toolbar.ViewToolItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;
/**
 * Manages Properties View
 * @author Vlado Pakan
 *
 */
public class PropertiesView extends WorkbenchView{
	public PropertiesView() {
		super("Properties");
	}

	/**
	 * Returns list of Properties
	 * 
	 * @return
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
	 * @param propertyName
	 * @return
	 */
	public PropertiesViewProperty getProperty(String... propertyNamePath){
		open();
		return new PropertiesViewProperty(new DefaultTreeItem(propertyNamePath));
	}
	public void toggleShowCategories(boolean toggle){
		open();
		new ViewToolItem("Show Categories")
			.toggle(toggle);
	}
	public void toggleShowAdvancedProperties(boolean toggle){
		open();
		new ViewToolItem("Show Advanced Properties")
			.toggle(toggle);
	}
}
