package org.jboss.reddeer.eclipse.ui.views.properties;

import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Implements property of Properties View
 * @author Vlado Pakan
 *
 */
public class PropertiesViewProperty {
	private TreeItem treeItem;
	
	/**
	 * Instantiates a new properties view property.
	 *
	 * @param treeItem the tree item
	 */
	public PropertiesViewProperty(TreeItem treeItem){
		this.treeItem = treeItem;
	}
	
	/**
	 * Returns property value.
	 *
	 * @return the property value
	 */
	public String getPropertyValue(){
		return treeItem.getCell(1);
	}
	
	/**
	 * Returns property name.
	 *
	 * @return the property name
	 */
	public String getPropertyName(){
		return treeItem.getCell(0);
	}
	
	/**
	 * Returns RedDeer TreeItem representing this property.
	 *
	 * @return the tree item
	 */
	public TreeItem getTreeItem(){
		return this.treeItem;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getPropertyName() + "=" + getPropertyValue();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getPropertyName() == null) ? 0 : getPropertyName().hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
