package org.jboss.reddeer.eclipse.jdt.ui.dialogs;

import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Represents class field in generate hash and equals dialog
 * @author rawagner
 *
 */
public class ClassField {
	
	private TreeItem item;
	
	public ClassField(TreeItem item){
		this.item = item;
	}
	
	/**
	 * Returns class field name
	 * @return class field name
	 */
	public String getFieldName(){
		return item.getText();
	}
	
	/**
	 * Check/Uncheck class field
	 * @param toggle true if class field should be checked, false otherwise
	 */
	public void toggleField(boolean toggle){
		item.setChecked(toggle);
	}
	
	/**
	 * Checks if class field is checked
	 * @return true if class field is checked, false otherwise
	 */
	public boolean isFieldChecked(){
		return item.isChecked();
	}

}
