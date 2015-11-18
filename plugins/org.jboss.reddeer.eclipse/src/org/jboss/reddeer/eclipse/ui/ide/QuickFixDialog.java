package org.jboss.reddeer.eclipse.ui.ide;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.table.DefaultTableItem;

/**
 * Represents Quick Fix dialog
 * 
 * @author jjankovi
 *
 */
public class QuickFixDialog extends DefaultShell {

	public static final String TITLE = "Quick Fix";
	
	/**
	 * Constructs the view with {@value #TITLE}.
	 * Opens QuickFix dialog and set focus on it.
	 */
	public QuickFixDialog() {
		super(TITLE);
	}
	
	/**
	 * Get available fixes provided by quick fix.
	 *
	 * @return 	available fixes
	 */
	public List<String> getAvailableFixes() {
		List<String> fixes = new ArrayList<String>();
		Table fixesTable = new DefaultTable(0); 
		int count = fixesTable.rowCount();
		for (int i = 0; i < count; i++) {
			fixes.add(fixesTable.getItem(i).getText());
		}
		return fixes;
	}
	
	/**
	 * Select fix in available fixes with provided index .
	 *
	 * @param index the index
	 */
	public void selectFix(int index) {
		new DefaultTable(0).select(index);
	}
	
	/**
	 * Select fix in available fixes with provided text.
	 *
	 * @param fix the fix
	 */
	public void selectFix(String fix) {
		Table fixTable = new DefaultTable(0);
		int count = fixTable.rowCount();
		for (int i = 0; i < count; i++) {
			String fixInTable = fixTable.getItem(i).getText();
			if (fixInTable.contains(fix)) {
				fixTable.select(fixInTable);
				break;
			}
		}
	}
	
	/**
	 * Get resources files in quick fix dialog.
	 *
	 * @return the resources
	 */
	public List<String> getResources() {
		List<String> resources = new ArrayList<String>();
		Table resourcesTable = new DefaultTable(1);
		int count = resourcesTable.rowCount();
		for (int i = 0; i < count; i++) {
			resources.add(resourcesTable.getItem(i).getText());
		}
		return resources;
	}
	
	/**
	 * Select resource in resources table with provided text.
	 *
	 * @param resource the new resource
	 */
	public void setResource(String resource) {
		new DefaultTableItem(new DefaultTable(1),resource).setChecked(true);
	}
	
	/**
	 * Press Select All button.
	 */
	public void selectAll() {
		new PushButton("Select All").click();
	}
	
	/**
	 * Press Deselect All button.
	 */
	public void deselectAll() {
		new PushButton("Deselect All").click();
	}
	
	/**
	 * Press Cancel button.
	 */
	public void cancel() {
		new PushButton("Cancel").click();
		new WaitWhile(new ShellWithTextIsActive(TITLE));
	}
	
	/**
	 * Press Finish button.
	 */
	public void finish() {
		new PushButton("Finish").click();
		new WaitWhile(new ShellWithTextIsActive(TITLE));
	}
	
}
