package org.jboss.reddeer.swt.api;

import org.eclipse.swt.graphics.Image;


public interface TableItem {
	
	/**
	 * Return the text of table item
	 * 
	 * @return
	 */
	String getText();
	
	/**
	 * Return the state of selection of table item
	 * 
	 * @return
	 */
	boolean isSelected();
	
	
	/**
	 * Select table item
	 */
	void select();
	
	/**
	 * Set is being checked state of table item according to parameter check
	 * 
	 * @param check
	 */
	void setChecked(boolean check);
	
	/**
	 * Return the state of being checked of table item
	 * 
	 * @return
	 */
	boolean isChecked();
	
	Table getParent();
	
	/**
	 * Returns text of table item on given cell index
	 * @param cellIndex
	 * @return
	 */
	String getText(int cellIndex);
	
	/**
	 * Returns SWT TableItem enclosed by this Tree Item
	 * @return
	 */
	org.eclipse.swt.widgets.TableItem getSWTWidget();
	
	/**
	 * Returns Image with given index
	 * @return image with given index
	 */
	Image getImage(int imageIndex);
	
	/**
	 * Checks if table item is grayed
	 * @return true if item is grayed, false otherwise
	 */
	boolean isGrayed();

}
