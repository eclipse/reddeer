package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;
import java.util.List;

/**
 * API for combo (combo box) manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Combo extends Widget {

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

	org.eclipse.swt.widgets.Combo getSWTWidget();

}
