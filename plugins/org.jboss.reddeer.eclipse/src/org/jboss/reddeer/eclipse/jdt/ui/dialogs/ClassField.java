/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.jdt.ui.dialogs;

import org.jboss.reddeer.swt.api.TreeItem;

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
