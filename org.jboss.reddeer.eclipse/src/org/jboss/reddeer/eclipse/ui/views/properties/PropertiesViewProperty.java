package org.jboss.reddeer.eclipse.ui.views.properties;

import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Implements property of Properties View
 * @author Vlado Pakan
 *
 */
public class PropertiesViewProperty {
	private TreeItem treeItem;
	public PropertiesViewProperty(TreeItem treeItem){
		this.treeItem = treeItem;
	}
	/**
	 * Returns property value
	 * @return
	 */
	public String getPropertyValue(){
		return treeItem.getCell(1);
	}
	/**
	 * Returns property name
	 * @return
	 */
	public String getPropertyName(){
		return treeItem.getCell(0);
	}
	/**
	 * Click on lookup button of property
	 */
	public void clickLookupButton(){
		
	}
	@Override
	public String toString() {
		return getPropertyName() + "=" + getPropertyValue();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getPropertyName() == null) ? 0 : getPropertyName().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertiesViewProperty other = (PropertiesViewProperty) obj;
		if (treeItem == null) {
			if (other.treeItem != null)
				return false;
		} else if (!getPropertyName().equals(other.getPropertyName()))
			return false;
		return true;
	}	
	
}
