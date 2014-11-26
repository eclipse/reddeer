package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Wizard page for adding and removing modules on the server.
 * 
 * @author Lucia Jelinkova
 * @author Radoslav Rabara
 */
public class ModifyModulesPage extends WizardPage {

	/**
	 * Adds modules with the specified names
	 * 
	 * @param projectNames names of the modules to be added
	 */
	public void add(String... projectNames) {
		for (String project : projectNames) {
			new DefaultTreeItem(new DefaultTree(), project).select();
			new PushButton("Add >").click();
		}
	}

	/**
	 * Adds all available modules to the server
	 * 
	 * @see {@link #getAvailableModules()}
	 */
	public void addAll() {
		new PushButton("Add All >>").click();
	}

	/**
	 * Removes modules with the specified names
	 * 
	 * @param projectNames names of the modules to be removed from the server
	 */
	public void remove(String... projectNames) {
		for (String project : projectNames) {
			new DefaultTreeItem(new DefaultTree(1), project).select();
			new PushButton("< Remove").click();
		}
	}

	/**
	 * Removes all modules from the server
	 */
	public void removeAll() {
		new PushButton("<< Remove All").click();
	}

	/**
	 * Returns available modules that are not configured on the server or are
	 * not proposed to be configured on the server
	 * 
	 * @return list of modules that are not configured on the server or are not
	 *         proposed to be configured on the server
	 */
	public List<String> getAvailableModules() {
		return getItemsFromTable(0);
	}

	/**
	 * Returns modules that are configured or are proposed to be configured on
	 * the server
	 * 
	 * @return list of modules that are configured or are proposed to be
	 *         configured on the server
	 */
	public List<String> getConfiguredModules() {
		return getItemsFromTable(1);
	}

	private List<String> getItemsFromTable(int tableIndex) {
		List<String> items = new ArrayList<String>();
		for (TreeItem ti : new DefaultTree(tableIndex).getAllItems()) {
			items.add(ti.getText());
		}
		return items;
	}
}
