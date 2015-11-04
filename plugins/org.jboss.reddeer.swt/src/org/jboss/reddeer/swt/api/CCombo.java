package org.jboss.reddeer.swt.api;

import java.util.List;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for custom combo box manipulation.
 * 
 * @author Andrej Podhradsky
 *
 */
public interface CCombo extends Widget {

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

	org.eclipse.swt.custom.CCombo getSWTWidget();

}
