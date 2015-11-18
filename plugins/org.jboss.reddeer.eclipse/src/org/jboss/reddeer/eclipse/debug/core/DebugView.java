package org.jboss.reddeer.eclipse.debug.core;

import java.util.List;

import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Debug View
 * 
 * @author Andrej Podhradsky
 *
 */
public class DebugView extends WorkbenchView {

	/**
	 * Instantiates a new debug view.
	 */
	public DebugView() {
		super("Debug");
	}

	/**
	 * Gets a text of a selected tree item.
	 * 
	 * @return text of a selected tree item
	 */
	public String getSelectedText() {
		TreeItem selectedItem = getSelectedItem();
		if (selectedItem == null) {
			return null;
		}
		return selectedItem.getText();
	}

	/**
	 * Gets a selected tree item.
	 * 
	 * @return selected tree item
	 */
	public TreeItem getSelectedItem() {
		open();
		List<TreeItem> selectedItems = new DefaultTree().getSelectedItems();
		if (selectedItems.isEmpty()) {
			return null;
		}
		return selectedItems.get(0);
	}

}
