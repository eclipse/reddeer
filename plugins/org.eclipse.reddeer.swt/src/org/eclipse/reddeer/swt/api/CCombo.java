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
 * API for custom combo box manipulation.
 * 
 * @author Andrej Podhradsky
 *
 */
public interface CCombo extends Control<org.eclipse.swt.custom.CCombo> {

	/**
	 * Sets text to the custom combo.
	 * 
	 * @param text
	 *            text which will be set in the custom combo
	 */
	void setText(String text);

	/**
	 * Returns text of the custom combo.
	 * 
	 * @return text of currently selection in the custom combo
	 */
	String getText();

	/**
	 * Returns {@link List} of {@link String}s representing custom combo items
	 * presented in the custom combo.
	 * 
	 * @return {@link List} containing all items of the custom combo
	 */
	List<String> getItems();

	/**
	 * Sets custom combo selection to item with the index.
	 * 
	 * @param index
	 *            index of desired item
	 */
	void setSelection(int index);

	/**
	 * Sets selection of the custom combo to the selection represented by String
	 * value.
	 * 
	 * @param selection
	 *            an item of the custom combo to select
	 */
	void setSelection(String selection);

	/**
	 * Returns selection of the current custom combo selection.
	 * 
	 * @return the text on the current selection
	 */
	String getSelection();

	/**
	 * Returns index of the selected item in the custom combo.
	 * 
	 * @return index of selected custom combo item
	 */
	int getSelectionIndex();

	/**
	 * Finds out whether custom combo is enabled or not.
	 * 
	 * @return true if custom combo is enabled, false otherwise
	 */
	boolean isEnabled();
}
