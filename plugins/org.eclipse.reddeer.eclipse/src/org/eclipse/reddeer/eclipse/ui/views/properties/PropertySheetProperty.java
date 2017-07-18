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

import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * Implements property of Properties View
 * @author Vlado Pakan
 *
 */
public class PropertySheetProperty {
	private TreeItem treeItem;
	
	/**
	 * Instantiates a new properties view property.
	 *
	 * @param treeItem the tree item
	 */
	public PropertySheetProperty(TreeItem treeItem){
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
		PropertySheetProperty other = (PropertySheetProperty) obj;
		if (treeItem == null) {
			if (other.treeItem != null)
				return false;
		} else if (!getPropertyName().equals(other.getPropertyName()))
			return false;
		return true;
	}	
	
}
