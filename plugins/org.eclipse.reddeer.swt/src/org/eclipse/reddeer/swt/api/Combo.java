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
package org.eclipse.reddeer.swt.api;

import java.util.List;

/**
 * API for combo (combo box) manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Combo extends Control<org.eclipse.swt.widgets.Combo> {

	/**
	 * Sets text to the combo.
	 * 
	 * @param text text which will be set in the combo
	 */
	void setText(String text);

	/**
	 * Returns text of the combo.
	 * 
	 * @return text of currently selection in the combo
	 */
	String getText();

	/**
	 * Returns {@link List} of {@link String}s representing
	 * combo items presented in the combo.
	 * 
	 * @return {@link List} containing all items of the combo
	 */
	List<String> getItems();

	/**
	 * Sets combo selection to item with the index.
	 * 
	 * @param index index of desired item
	 */
	void setSelection(int index);

	/**
	 * Sets selection of the combo to the selection represented by String value.
	 * 
	 * @param selection of the combo to select
	 */
	void setSelection(String selection);

	/**
	 * Returns selection of the current combo selection.
	 * 
	 * @return the text on the current selection
	 */
	String getSelection();

	/**
	 * Returns index of the selected item in the combo.
	 * 
	 * @return index of selected combo item 
	 */
	int getSelectionIndex();

	/**
	 * Finds out whether combo is enabled or not.
	 * 
	 * @return true if combo is enabled, false otherwise
	 */
	boolean isEnabled();
}
