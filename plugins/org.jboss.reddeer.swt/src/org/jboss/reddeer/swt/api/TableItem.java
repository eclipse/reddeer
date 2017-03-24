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
package org.jboss.reddeer.swt.api;

import org.eclipse.swt.graphics.Image;

/**
 * API for table item manipulation.
 * 
 * @author  Jiri Peterka
 *
 */
public interface TableItem extends Item<org.eclipse.swt.widgets.TableItem> {

	/**
	 * Finds out whether the table item is selected or not.
	 * 
	 * @return true if the table item is selected, false otherwise
	 */
	boolean isSelected();

	/**
	 * Selects the table item.
	 */
	void select();

	/**
	 * Sets checked on the table item.
	 * 
	 * @param check boolean value whether check the table item or not
	 */
	void setChecked(boolean check);

	/**
	 * Finds out whether the table item is checked or not.
	 * 
	 * @return true if the table item is checked, false otherwise
	 */
	boolean isChecked();

	/**
	 * Gets parent of the table item. Parent is RedDeer implementation of table.
	 * 
	 * @return parent table of the table item
	 */
	Table getParent();

	/**
	 * Returns text of the table item on a position defined by the cell index.
	 * 
	 * @param cellIndex cell position index
	 * @return text of the table item on the specific position
	 */
	String getText(int cellIndex);

	/**
	 * Returns image on the position defined by the image index.
	 * 
	 * @param imageIndex image position index
	 * @return image on the specific position
	 */
	Image getImage(int imageIndex);

	/**
	 * Checks whether table item is grayed or not.
	 * 
	 * @return true if the table item is grayed, false otherwise
	 */
	boolean isGrayed();

	/**
	 * Clicks twice on the table item.
	 */
	void doubleClick();
	
	/**
	 * Click on the table item.
	 */
	void click();

	/**
	 * Click on the table item.
	 * 
	 * @param column to click on
	 */
	void click(int column);
	
	/**
	 * Clicks twice on specified column in the table item.
	 * 
	 * @param column to click on
	 */
	void doubleClick(int column);
}
