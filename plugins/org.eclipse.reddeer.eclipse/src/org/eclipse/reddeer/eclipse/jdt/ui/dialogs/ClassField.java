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
package org.eclipse.reddeer.eclipse.jdt.ui.dialogs;

import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * Represents class field in generate hash and equals dialog
 * @author rawagner
 *
 */
public class ClassField {
	
	private TreeItem item;
	
	/**
	 * Instantiates a new class field.
	 *
	 * @param item the item
	 */
	public ClassField(TreeItem item){
		this.item = item;
	}
	
	/**
	 * Returns class field name.
	 *
	 * @return class field name
	 */
	public String getFieldName(){
		return item.getText();
	}
	
	/**
	 * Check/Uncheck class field.
	 *
	 * @param toggle true if class field should be checked, false otherwise
	 */
	public void toggleField(boolean toggle){
		item.setChecked(toggle);
	}
	
	/**
	 * Checks if class field is checked.
	 *
	 * @return true if class field is checked, false otherwise
	 */
	public boolean isFieldChecked(){
		return item.isChecked();
	}

}
